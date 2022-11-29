package me.raven.Sandbox.Managers;

import me.raven.Engine.Camera;
import me.raven.Engine.Listeners.KeyboardListener;
import me.raven.Engine.Renderer.BatchRenderer;
import me.raven.Engine.Renderer.Renderer;
import me.raven.Engine.Utils.*;
import me.raven.Engine.Window;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class GameManager {

    private static GameManager instance = null;
    private Window window;
    private Camera camera;
    private SceneManager sceneManager;

    private Renderer renderer;

    public GameManager() {
        instance = this;
        this.window = Window.get();
        this.camera = new Camera();
        this.sceneManager = new SceneManager();
        this.renderer = new Renderer();
    }

    public void loop() {
        GL.createCapabilities();

        glClearColor(1.0f, 0.0f, 0.0f, 0.0f);

        sceneManager.createFirstScene();

        renderer.createBatchRenderer();

        while (!glfwWindowShouldClose(window.getWindow())) {
            glClear(GL_COLOR_BUFFER_BIT);

            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 10; j++) {
                    renderer.putData(Vertex.createQuad(new Vector3f(i * 50.f, j * 50.f, 0.0f), 50.f, (i + j) % 2));
                }
            }
            System.out.println(renderer.getBatchRenderers().size());
            renderer.onRender();

            glfwSwapBuffers(window.getWindow());
            glfwPollEvents();

            DeltaTime.calculate();
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
        glfwFreeCallbacks(window.getWindow());
        glfwDestroyWindow(window.getWindow());

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public static GameManager get() {
        if (instance == null) {
            instance = new GameManager();
        }
        return instance;
    }

    public Camera getCamera() {
        return camera;
    }

    public SceneManager getSceneManager() {
        return sceneManager;
    }

    public Renderer getRenderer() {
        return renderer;
    }
}
