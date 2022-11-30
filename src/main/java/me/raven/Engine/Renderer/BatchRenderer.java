package me.raven.Engine.Renderer;

import me.raven.Engine.Utils.*;
import me.raven.Sandbox.Managers.GameManager;
import org.joml.Matrix4f;
import org.joml.Vector3f;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;

public class BatchRenderer {

    private final int MAX_QUAD_SIZE = 10000;
    private final int MAX_VERTEX_SIZE = MAX_QUAD_SIZE * 4;
    private final int MAX_INDEX_SIZE = MAX_QUAD_SIZE * 6;
    private final int MAX_QUAD_FLOAT_SIZE = MAX_QUAD_SIZE * 40;
    private final int MAX_QUAD_BYTE_SIZE = MAX_QUAD_FLOAT_SIZE * Float.BYTES;

    private float[] bufferData = new float[MAX_QUAD_FLOAT_SIZE];
    private float[] tempBufferData = new float[MAX_QUAD_FLOAT_SIZE];
    private int currentDataLength = 0;
    private int currentIndexLength = 0;
    public Shader shader;
    public VertexArrayBuffer vertexArrayBuffer;
    public VertexBuffer vertexBuffer;
    public IndexBuffer indexBuffer;
    Texture texture;
    Texture texture1;

    public BatchRenderer() {
        texture = new Texture("resources/wall.jpg", 0);
        texture1 = new Texture("resources/awesomeface.jpg", 1);

        shader = new Shader("vertex.vert", "fragment.frag");
        vertexArrayBuffer = new VertexArrayBuffer();
        vertexBuffer = new VertexBuffer(MAX_QUAD_BYTE_SIZE, GL_DYNAMIC_DRAW);

        int indices[] = generateIndices();
        indexBuffer = new IndexBuffer(indices);

        VertexAttribPointer posAttrib = new VertexAttribPointer(10, 3, 0, 0);
        VertexAttribPointer colorAttrib = new VertexAttribPointer(10, 4, 1, 3);
        VertexAttribPointer texCoordAttrib = new VertexAttribPointer(10, 2, 2, 7);
        VertexAttribPointer texIDAttrib = new VertexAttribPointer(10, 1, 3, 9);

        shader.use();
        shader.setInt("fTexture0", 0);
        shader.setInt("fTexture1", 1);
        shader.setMat4f("ortho", GameManager.get().getCamera().getOrthoMatrix());
        shader.setMat4f("view", GameManager.get().getCamera().getViewMatrix());
        shader.setMat4f("model", new Matrix4f().identity());
    }

    public void draw() {
        texture.bind();
        texture1.bind();

        vertexArrayBuffer.bind();
        shader.use();
        glDrawElements(GL_TRIANGLES, currentIndexLength, GL_UNSIGNED_INT, 0);
        shader.unuse();
        vertexArrayBuffer.unbind();

        texture.unbind();
        texture1.unbind();
    }

    public void end() {
        glDeleteBuffers(vertexBuffer.id);
        glDeleteBuffers(indexBuffer.id);
        glDeleteVertexArrays(vertexArrayBuffer.id);

        glDeleteTextures(texture.id);
        glDeleteTextures(texture1.id);
    }

    public void putData(float[] data) {
        int num = currentDataLength;
        for (float datum : data) {
            tempBufferData[num] = datum;
            num++;
        }

        currentIndexLength += 6;
        currentDataLength += data.length;
    }

    public boolean hasEnoughSpace(float[] data) {
        return currentDataLength + data.length <= bufferData.length;
    }

    public void updateData() {
        vertexBuffer.bind();
        glBufferSubData(GL_ARRAY_BUFFER, 0L, tempBufferData);
        vertexBuffer.unbind();
        currentDataLength = 0;
        tempBufferData = new float[MAX_QUAD_FLOAT_SIZE];
    }

    private int[] generateIndices() {
        int[] output = new int[MAX_INDEX_SIZE];
        int offset = 0;
        for (int i = 0; i < MAX_INDEX_SIZE; i+=6) {
            output[0 + i] = 0 + offset;
            output[1 + i] = 1 + offset;
            output[2 + i] = 2 + offset;

            output[3 + i] = 2 + offset;
            output[4 + i] = 3 + offset;
            output[5 + i] = 0 + offset;

            offset += 4;
        }
        return output;
    }
}
