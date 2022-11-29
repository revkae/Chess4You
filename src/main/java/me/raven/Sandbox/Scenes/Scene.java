package me.raven.Sandbox.Scenes;

public abstract class Scene {

    public int id;
    public Scene(int id) {
        this.id  = id;
    }

    public void start() {

    }

    abstract public void init();
    abstract public void update(float dt);
    abstract public void render(float dt);
}
