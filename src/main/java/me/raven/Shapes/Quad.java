package me.raven.Shapes;

import me.raven.Camera;
import me.raven.Utils.*;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class Quad {

    private float vertices[];

    private int indices[] = {
            0, 1, 2,
            2, 3, 0
    };

    Shader shader;
    VertexArrayBuffer vertexArrayBuffer;
    VertexBuffer vertexBuffer;
    IndexBuffer indexBuffer;
    List<Texture> textures = new ArrayList<>();
    VertexAttribPointer posAttribPointer;
    VertexAttribPointer texAttribPointer;
    VertexAttribPointer colorAttribPointer;

    private Matrix4f model = new Matrix4f();
    private Vector2f position;
    private Vector3f color;
    private Vector2f size;

    public Quad(Vector2f pos, Vector2f size, Vector3f color) {
        this.position = pos;
        this.size = size;
        this.color = color;

        shader = new Shader("colorVertex.vert", "colorFragment.frag");

        float sizeX = size.x * 0.5f;
        float sizeY = size.y * 0.5f;
        vertices = new float[] {                                   // color
                -sizeX + pos.x, -sizeY + pos.y,  0.0f,             color.x, color.y, color.z, 1.0f,
                 sizeX + pos.x, -sizeY + pos.y,  0.0f,             color.x, color.y, color.z, 1.0f,
                 sizeX + pos.x,  sizeY + pos.y,  0.0f,             color.x, color.y, color.z, 1.0f,
                -sizeX + pos.x,  sizeY + pos.y,  0.0f,             color.x, color.y, color.z, 1.0f,
        };

        shader.use();
        shader.setMat4f("ortho", Camera.get().getOrthoMatrix());
        shader.setMat4f("model", model);
        shader.setMat4f("view", Camera.get().getViewMatrix());
        shader.unuse();

        vertexArrayBuffer = new VertexArrayBuffer();
        vertexBuffer = new VertexBuffer(vertices);
        indexBuffer = new IndexBuffer(indices);
        posAttribPointer = new VertexAttribPointer(3, 4, 0, 0, 0);
        //texAttribPointer = new VertexAttribPointer(3, 4, 2, 1, 7);
        colorAttribPointer = new VertexAttribPointer(3, 4, 0, 1, 3);
    }

    public Quad(Vector3f color) {
        shader = new Shader("colorVertex.vert", "colorFragment.frag");

        vertices = new float[] {               // color
                -0.5f, -0.5f, 0.0f,            color.x, color.y, color.z, 1.0f,
                 0.5f, -0.5f, 0.0f,            color.x, color.y, color.z, 1.0f,
                 0.5f,  0.5f,  0.0f,           color.x, color.y, color.z, 1.0f,
                -0.5f,  0.5f,  0.0f,           color.x, color.y, color.z, 1.0f,
        };

//        vertices = new float[] {               // color                     // texture
//                -0.5f, -0.5f, 0.0f,            color.x, color.y, color.z, 1.0f,      0.0f, 0.0f,
//                0.5f, -0.5f, 0.0f,            color.x, color.y, color.z, 1.0f,      1.0f, 0.0f,
//                0.5f,  0.5f,  0.0f,           color.x, color.y, color.z, 1.0f,      1.0f, 1.0f,
//                -0.5f,  0.5f,  0.0f,           color.x, color.y, color.z, 1.0f,      0.0f, 1.0f,
//        };

        vertexArrayBuffer = new VertexArrayBuffer();
        vertexBuffer = new VertexBuffer(vertices);
        indexBuffer = new IndexBuffer(indices);
        posAttribPointer = new VertexAttribPointer(3, 4, 0, 0, 0);
        //texAttribPointer = new VertexAttribPointer(3, 4, 2, 1, 7);
        colorAttribPointer = new VertexAttribPointer(3, 4, 0, 1, 3);
    }

    public void setPosition(Vector2f pos) {
        this.position = pos;
        shader.use();
        shader.setMat4f("model", model.translate(pos.x, pos.y, 0.0f));
        shader.unuse();
    }

    public Vector2f getPosition() {
        return this.position;
    }

    public Vector3f getColor() {
        return color;
    }

    public Vector2f getSize() {
        return size;
    }

    public void draw() {
        vertexArrayBuffer.bind();
        vertexBuffer.bind();
        posAttribPointer.enable();
        //texAttribPointer.enable();
        colorAttribPointer.enable();
        indexBuffer.bind();
        for (Texture texture : textures) {
            texture.bind();
        }
        shader.use();

        glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);

        vertexArrayBuffer.unbind();
        vertexBuffer.unbind();
        posAttribPointer.disable();
        //texAttribPointer.disable();
        colorAttribPointer.disable();
        indexBuffer.unbind();
        for (Texture texture : textures) {
            texture.unbind();
        }
        shader.unuse();
    }

    public void addTexture(String filepath, String uniName, int slot) {
        textures.add(new Texture(filepath, uniName, slot, this.shader));
    }

    public Shader getShader() {
        return shader;
    }
}
