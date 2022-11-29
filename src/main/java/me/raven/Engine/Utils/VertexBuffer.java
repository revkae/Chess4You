package me.raven.Engine.Utils;

import static org.lwjgl.opengl.GL15.*;

public class VertexBuffer {
    private int id;

    public VertexBuffer(float[] data, int type) {
        id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, id);
        glBufferData(GL_ARRAY_BUFFER, data, type);
    }

    public VertexBuffer(long size, int type) {
        id = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, id);
        glBufferData(GL_ARRAY_BUFFER, size, type);
    }

    public void bind() {
        glBindBuffer(GL_ARRAY_BUFFER, id);
    }

    public void unbind(){
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }
}
