package me.raven.Utils;

import static org.lwjgl.opengl.GL20.*;

public class VertexAttribPointer {

    private int loc;

    public VertexAttribPointer(int posSize, int colorSize, int texSize, int loc, int offset) {
        this.loc = loc;
        int byteSize = (posSize + colorSize + texSize) * Float.BYTES;
        glVertexAttribPointer(loc, posSize, GL_FLOAT, false, byteSize, (long) offset * Float.BYTES);
        glEnableVertexAttribArray(loc);
    }

    public void enable() {
        glEnableVertexAttribArray(loc);
    }

    public void disable() {
        glDisableVertexAttribArray(loc);
    }
}
