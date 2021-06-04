package net.kappabyte.kappaengine.physics;

import java.util.ArrayList;

import org.joml.Vector3f;

import net.kappabyte.kappaengine.math.Time;
import net.kappabyte.kappaengine.util.Log;

public class Rigidbody extends AABBCollider {

    public ArrayList<Force> forces = new ArrayList<Force>();
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
        for(Collider collider : getCollisions()) {
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
            && (getMinAbsolute().y + displacement.y < other.getMaxAbsolute().y && getMaxAbsolute().y + displacement.y > other.getMinAbsolute().y)
            && (getMinAbsolute().z + displacement.z < other.getMaxAbsolute().z && getMaxAbsolute().z + displacement.z > other.getMinAbsolute().z)) {
                displacement.x += other.getMaxAbsolute().x - (getMinAbsolute().x + displacement.x);
                velocity.x = 0;
                Log.info("Collide: +x");
        }
        //+z
        if((getMinAbsolute().x + displacement.x < other.getMaxAbsolute().x && getMaxAbsolute().x + displacement.x > other.getMinAbsolute().x)
            && (getMinAbsolute().y + displacement.y < other.getMaxAbsolute().y && getMaxAbsolute().y + displacement.y > other.getMinAbsolute().y)
            && (getMinAbsolute().z - displacement.z < other.getMaxAbsolute().z && getMaxAbsolute().z - displacement.z > other.getMaxAbsolute().z)) {
                displacement.z += other.getMaxAbsolute().z - (getMinAbsolute().z + displacement.z);
                velocity.z = 0;
                Log.info("Collide: +z");
        }
        // -x
        if((getMinAbsolute().x + displacement.x < other.getMinAbsolute().x && getMaxAbsolute().x + displacement.x > other.getMinAbsolute().x)
            && (getMinAbsolute().y + displacement.y < other.getMaxAbsolute().y && getMaxAbsolute().y + displacement.y > other.getMinAbsolute().y)
            && (getMinAbsolute().z + displacement.z < other.getMaxAbsolute().z && getMaxAbsolute().z + displacement.z > other.getMinAbsolute().z)) {
                displacement.x += other.getMaxAbsolute().x - (getMinAbsolute().x + displacement.x);
                velocity.x = 0;
                Log.info("Collide: -x");
        }
        //-z
        if((getMinAbsolute().x + displacement.x < other.getMaxAbsolute().x && getMaxAbsolute().x + displacement.x > other.getMinAbsolute().x)
            && (getMinAbsolute().y + displacement.y < other.getMaxAbsolute().y && getMaxAbsolute().y + displacement.y > other.getMinAbsolute().y)
            && (getMinAbsolute().z - displacement.z < other.getMinAbsolute().z && getMaxAbsolute().z - displacement.z > other.getMinAbsolute().z)) {
                displacement.z += other.getMaxAbsolute().z - (getMinAbsolute().z + displacement.z);
                velocity.z = 0;
                Log.info("Collide: -z");
        }
        //+y
        if((getMinAbsolute().x + displacement.x < other.getMaxAbsolute().x && getMaxAbsolute().x + displacement.x > other.getMinAbsolute().x)
            && (getMinAbsolute().y + displacement.y < other.getMaxAbsolute().y && getMaxAbsolute().y + displacement.y > other.getMaxAbsolute().y)
            && (getMinAbsolute().z + displacement.z < other.getMaxAbsolute().z && getMaxAbsolute().z + displacement.z > other.getMinAbsolute().z)) {
                displacement.y += other.getMaxAbsolute().y - (getMinAbsolute().y + displacement.y);
                velocity.y = 0;
        }
        //-y
        if((getMinAbsolute().x + displacement.x < other.getMaxAbsolute().x && getMaxAbsolute().x + displacement.x > other.getMinAbsolute().x)
            && (getMinAbsolute().y + displacement.y < other.getMinAbsolute().y && getMaxAbsolute().y + displacement.y > other.getMinAbsolute().y)
            && (getMinAbsolute().z + displacement.z < other.getMaxAbsolute().z && getMaxAbsolute().z + displacement.z > other.getMinAbsolute().z)) {
                displacement.y += other.getMinAbsolute().y - (getMaxAbsolute().y + displacement.y);
                velocity.y = 0;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
