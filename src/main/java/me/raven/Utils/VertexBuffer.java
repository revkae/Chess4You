package me.raven.Utils;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL15.*;

public class VertexBuffer {
    private int id;

    public VertexBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data).flip();

        id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, id);
        glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
    }

    public void bind() {
        glBindBuffer(GL_ARRAY_BUFFER, id);
    }

    public void unbind(){
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
}
