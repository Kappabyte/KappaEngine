package net.kappabyte.kappaengine.physics;

import java.util.Arrays;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import net.kappabyte.kappaengine.scenes.Scene;
import net.kappabyte.kappaengine.util.Log;

public class Raycast {
    //Help from: https://antongerdelan.net/opengl/raycasting.html
    /**
     * Coords in screenspace coordinates (-1 to 1)
     * (0, 0) is in the centre of the screen.
     */
    public static Ray castRayFromScreenPoint(int x, int y, float max_distance, Scene scene, Collider... ignore) {
        Vector4f ray_clip = new Vector4f(x, y, -1.0f, 1.0f);
        Vector4f ray_eye = ray_clip.mul(new Matrix4f(scene.getActiveCamera().getProjectionMatrix()).invert());
        ray_eye = new Vector4f(ray_eye.x, ray_eye.y, -1.0f, 0.0f);

        ray_eye.mul(new Matrix4f(scene.getActiveCamera().getTransform().getModelViewMatrix(scene.getActiveCamera().getViewMatrix())).invert());
        Vector3f ray = new Vector3f(ray_eye.x, ray_eye.y, ray_eye.z);
        ray.normalize();

        for(AABBCollider collider : scene.GetComponents(AABBCollider.class)) {
            if(Arrays.asList(ignore).contains(collider)) continue;
            if(collider.getTransform().getPosition().distance(scene.getActiveCamera().getTransform().getPosition()) > max_distance) {
                continue;
            }

            if(collidesWith(collider, ray, scene.getActiveCamera().getTransform().getPosition())) {
                return new Ray(collider);
            }
        }

        return null;
    }

    private static boolean collidesWith(AABBCollider other, Vector3f ray, Vector3f origin) {
        Vector3f minAbs = other.getMinAbsolute();
        Vector3f maxAbs = other.getMaxAbsolute();

        float dirfra_x =  1.0f / ray.x;
        float dirfra_y =  1.0f / ray.y;
        float dirfra_z =  1.0f / ray.z;

        float t1 = (minAbs.x - origin.x) * dirfra_x;
        float t2 = (maxAbs.x - origin.x) * dirfra_x;
        float t3 = (minAbs.y - origin.y) * dirfra_y;
        float t4 = (maxAbs.y - origin.y) * dirfra_y;
        float t5 = (minAbs.z - origin.z) * dirfra_z;
        float t6 = (maxAbs.z - origin.z) * dirfra_z;

        float tmin = Math.max(Math.max(Math.min(t1, t2), Math.min(t3, t4)), Math.min(t5, t6));
        float tmax = Math.min(Math.min(Math.max(t1, t2), Math.max(t3, t4)), Math.max(t5, t6));

        // if tmax < 0, ray (line) is intersecting AABB, but the whole AABB is behind us
        if (tmax < 0) {
            return false;
        }
        // if tmin > tmax, ray doesn't intersect AABB
        if (tmin > tmax) {
            return false;
        }
        return true;
    }

    public static class Ray {
        Collider other;

        public Ray(Collider other) {
            this.other = other;
        }

        public Collider getCollider() {
            return other;
        }
    }
}

