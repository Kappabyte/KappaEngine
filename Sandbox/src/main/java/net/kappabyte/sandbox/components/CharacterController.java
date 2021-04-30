package net.kappabyte.sandbox.components;

import org.joml.Vector3f;

import net.kappabyte.kappaengine.input.InputManager.Input;
import net.kappabyte.kappaengine.math.Time;
import net.kappabyte.kappaengine.scenes.GameObject;
import net.kappabyte.kappaengine.scenes.components.Component;
import net.kappabyte.kappaengine.util.Log;

public class CharacterController extends Component {

    private float speed = 5;

    private float turnSpeed = 5000;

    GameObject cameraObject;

    public CharacterController(GameObject cameraObject) {
        this.cameraObject = cameraObject;
    }

    @Override
    public void onStart() {
    }

    @Override
    public void onUpdate() {
        if(getWindow().getInputManager().held(Input.KEYBOARD_W)) getTransform().addPositionLocal(new Vector3f(0, 0, -1).mul(speed).mul(Time.deltaTime()));
        if(getWindow().getInputManager().held(Input.KEYBOARD_S)) getTransform().addPositionLocal(new Vector3f(0, 0, 1).mul(speed).mul(Time.deltaTime()));
        if(getWindow().getInputManager().held(Input.KEYBOARD_A)) getTransform().addPositionLocal(new Vector3f(-1, 0, 0).mul(speed).mul(Time.deltaTime()));
        if(getWindow().getInputManager().held(Input.KEYBOARD_D)) getTransform().addPositionLocal(new Vector3f(1, 0, 0).mul(speed).mul(Time.deltaTime()));
        if(getWindow().getInputManager().held(Input.KEYBOARD_SPACE)) getTransform().addPositionLocal(new Vector3f(0, 1, 0).mul(speed).mul(Time.deltaTime()));
        if(getWindow().getInputManager().held(Input.KEYBOARD_LEFT_SHIFT)) getTransform().addPositionLocal(new Vector3f(0, -1, 0).mul(speed).mul(Time.deltaTime()));

        getTransform().addRotation(new Vector3f(0, 1, 0).mul((float) getWindow().getInputManager().getMouseDelta().x * Time.deltaTime() * turnSpeed));
        cameraObject.getTransform().addRotation(new Vector3f(1, 0, 0).mul((float) getWindow().getInputManager().getMouseDelta().y * Time.deltaTime() * turnSpeed));

        //Clamp rotation
        float pitch = cameraObject.getTransform().getRotationLocal().x;
        Log.debug("Pitch: " + pitch);
        if(pitch < -90 || pitch > 270) pitch = -90;
        if(pitch > 90) pitch = 90;

        cameraObject.getTransform().setRotation(new Vector3f(pitch, cameraObject.getTransform().getRotationLocal().y, cameraObject.getTransform().getRotationLocal().z));
    }

    @Override
    public void onDestroy() {
    }

}
