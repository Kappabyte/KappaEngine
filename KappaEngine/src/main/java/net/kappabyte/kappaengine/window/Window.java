package net.kappabyte.kappaengine.window;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.*;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Collection;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLCapabilities;
import org.lwjgl.system.MemoryUtil;

import net.kappabyte.kappaengine.graphics.RenderData;
import net.kappabyte.kappaengine.math.Time;
import net.kappabyte.kappaengine.scenes.GameObject;
import net.kappabyte.kappaengine.scenes.Scene;
import net.kappabyte.kappaengine.scenes.components.Component;
import net.kappabyte.kappaengine.scenes.components.Renderable;
import net.kappabyte.kappaengine.scenes.components.Camera;
import net.kappabyte.kappaengine.util.Log;

public abstract class Window {

    private String title;
    private long handle;

    private Scene scene;

    private GLCapabilities capabilities;

    public Window(String title) {
        this.title = title;

        initalizeWindow();
    }

    int width = 300;
    int height = 300;
    protected final void initalizeWindow() {
        // Configure GLFW
		glfwDefaultWindowHints(); // optional, the current window hints are already the default
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE); // the window will be resizable

        // Create the window
		handle = glfwCreateWindow(300, 300, title, NULL, NULL);
		if ( handle == NULL )
			throw new RuntimeException("Failed to create the GLFW window");

		// Setup a key callback. It will be called every time a key is pressed, repeated or released.
		glfwSetKeyCallback(handle, (handle, key, scancode, action, mods) -> {
			if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
				glfwSetWindowShouldClose(handle, true); // We will detect this in the rendering loop
		});

        // Make the OpenGL context current
		glfwMakeContextCurrent(handle);
		// Enable v-sync
		glfwSwapInterval(1);

		// Make the window visible
		glfwShowWindow(handle);
    }

    public final void update() {
        //Log.info("Window Update");
        if(capabilities != null) {
            GL.setCapabilities(capabilities);
        } else {
            capabilities = GL.createCapabilities();
            onWindowReady();
        }
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        glfwGetWindowSize(handle, width, height);

        if (width.get(0) != this.width || height.get(0) != this.height) {
            glViewport(0, 0, width.get(0), height.get(0));
            this.width = width.get(0);
            this.height = height.get(0);
            scene.getActiveCamera().updateProjectionMatrix();
        }

        Time.update();

		glClearColor(0.0f, 0.5f, 0.5f, 0.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

        if(scene != null) {
            Render();
        }

        for(GameObject object : scene.gameObjects) {
            for(Component component : object.GetComponents(Component.class)) {
                component.onUpdate();
            }
        }

		glfwSwapBuffers(handle); // swap the color buffers

		// Poll for window events. The key callback above will only be
		// invoked during this call.
		glfwPollEvents();
    }

    protected abstract void onSceneChange();
    protected abstract void onWindowReady();

    public final int getWidth() {
        return width;
    }

    public final int getHeight() {
        return height;
    }

    public final void Render() {
        if(scene.getActiveCamera() == null) return;
        Collection<Renderable> sceneRenderables = scene.GetComponents(Renderable.class);
        for(Renderable renderable : sceneRenderables) {
            renderable.Render();
        }
    }

    public final void closeWindow() {
        Log.info("Window " + handle + " has been marked to be closed!");
        glfwSetWindowShouldClose(handle, true);
    }

    public final void setScene(Scene scene) {
        this.scene = scene;
        onSceneChange();
    }

    public final Scene getScene() {
        return scene;
    }

    public final void destroyWindow() {
        Log.debug("Window was destroyed!");
        glfwDestroyWindow(handle);
    }
    public final long getHandle() {
        return handle;
    }
}
