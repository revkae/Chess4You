package me.raven.Engine.Shapes;

import me.raven.Engine.Drawable;
import me.raven.Engine.Utils.*;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Quad implements Drawable {

    Transform transform;
    Vector4f color;
    float[] data;

    public Quad() {
        transform = new Transform();
        color = new Vector4f(1.f);
        data = Vertex.createQuad(transform.position, color, transform.scale, 0.f);
    }

    public Quad(Vector3f position) {
        transform = new Transform(position);
        color = new Vector4f(1.f);
        data = Vertex.createQuad(transform.position, color, transform.scale, 0.f);
    }

    public Quad(Vector3f position, Vector2f scale) {
        transform = new Transform(position, scale);
        color = new Vector4f(1.f);
        data = Vertex.createQuad(transform.position, color, transform.scale, 0.f);
    }

    @Override
    public float[] getData() {
        return data;
    }
}
