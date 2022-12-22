package me.raven.Sandbox.Managers;

import me.raven.Sandbox.Scenes.GameScene;
import me.raven.Sandbox.Scenes.MenuScene;
import me.raven.Sandbox.Scenes.Scene;

public class SceneManager {

    private static SceneManager instance = null;
    private Scene current;

    public SceneManager() {
        instance = this;
    }

    public void createFirstScene() {
        current = new MenuScene(0);
    }

    public void switchScene() {

    }

    public Scene getCurrent() {
        return current;
    }

    public static SceneManager get() {
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }
}
