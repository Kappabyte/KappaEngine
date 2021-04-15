package net.kappabyte.kappaengine.scenes.components;

import org.joml.Matrix4f;

import net.kappabyte.kappaengine.scenes.GameObject;
import net.kappabyte.kappaengine.scenes.components.Component;
import net.kappabyte.kappaengine.util.Log;
import net.kappabyte.kappaengine.window.Window;
import org.joml.Vector3f;

public class Camera extends Component {

    private final float FOV = (float) Math.toRadians(60.0f);
    private final float Z_NEAR = 0.01f;
    private final float Z_FAR = 1000.f;
    
    private Matrix4f viewMatrix = new Matrix4f();

    private Matrix4f projectionMatrix;

    private Window window;

    public Camera(Window window) {
        super();
        this.window = window;
        updateProjectionMatrix();
    }

    public void updateProjectionMatrix() {
        float aspectRatio = ((float) window.getWidth()) / ((float) window.getHeight());
        Log.info("Window Ratio: " + aspectRatio);
        Log.info("Window Width: " + window.getWidth());
        Log.info("Window Height: " + window.getHeight());
        projectionMatrix = new Matrix4f().perspective(FOV, aspectRatio, Z_NEAR, Z_FAR);
    }

    public Matrix4f getProjectionMatrix() {
        return projectionMatrix;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onUpdate() {
    }

    @Override
    public void onDestroy() {
    }
    
    public Matrix4f getViewMatrix() {
        viewMatrix.identity();
        
        viewMatrix.rotate((float) getTransform().getRotation().x(), new Vector3f(1, 0, 0));
        viewMatrix.rotate((float) getTransform().getRotation().y(), new Vector3f(0, 1, 0));
        viewMatrix.rotate((float) getTransform().getRotation().z(), new Vector3f(0, 0, 1));
        
        return viewMatrix;
    }
    
}
