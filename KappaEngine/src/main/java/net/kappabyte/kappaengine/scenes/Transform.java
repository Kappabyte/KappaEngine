package net.kappabyte.kappaengine.scenes;

import net.kappabyte.kappaengine.scenes.components.Camera;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Quaternionfc;
import org.joml.Vector3f;

public class Transform {
    private Vector3f position;
    private Vector3f rotation;
    private Vector3f scale;

    private Matrix4f modelViewMatrix = new Matrix4f();

    public Transform(Vector3f position, Vector3f rotation, Vector3f scale) {
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public Vector3f addPosition(Vector3f positionDelta) {
        position.add(positionDelta);
        return position;
    }

    public Vector3f addRotation(Vector3f rotationDelta) {
        rotation.add(rotationDelta.x % 360, rotationDelta.y % 360, rotationDelta.z % 360);
        return rotation;
    }

    public Vector3f addScale(Vector3f scaleDelta) {
        scale.add(scaleDelta);
        return scale;
    }

    public Vector3f getPosition() {
        return position;
    }

    public Vector3f getRotation() {
        return rotation;
    }

    public Vector3f getScale() {
        return scale;
    }

    public void setPosition(Vector3f position) {
        this.position.set(position);
    }

    public void setRotation(Vector3f rotation) {
        this.rotation.set(rotation.x % 360f, rotation.y % 360f, rotation.z % 360f);
    }

    public void setScale(Vector3f scale) {
        this.scale.set(position);
    }

    public Matrix4f getModelViewMatrix(Matrix4f viewMatrix) {
        modelViewMatrix.identity().translate(position).rotateXYZ(rotation.x, rotation.y, rotation.z).scale(scale);
        
        return new Matrix4f(viewMatrix).mul(modelViewMatrix);
    }
}
