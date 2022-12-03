package me.raven.Sandbox.Scenes;

import me.raven.Engine.Listeners.KeyboardListener;
import me.raven.Sandbox.Managers.SceneManager;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_P;

public class GameScene extends Scene {

    public GameScene(int id) {
        super(id);
    }

    @Override
    public void init() {

    }

    @Override
    public void start() {

    }

    @Override
    public void onUpdate(float dt) {

        if (KeyboardListener.isPressed(GLFW_KEY_P)) {
            SceneManager.get().switchScene();
            System.out.println("switched to menu scene");
        }

    }

    @Override
    public void onRender(float dt) {

    }


}
