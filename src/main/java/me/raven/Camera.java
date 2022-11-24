package me.raven;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class Camera {
    private static Camera instance = null;
    private Matrix4f orthoMatrix, viewMatrix;
    public Vector2f position;

    public Camera() {
        instance = this;
        this.position = new Vector2f(0.0f, 0.0f);
        this.orthoMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();

        adjustOrthographic();
    }

    public Camera(Vector2f position) {
        instance = this;
        this.position = position;
        this.orthoMatrix = new Matrix4f();
        this.viewMatrix = new Matrix4f();

        adjustOrthographic();
    }

    public void adjustOrthographic() {
        orthoMatrix.identity();
        orthoMatrix.ortho(0.0f, Window.get().width, 0.0f, Window.get().height, 0.0f, 100.0f);
    }

    public Matrix4f getViewMatrix() {
        Vector3f cameraFront = new Vector3f(0.0f, 0.0f, -1.0f);
        Vector3f cameraUp = new Vector3f(0.0f, 1.0f, 0.0f);
        viewMatrix.identity();
        viewMatrix.lookAt(
                new Vector3f(position.x, position.y, 20.0f),
                cameraFront.add(position.x, position.y, 0.0f),
                cameraUp);
        return viewMatrix;
    }

    public Matrix4f getOrthoMatrix() {
        return orthoMatrix;
    }

    public static Camera get() {
        if (instance == null) {
            instance = new Camera();
        }
        return instance;
    }
}
