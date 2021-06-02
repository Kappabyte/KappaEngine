package net.kappabyte.kappaengine.physics;

import java.util.ArrayList;

import org.joml.Vector3f;

import net.kappabyte.kappaengine.math.Time;
import net.kappabyte.kappaengine.util.Log;

public class Rigidbody extends AABBCollider {

    public ArrayList<Force> forces = new ArrayList<Force>();
    public Vector3f velocity = new Vector3f(0, 0, 0);
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
        Log.info("Player: " + getMinAbsolute().y);
        Log.info("Target: " + (getMinAbsolute().y + displacement.y));
        for(Collider collider : getCollisionsAtOffset(displacement)) {
            if(!(collider instanceof AABBCollider)) return;
            AABBCollider other = (AABBCollider) collider;
            if(getMinAbsolute().x + displacement.x <= other.getMaxAbsolute().x && getMaxAbsolute().x + displacement.x >= other.getMinAbsolute().x) {
                displacement.x = 0;
                velocity.x = 0;
            }
            if(getMinAbsolute().y + displacement.y <= other.getMaxAbsolute().y && getMaxAbsolute().y + displacement.y >= other.getMinAbsolute().y) {
                displacement.y = 0;
                velocity.y = 0;
            }
            if(getMinAbsolute().z + displacement.z <= other.getMaxAbsolute().z && getMaxAbsolute().z + displacement.z >= other.getMinAbsolute().z) {
                displacement.z = 0;
                velocity.z = 0;
            }
            Log.info("Chunk: " + other.getMaxAbsolute().y);
        }
        getTransform().addPosition(displacement);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
