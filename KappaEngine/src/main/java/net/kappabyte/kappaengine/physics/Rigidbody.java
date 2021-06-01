package net.kappabyte.kappaengine.physics;

import java.util.ArrayList;
import java.util.List;

import org.joml.Vector3f;

import net.kappabyte.kappaengine.math.Time;
import net.kappabyte.kappaengine.scenes.components.Component;
import net.kappabyte.kappaengine.util.Log;

public class Rigidbody extends AABBCollider {

    public ArrayList<Vector3f> forces = new ArrayList<Vector3f>();
    public Vector3f velocity = new Vector3f(0, 0, 0);
    public float mass = 1.0f;

    public float gravity = -9.81f;

    public Rigidbody(float mass) {
        super(new Vector3f(-0.5f, 0, -0.5f), new Vector3f(0.5f, 2, 0.5f));
        this.mass = mass;
    }

    public void applyForce(Vector3f force) {

    }

    public void applyForce(Vector3f force, float time) {

    }

    @Override
    public void onStart() {

    }

    @Override
    public void onUpdate() {
        float time = Time.deltaTime();
        Log.info("Time: " + time);

        // Get net force acting on object
        Vector3f netForce = new Vector3f();
        for (Vector3f force : forces) {
            netForce.add(force);
        }
        netForce.add(0, gravity * mass, 0);

        // Get acceleration of the object
        // F = ma
        Vector3f acceleration = netForce.div(mass);
        Log.info("Acceleration: " + acceleration);
        // Calculate displacement
        Vector3f displacement = new Vector3f(velocity).mul(time)
                .add(new Vector3f(acceleration).mul(0.5f).mul(time * time));
        getTransform().addPosition(displacement);

        // Get final velocity
        // a = (dv/dt)
        // a = (v2 - v1)t
        // at + v1 = v2
        velocity.add(new Vector3f(acceleration).mul(time));
        Log.info("Velocity: " + velocity);
    }

    @Override
    public void onDestroy() {

    }
}
