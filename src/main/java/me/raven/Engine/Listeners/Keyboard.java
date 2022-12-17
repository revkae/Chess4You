package me.raven.Engine.Listeners;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class Keyboard {

    static boolean[] keys = new boolean[500];

    public static void key_callback(long window, int key, int scancode, int action, int mods)
    {
        if (action == GLFW_PRESS)
            keys[key] = true;
        else if (action == GLFW_RELEASE)
            keys[key] = false;
    }

    public static boolean isPressed(int key) {
        return keys[key];
    }
}
