package me.raven.Engine.Components;

import me.raven.Engine.Utils.Transform;
import org.joml.Vector3f;

public class Collision {

    private Transform transform;

    public Collision(Transform transform) {
        this.transform = transform;
    }

    public boolean isInside(Vector3f position) {
//        System.out.println("positionX: " + position.x);
//        System.out.println("transformX: " + transform.position.x);
//        if (transform.position.x <= position.x && transform.position.x + transform.scale.x >= position.x) {
//            System.out.println("x is inside");
//        }
//        System.out.println("positionY: " + position.y);
//        System.out.println("transformY: " + transform.position.y);
//        if (transform.position.y <= position.y && transform.position.y + transform.scale.y >= position.y) {
//            System.out.println("y is inside");
//        }
        if (transform.position.x <= position.x && transform.position.x + transform.scale.x >= position.x) {
            if (transform.position.y <= position.y && transform.position.y + transform.scale.y >= position.y) {
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
