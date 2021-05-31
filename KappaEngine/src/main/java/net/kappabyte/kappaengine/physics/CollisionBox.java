package net.kappabyte.kappaengine.physics;

import java.util.ArrayList;

import org.joml.Vector3f;

import net.kappabyte.kappaengine.graphics.Mesh;
import net.kappabyte.kappaengine.graphics.RenderData;
import net.kappabyte.kappaengine.graphics.materials.RainbowMaterial;
import net.kappabyte.kappaengine.scenes.components.Component;
import net.kappabyte.kappaengine.scenes.components.Renderable;

public class CollisionBox extends Renderable {

    private static ArrayList<CollisionBox> collisions = new ArrayList<CollisionBox>();

    private Mesh debugMesh;

    Vector3f min;
    Vector3f max;

    public CollisionBox(Vector3f min, Vector3f max) {
        super(new RainbowMaterial(), true);
        this.min = min;
        this.max = max;

        float[] verts = new float[] {
            min.x, min.y, min.z,
            max.x, min.y, min.z,
            min.x, max.y, min.z,
            min.x, min.y, max.z,
            max.x, max.y, min.z,
            max.x, min.y, max.z,
            min.x, max.y, max.z,
            max.x, max.y, max.z
        };

        int[] indicies = new int[] {
            0, 1, 3,

        }

        //debugMesh = new Mesh();
    }

    public boolean isColliding() {
        for (CollisionBox other : collisions) {
            if (other == this)
                continue;
            if ((other.getMinAbsolute().x <= getMaxAbsolute().x && other.getMaxAbsolute().x >= getMinAbsolute().x)
                    && (other.getMinAbsolute().y <= getMaxAbsolute().y
                            && other.getMaxAbsolute().y >= getMinAbsolute().y)
                    && (other.getMinAbsolute().z <= getMaxAbsolute().z
                            && other.getMaxAbsolute().z >= getMinAbsolute().z))
                return true;
        }
        return false;
    }

    public Collision getCollisionWith(CollisionBox other) {
        return null;
    }

    public Vector3f getMinAbsolute() {
        return new Vector3f(min).add(getTransform().getPosition());
    }

    public Vector3f getMaxAbsolute() {
        return new Vector3f(max).add(getTransform().getPosition());
    }

    @Override
    public void onStart() {
        collisions.add(this);
    }

    @Override
    public void onUpdate() {
    }

    @Override
    public void onDestroy() {
        collisions.remove(this);
    }

    @Override
    protected RenderData supplyRenderData() {
        return new RenderData(getTransform(), mesh, material, getGameObject());
    }
}
