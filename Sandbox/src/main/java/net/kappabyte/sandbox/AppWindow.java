package net.kappabyte.sandbox;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;

import org.joml.Vector3f;

import net.kappabyte.kappaengine.graphics.FragmentShader;
import net.kappabyte.kappaengine.graphics.Texture;
import net.kappabyte.kappaengine.graphics.VertexShader;
import net.kappabyte.kappaengine.graphics.materials.RainbowMaterial;
import net.kappabyte.kappaengine.graphics.materials.UnitTexturedMaterial;
import net.kappabyte.kappaengine.scenes.GameObject;
import net.kappabyte.kappaengine.scenes.components.CubeRender;
import net.kappabyte.kappaengine.scenes.components.Camera;
import net.kappabyte.kappaengine.util.Log;
import net.kappabyte.kappaengine.window.Window;
import net.kappabyte.sandbox.components.Spin;

public class AppWindow extends Window {

    public AppWindow(String title) {
        super(title);
    }

    @Override
    protected void onSceneChange() {
    }

    @Override
    protected void onWindowReady() {
        GameObject camera = new GameObject();
        camera.add(new Camera(this));
        getScene().add(camera);
        getScene().setActiveCamera(camera.GetComponent(Camera.class));

        GameObject cubeA = new GameObject();
        cubeA.add(new CubeRender(cubeA, new UnitTexturedMaterial(new Texture("cube_texture.png"))));
        //object.add(new CubeRender(object, new RainbowMaterial()));
        cubeA.add(new Spin(1));
        getScene().add(cubeA);
        cubeA.getTransform().setPosition(new Vector3f(-3, 0, -5));

        GameObject cubeB = new GameObject();
        cubeB.add(new CubeRender(cubeB, new RainbowMaterial()));
        cubeB.add(new Spin(2));
        getScene().add(cubeB);
        cubeB.getTransform().setPosition(new Vector3f(3, 0, -5));
    }

}
