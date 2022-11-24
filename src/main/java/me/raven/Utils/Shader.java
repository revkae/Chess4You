package me.raven.Utils;

import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
    private int vertex, fragment, program;

    public Shader(String vertexFileName, String fragmentFileName) {
        vertex = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertex, fileToString("shaders/" + vertexFileName));
        glCompileShader(vertex);

        checkShader(vertex, vertexFileName);

        fragment = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragment, fileToString("shaders/" + fragmentFileName));
        glCompileShader(fragment);

        checkShader(fragment, fragmentFileName);

        program = glCreateProgram();
        glAttachShader(program, vertex);
        glAttachShader(program, fragment);
        glLinkProgram(program);

        checkProgram(program);
    }

    public void setMat4f(String name, Matrix4f value) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
        value.get(buffer);
        glUniformMatrix4fv(glGetUniformLocation(program, name), false, buffer);
    }

    public void setFloat(String name, float value) {
        glUniform1f(glGetUniformLocation(program, name), value);
    }

    public void setInt(String name, int value) {
        glUniform1i(glGetUniformLocation(program, name), value);
    }

    private String fileToString(String path) {
        String output;
        try {
            String temp = new String(Files.readAllBytes(Paths.get(path)));
            output = temp;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return output;
    }

    private void checkShader(int shader, String name) {
        int success = glGetShaderi(shader, GL_COMPILE_STATUS);

        if (success == GL_FALSE) {
            int len = glGetShaderi(shader, GL_INFO_LOG_LENGTH);
            System.out.println(name + " shader failed to compile");
            System.out.println(glGetShaderInfoLog(shader, len));
            assert false : "";
        }
    }

    private void checkProgram(int program) {
        int success = glGetProgrami(program, GL_LINK_STATUS);
        if (success == GL_FALSE) {
            int len = glGetProgrami(program, GL_INFO_LOG_LENGTH);
            System.out.println("program failed to link");
            System.out.println(glGetProgramInfoLog(program, len));
            assert false : "";
        }
    }

    public void use() {
        glUseProgram(program);
    }

    public void unuse() {
        glUseProgram(0);
    }
}
