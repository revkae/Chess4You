package me.raven.Engine.Utils;

import static org.lwjgl.opengl.GL20.*;

public class VertexAttribPointer {

    private int loc;

    public VertexAttribPointer(int vertexSize, int size, int loc, int offset) {
        this.loc = loc;
        int byteSize = vertexSize * Float.BYTES;
        glVertexAttribPointer(loc, size, GL_FLOAT, false, byteSize, (long) offset * Float.BYTES);
        glEnableVertexAttribArray(loc);
    }

    public void enable() {
        glEnableVertexAttribArray(loc);
    }

    public void disable() {
        glDisableVertexAttribArray(loc);
    }
}
