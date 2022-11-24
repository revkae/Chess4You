package me.raven;

import me.raven.Listeners.KeyboardListener;
import me.raven.Shapes.Quad;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL13.*;

public class GameManager {

    Window window = Window.get();
    Camera camera = new Camera();

    public void loop() {
        GL.createCapabilities();

        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        Quad quad = new Quad(new Vector2f(200.f, 200.f), new Vector2f(50.f, 50.f), new Vector3f(1.f));
        quad.addTexture("resources/wall.jpg", "fTexture", 0);
        quad.addTexture("resources/awesomeface.jpg", "test", 1);


        while (!glfwWindowShouldClose(window.window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

            quad.draw();

            glfwSwapBuffers(window.window);
            glfwPollEvents();
        }

        end();
    }

    private void update() {
        if (KeyboardListener.isPressed(GLFW_KEY_SPACE))
            System.out.println("SPACE");
    }

    private void render() {

    }

    private void end() {
        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window.window);
        glfwDestroyWindow(window.window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public Camera getCamera() {
        return camera;
    }
}
