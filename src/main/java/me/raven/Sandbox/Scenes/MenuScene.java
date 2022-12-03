package me.raven.Sandbox.Scenes;

import me.raven.Engine.Listeners.KeyboardListener;
import me.raven.Sandbox.Managers.SceneManager;
import me.raven.Engine.Shapes.Quad;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_O;

public class MenuScene extends Scene {

    List<Quad> quads = new ArrayList<>();

    public MenuScene(int id) {
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
        if (KeyboardListener.isPressed(GLFW_KEY_O)) {
            SceneManager.get().switchScene();
            System.out.println("switched to game scene");
        }

    }

    @Override
    public void onRender(float dt) {

    }
}
