package me.raven.Engine.Utils;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Transform {

    public Vector3f position;
    public Vector2f scale;

    public Transform() {
        init(new Vector3f(0.f), new Vector2f(50.f));
    }

    public Transform(Vector3f position) {
        init(position, new Vector2f(50.f));
    }

    public Transform(Vector3f position, Vector2f scale) {
        init(position, scale);
    }

    private void init(Vector3f position, Vector2f scale) {
        this.position = position;
        this.scale = scale;
    }
}
