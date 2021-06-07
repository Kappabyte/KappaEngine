package net.kappabyte.kappaengine.physics;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.joml.Vector3f;

import net.kappabyte.kappaengine.math.Time;
import net.kappabyte.kappaengine.util.Log;

public class Rigidbody extends AABBCollider {

    public ArrayList<Force> forces = new ArrayList<Force>();
    Supplier<List<Collider>> colliderSupplier = Collider::getAllColliders;
    public Vector3f velocity = new Vector3f();
    public float mass = 1.0f;

    public float gravity = -9.81f;

    public Rigidbody(float mass) {
        super(new Vector3f(-0.5f, 0, -0.5f), new Vector3f(0.5f, 2, 0.5f));
        this.mass = mass;
    }

    public void applyForce(Vector3f force) {
        forces.add(new Force(force));
    }

    public void setColliderSupplier(Supplier<List<Collider>> colliderSupplier) {
        this.colliderSupplier = colliderSupplier;
    }

    public void applyForce(Vector3f force, float time) {
        forces.add(new Force(force, time));
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        float time = Time.deltaTime();

        // Get net force acting on object
        Vector3f netForce = new Vector3f();
        for (Force force : forces) {
            netForce.add(force.force);
            force.time -= time;
        }
        forces.removeIf(force -> force.time <= 0);

        netForce.add(0, gravity * mass, 0);

        // Get acceleration of the object
        // F = ma
        Vector3f acceleration = netForce.div(mass);

        // Get final velocity
        // a = (dv/dt)
        // a = (v2 - v1)t
        // at + v1 = v2
        velocity.add(new Vector3f(acceleration).mul(time));

        // Calculate displacement
        Vector3f displacement = new Vector3f(velocity).mul(time)
                .sub(new Vector3f(acceleration).mul(0.5f).mul(time * time));

        //Collision Detection
        List<Collider> colliders = colliderSupplier.get();
        for(Collider collider : getCollisions(colliders)) {
            if(!(collider instanceof AABBCollider)) return;
            AABBCollider other = (AABBCollider) collider;
            reactToCollision(other, displacement, velocity);
        }
        getTransform().addPosition(displacement);
    }
    public void reactToCollision(AABBCollider other, Vector3f displacement) {
        reactToCollision(other, displacement, new Vector3f());
    }
    public void reactToCollision(AABBCollider other, Vector3f displacement, Vector3f velocity) {
        //Log.warn("Displacement: " + displacement);
        //+x
        if((getMinAbsolute().x + displacement.x < other.getMaxAbsolute().x && getMaxAbsolute().x + displacement.x > other.getMaxAbsolute().x)
                && (getMinAbsolute().y + displacement.y < other.getMaxAbsolute().y - getSize().y/2 && getMaxAbsolute().y + displacement.y > other.getMinAbsolute().y + getSize().y/2)
                && (getMinAbsolute().z + displacement.z < other.getMaxAbsolute().z - getSize().z/2 && getMaxAbsolute().z + displacement.z > other.getMinAbsolute().z + getSize().z/2)) {
                    displacement.x += other.getMaxAbsolute().x - (getMinAbsolute().x + displacement.x);
                    velocity.x = 0;
                    Log.info("+x");
            }
            //+z
            if((getMinAbsolute().x + displacement.x < other.getMaxAbsolute().x - getSize().x/2 && getMaxAbsolute().x + displacement.x > other.getMinAbsolute().x + getSize().x/2)
                && (getMinAbsolute().y + displacement.y < other.getMaxAbsolute().y - getSize().y/2 && getMaxAbsolute().y + displacement.y > other.getMinAbsolute().y + getSize().y/2)
                && (getMinAbsolute().z + displacement.z < other.getMaxAbsolute().z && getMaxAbsolute().z + displacement.z > other.getMaxAbsolute().z)) {
                    displacement.z += other.getMaxAbsolute().z - (getMinAbsolute().z + displacement.z);
                    velocity.z = 0;
                    Log.info("+z");
            }
            // -x face
            if((getMinAbsolute().x + displacement.x < other.getMinAbsolute().x && getMaxAbsolute().x + displacement.x > other.getMinAbsolute().x)
                && (getMinAbsolute().y + displacement.y < other.getMaxAbsolute().y - getSize().y/2 && getMaxAbsolute().y + displacement.y > other.getMinAbsolute().y + getSize().y/2)
                && (getMinAbsolute().z + displacement.z < other.getMaxAbsolute().z - getSize().z/2 && getMaxAbsolute().z + displacement.z > other.getMinAbsolute().z + getSize().z/2)) {
                    displacement.x += other.getMinAbsolute().x - (getMaxAbsolute().x + displacement.x);
                    velocity.x = 0;
                    Log.info("-x");
            }
            //-z
            if((getMinAbsolute().x + displacement.x < other.getMaxAbsolute().x - getSize().x/2 && getMaxAbsolute().x + displacement.x > other.getMinAbsolute().x - getSize().x/2)
                && (getMinAbsolute().y + displacement.y < other.getMaxAbsolute().y - getSize().y/2 && getMaxAbsolute().y + displacement.y > other.getMinAbsolute().y - getSize().y/2)
                && (getMinAbsolute().z + displacement.z < other.getMinAbsolute().z && getMaxAbsolute().z + displacement.z > other.getMinAbsolute().z)) {
                    displacement.z += other.getMinAbsolute().z - (getMaxAbsolute().z + displacement.z);
                    velocity.z = 0;
                    Log.info("-z");
            }
            //+y
            if((getMinAbsolute().x + displacement.x < other.getMaxAbsolute().x - getSize().x/2 && getMaxAbsolute().x + displacement.x > other.getMinAbsolute().x + getSize().x/2)
                && (getMinAbsolute().y + displacement.y < other.getMaxAbsolute().y && getMaxAbsolute().y + displacement.y > other.getMaxAbsolute().y)
                && (getMinAbsolute().z + displacement.z < other.getMaxAbsolute().z - getSize().z/2 && getMaxAbsolute().z + displacement.z > other.getMinAbsolute().z + getSize().z/2)) {
                    displacement.y += other.getMaxAbsolute().y - (getMinAbsolute().y + displacement.y);
                    velocity.y = 0;
                    Log.info("+y");
            }
            //-y
            if((getMinAbsolute().x + displacement.x < other.getMaxAbsolute().x - getSize().x/2 && getMaxAbsolute().x + displacement.x > other.getMinAbsolute().x + getSize().x/2)
                && (getMinAbsolute().y + displacement.y < other.getMinAbsolute().y && getMaxAbsolute().y + displacement.y > other.getMinAbsolute().y)
                && (getMinAbsolute().z + displacement.z < other.getMaxAbsolute().z - getSize().z/2 && getMaxAbsolute().z + displacement.z > other.getMinAbsolute().z + getSize().z/2)) {
                    displacement.y += other.getMinAbsolute().y - (getMaxAbsolute().y + displacement.y);
                    velocity.y = 0;
                    Log.info("-y");
            }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
