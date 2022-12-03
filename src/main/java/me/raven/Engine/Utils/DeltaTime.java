package me.raven.Engine.Utils;

import static org.lwjgl.glfw.GLFW.glfwGetTime;

public class DeltaTime {

    static float deltaTime;
    static float beginTime = (float) glfwGetTime();
    static float endTime = (float) glfwGetTime();

    public static void calculate() {
        endTime = (float) glfwGetTime();
        deltaTime = endTime - beginTime;
        beginTime = endTime;
    }

    public static float get() {
        return deltaTime;
    }
}
