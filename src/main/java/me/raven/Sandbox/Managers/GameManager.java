package me.raven.Sandbox.Managers;

import me.raven.Engine.Camera;
import me.raven.Engine.Listeners.MouseListener;
import me.raven.Engine.Renderer.Renderer;
import me.raven.Engine.Utils.*;
import me.raven.Engine.Window;
import me.raven.Sandbox.Game.BoardManager;
import me.raven.Sandbox.Game.Piece;
import me.raven.Sandbox.Game.PieceManager;
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
    private BoardManager boardManager;
    private PieceManager pieceManager;

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
        renderer.createTextureBatchRenderer();
        boardManager = new BoardManager();
        pieceManager = new PieceManager();

        while (!glfwWindowShouldClose(window.getWindow())) {
            glClear(GL_COLOR_BUFFER_BIT);

            boardManager.onUpdate();
            boardManager.onRender(renderer);

            pieceManager.onUpdate();
            pieceManager.onRender(renderer);

            renderer.onRender();

            glfwSwapBuffers(window.getWindow());
            glfwPollEvents();

            DeltaTime.calculate();
        }
        renderer.shutdown();
        end();
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

    public BoardManager getBoardManager() {
        return boardManager;
    }

    public PieceManager getPieceManager() {
        return pieceManager;
    }
}
