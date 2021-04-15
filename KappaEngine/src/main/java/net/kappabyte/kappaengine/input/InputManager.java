package net.kappabyte.kappaengine.input;

import net.kappabyte.kappaengine.window.Window;
import org.joml.Vector2d;
import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

public class InputManager {
    
    private Window window;
    
    private Vector2d prevMouseLocation;
    private Vector2d mouseLocation;
    
    private boolean cursorInWindow = false;
    
    private boolean leftMouseButtonDown = false;
    private boolean rightMouseButtonDown = false;
    private boolean middleMouseButtonDown = false;
    private boolean button3MouseButtonDown = false;
    private boolean button4MouseButtonDown = false;
    
    public InputManager(Window window) {
        this.window = window;
        
        mouseLocation = new Vector2d(0.5, 0.5);
        
        GLFW.glfwSetCursorPosCallback(window.getHandle(), (handle, x, y) -> {
            mouseLocation.x = x;
            mouseLocation.y = y;
        });
        GLFW.glfwSetCursorEnterCallback(window.getHandle(), (handle, entered) -> {
            cursorInWindow = entered;
        });
        GLFW.glfwSetMouseButtonCallback(window.getHandle(), (handle, button, action, mode) -> {
            leftMouseButtonDown = button == GLFW.GLFW_MOUSE_BUTTON_LEFT && action == GLFW.GLFW_PRESS;
        });
    }
    
    public void pollInputDevices() {
        
    }
}
