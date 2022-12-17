package me.raven.Engine.Components;

import me.raven.Engine.Utils.Transform;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Collision {

    private Transform transform;

    public Collision(Transform transform) {
        this.transform = transform;
    }

    public boolean isInside(Vector2f position) {
        if (transform.position.x <= position.x && transform.position.x + transform.scale.x >= position.x) {
            if (transform.position.y <= position.y && transform.position.y + transform.scale.y >= position.y) {
                return true;
            }
        }
        return false;
    }

    public boolean isInside(float x, float y) {
        if (transform.position.x <= x && transform.position.x + transform.scale.x >= x) {
            if (transform.position.y <= y && transform.position.y + transform.scale.y >= y) {
                return true;
            }
        }
        return false;
    }

    public boolean isColliding(Vector3f position) {
        if (transform.position.x <= position.x && transform.position.x + transform.scale.x >= position.x) {
            if (transform.position.y >= position.y && transform.position.y + transform.scale.y <= position.y) {
                return true;
            }
        }
        return false;
    }
}
