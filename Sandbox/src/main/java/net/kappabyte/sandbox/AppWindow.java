package net.kappabyte.sandbox;

import org.joml.Vector3f;

import net.kappabyte.kappaengine.graphics.ModelLoader;
import net.kappabyte.kappaengine.graphics.Texture;
import net.kappabyte.kappaengine.graphics.materials.RainbowMaterial;
import net.kappabyte.kappaengine.graphics.materials.UnitTexturedMaterial;
import net.kappabyte.kappaengine.scenes.GameObject;
import net.kappabyte.kappaengine.scenes.components.Camera;
import net.kappabyte.kappaengine.scenes.components.CubeRender;
import net.kappabyte.kappaengine.scenes.components.MeshRender;
import net.kappabyte.kappaengine.window.Window;
import net.kappabyte.sandbox.components.CharacterController;
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
        GameObject player = new GameObject("Player");
        GameObject camera = new GameObject("Camera");
        player.addChild(camera);
        camera.addComponent(new Camera(this));
        player.addComponent(new CharacterController(camera));
        getScene().addGameObject(player);
        getScene().setActiveCamera(camera.GetComponent(Camera.class));

        test();
        //test2();
    }

    private void test() {
        GameObject cubeA = new GameObject("Cube A");
        cubeA.addComponent(new MeshRender(ModelLoader.loadOBJModel("example_textured_cube.obj"), new UnitTexturedMaterial(new Texture("cube_texture.png")), true));
        cubeA.addComponent(new Spin(1));
        //object.add(new CubeRender(object, new RainbowMaterial()));
        getScene().addGameObject(cubeA);
        cubeA.getTransform().setPosition(new Vector3f(-3, 0, -5));

        GameObject cubeB = new GameObject("Cube B");
        cubeB.addComponent(new CubeRender(new RainbowMaterial()));
        cubeA.addChild(cubeB);
        cubeB.getTransform().setPosition(new Vector3f(0, 2, 0));

        GameObject bunny = new GameObject("Bunny");
        bunny.addComponent(new MeshRender(ModelLoader.loadOBJModel("bunny.obj"), new RainbowMaterial(), false));
        getScene().addGameObject(bunny);
        bunny.getTransform().setPosition(new Vector3f(0, 0, 0));

        getScene().printSceneHeirarchy(0, getScene().getChildren());
    }
}
