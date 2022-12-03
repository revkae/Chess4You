package me.raven.Engine.Shapes;

import me.raven.Engine.Components.Collision;
import me.raven.Engine.Drawable;
import me.raven.Engine.Utils.*;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class Quad implements Drawable {

    Collision collision;
    Transform transform;
    Vector4f color;
    Texture texture;
    float texID;
    float[] data;

    public Quad() {
        transform = new Transform();
        color = new Vector4f(1.f);
        texID = 0.f;
        data = Vertex.createQuad(transform.position, color, transform.scale, texID);
        collision = new Collision(transform);
    }

    public Quad(Vector3f position) {
        transform = new Transform(position);
        color = new Vector4f(1.f);
        texID = 0.f;
        data = Vertex.createQuad(transform.position, color, transform.scale, texID);
        collision = new Collision(transform);
    }

    public Quad(Vector3f position, Vector2f scale) {
        transform = new Transform(position, scale);
        color = new Vector4f(1.f);
        texID = 0.f;
        data = Vertex.createQuad(transform.position, color, transform.scale, texID);
        collision = new Collision(transform);
    }

    public Quad(Vector3f position, Vector2f scale, Texture texture) {
        transform = new Transform(position, scale);
        color = new Vector4f(1.f, 1.f, 1.f, 1.f);
        this.texID = 0.f;
        data = Vertex.createQuad(transform.position, color, transform.scale, texID);
        collision = new Collision(transform);
        this.texture = texture;
    }

    public void setPosition(Vector3f position) {
        this.transform.position = position;
        updateData(position);
    }

    public void setScale(Vector2f scale) {
        this.transform.scale = scale;
        updateData(scale);
    }

    public void setColor(Vector4f color) {
        this.color = color;
        updateData(color);
    }

    public void setTexID(float texID) {
        this.texID = texID;
        updateData(texID);
    }

    public void setTexture(String path) {
        this.texture = new Texture(path);
    }

    private void updateData(Vector4f color) {
        this.data = Vertex.createQuad(this.transform.position, color, this.transform.scale, this.texID);
    }

    private void updateData(Vector2f scale) {
        this.data = Vertex.createQuad(this.transform.position, color, scale, this.texID);
    }

    private void updateData(Vector3f position) {
        this.data = Vertex.createQuad(position, color, this.transform.scale, this.texID);
    }

    private void updateData(float texID) {
        this.data = Vertex.createQuad(this.transform.position, color, this.transform.scale, texID);
    }

    public Collision getCollision() {
        return collision;
    }

    public Transform getTransform() {
        return transform;
    }

    public Vector4f getColor() {
        return color;
    }

    public float getTexID() {
        return texID;
    }

    public Texture getTexture() {
        return texture;
    }

    @Override
    public float[] getData() {
        return data;
    }
}
