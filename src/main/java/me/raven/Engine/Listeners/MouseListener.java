package me.raven.Engine.Listeners;

import me.raven.Engine.Window;
import org.javatuples.Pair;
import org.joml.Vector2f;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.lwjgl.glfw.GLFW.*;

public class MouseListener {

    static boolean[] buttons = new boolean[7];
    static boolean[] once = new boolean[7];
    static double posX, posY;
    static double scrollX, scrollY;

    public static void cursor_position_callback(long window, double xpos, double ypos) {
        posX = xpos;
        posY = Window.get().getHeight() - ypos;
    }

    public static void mouse_button_callback(long window, int button, int action, int mods) {
        if (action == GLFW_PRESS) {
            buttons[button] = true;
            once[button] = false;
        }
        else if (action == GLFW_RELEASE) {
            buttons[button] = false;
            once[button] = true;
        }
    }

    public static void scroll_callback(long window, double xoffset, double yoffset) {
        scrollX = xoffset;
        scrollY = yoffset;
    }

    public static Vector2f getCursorPos() {
        return new Vector2f((float) posX, (float) posY);
    }

    public static boolean isPressed(int button) {
        return buttons[button];
    }

    public static boolean isReleased(int button) {
        return !buttons[button];
    }
}
