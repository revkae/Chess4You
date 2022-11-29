package me.raven.Engine.Shapes;

import me.raven.Engine.Utils.*;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

public class Quad {

    public Shader shader;
    public int vboID, eboID, vaoID;

    public Quad() {
        shader = new Shader("vertex.vert", "fragment.frag");
        shader.use();

        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        vboID = glGenBuffers();
        glBindBuffer(GL_VERTEX_ARRAY, vboID);
        glBufferData(GL_VERTEX_ARRAY, Vertex.quadByteSize() * 1000L, GL_DYNAMIC_DRAW);

        int indices[] = {
                0, 1, 2, 2, 3, 0,
                4, 5, 6, 6, 7, 4
        };

        eboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, eboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

        glVertexAttribPointer(0, 3, GL_FLOAT, false, Vertex.byteSize(), Vertex.positionOffsetByte());
        glEnableVertexAttribArray(0);

        glVertexAttribPointer(1, 4, GL_FLOAT, false, Vertex.byteSize(), Vertex.colorOffsetByte());
        glEnableVertexAttribArray(1);

        glVertexAttribPointer(2, 2, GL_FLOAT, false, Vertex.byteSize(), Vertex.texCoordOffsetByte());
        glEnableVertexAttribArray(2);

        glVertexAttribPointer(3, 1, GL_FLOAT, false, Vertex.byteSize(), Vertex.texIDOffsetByte());
        glEnableVertexAttribArray(3);
    }
}
