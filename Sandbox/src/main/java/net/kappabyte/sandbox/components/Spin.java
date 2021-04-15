package net.kappabyte.sandbox.components;

import org.joml.Vector3f;

import net.kappabyte.kappaengine.math.Time;
import net.kappabyte.kappaengine.scenes.GameObject;
import net.kappabyte.kappaengine.scenes.components.Component;
import net.kappabyte.kappaengine.scenes.components.Renderable;
import net.kappabyte.kappaengine.util.Log;

public class Spin extends Component {

    public float speed;

    public Spin(float speed) {
        super();

        this.speed = speed;
    }

    @Override
    public void onStart() {
        Log.info(getGameObject().GetComponent(Renderable.class) + "");
    }

    @Override
    public void onUpdate() {
        getGameObject().getTransform().addRotation(new Vector3f(0, speed * Time.deltaTime(), 0));
    }

    @Override
    public void onDestroy() {
    }

}
