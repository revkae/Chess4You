package me.raven.Listeners;

import static org.lwjgl.glfw.GLFW.GLFW_PRESS;
import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;

public class MouseListener {

    static boolean[] buttons = new boolean[7];
    static double posX, posY;
    static double scrollX, scrollY;

    public static void cursor_position_callback(long window, double xpos, double ypos) {
        posX = xpos;
        posY = ypos;
    }

    public static void mouse_button_callback(long window, int button, int action, int mods) {
        if (action == GLFW_PRESS)
            buttons[button] = true;
        else if (action == GLFW_RELEASE)
            buttons[button] = false;
    }

    public static void scroll_callback(long window, double xoffset, double yoffset) {
        scrollX = xoffset;
        scrollY = yoffset;
    }

}
