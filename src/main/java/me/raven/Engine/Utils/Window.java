package me.raven.Engine.Utils;

import me.raven.Engine.Listeners.Keyboard;
import me.raven.Engine.Listeners.Mouse;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.system.MemoryStack;

import java.nio.IntBuffer;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.system.MemoryStack.stackPush;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private static Window instance = null;
    long window;
    int width = 0;
    int height = 0;
    String title = null;

    public Window(int width, int height, String title) {
        instance = this;
        this.width = width;
        this.height = height;
        this.title = title;

        init();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();

        if (!glfwInit())
            throw new IllegalStateException("Unable to initialize GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 6);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);

        window = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        glfwSetKeyCallback(window, Keyboard::key_callback);
        glfwSetCursorPosCallback(window, Mouse::cursor_position_callback);
        glfwSetMouseButtonCallback(window, Mouse::mouse_button_callback);
        glfwSetScrollCallback(window, Mouse::scroll_callback);
        glfwSetFramebufferSizeCallback(window, this::framebuffer_size_callback);

        try (MemoryStack stack = stackPush()) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            glfwGetWindowSize(window, pWidth, pHeight);

            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        }

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);

        glfwShowWindow(window);
    }

    void framebuffer_size_callback(long window, int width, int height)
    {
        glViewport(0, 0, width, height);
    }

    public static Window get() {
        if (Window.instance == null) {
            Window.instance = new Window(800, 800, "Chess For You");
        }
        return Window.instance;
    }

    public long getWindow() {
        return window;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getTitle() {
        return title;
    }
}
