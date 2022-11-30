package me.raven.Engine.Utils;

import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class VertexArrayBuffer {

    public int id;

    public VertexArrayBuffer() {
        id = glGenVertexArrays();
        glBindVertexArray(id);
    }

    public void bind() {
        glBindVertexArray(id);
    }

    public void unbind(){
        glBindVertexArray(0);
    }
}
