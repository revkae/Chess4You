package me.raven.Utils;

import org.lwjgl.BufferUtils;

import java.nio.IntBuffer;

import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;

public class IndexBuffer {
    private int id;

    public IndexBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data).flip();

        id = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
    }

    public void bind() {
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, id);
    }

    public void unbind(){
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
    }
}
