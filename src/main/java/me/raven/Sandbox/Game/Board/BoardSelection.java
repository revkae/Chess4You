package me.raven.Sandbox.Game.Board;

import me.raven.Engine.Listeners.Mouse;
import me.raven.Engine.Shapes.Quad;
import me.raven.Sandbox.Game.Colors;
import me.raven.Sandbox.Game.MousePress;
import me.raven.Sandbox.Managers.GameManager;
import org.joml.Vector2f;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_RIGHT;

public class BoardSelection {
    private Queue<Integer> selectedTileNums = new ConcurrentLinkedQueue<>();

    public void onUpdate() {
        if (Mouse.isPressed(GLFW_MOUSE_BUTTON_RIGHT) && !MousePress.isRightPressed) {
            for (int i = 0; i < GameManager.get().getBoardManager().getBoard().size(); i++) {
                Quad quad = GameManager.get().getBoardManager().getBoard().get(i);
                if (quad.getCollision().isInside(new Vector2f(Mouse.getCursorPos().x, Mouse.getCursorPos().y))) {
                    if (quad.getColor().equals(Colors.BOARD_SELECTION.color)) {
                        unselectTile(i);
                    } else {
                        System.out.println(i);
                        selectTile(i);
                    }
                }
            }
            MousePress.isRightPressed = true;
        }
        else if (Mouse.isReleased(GLFW_MOUSE_BUTTON_RIGHT) && MousePress.isRightPressed) {
            MousePress.isRightPressed = false;
        }
        else if (Mouse.isPressed(GLFW_MOUSE_BUTTON_LEFT) && !MousePress.isLeftPressed) {
            unselectAllTiles();
            MousePress.isLeftPressed = true;
        }
        else if (Mouse.isReleased(GLFW_MOUSE_BUTTON_LEFT) && MousePress.isLeftPressed) {
            MousePress.isLeftPressed = false;
        }
    }

    private void selectTile(int num) {
        GameManager.get().getBoardManager().getBoard().get(num).setColor(Colors.BOARD_SELECTION.color);
        selectedTileNums.add(num);
    }

    private void unselectTile(int num) {
        if (!selectedTileNums.contains(num)) return;

        GameManager.get().getBoardManager().getBoard().get(num).setColor(Colors.RESET.color);
        selectedTileNums.remove(Integer.valueOf(num));
    }

    private void unselectAllTiles() {
        for (Integer tile : selectedTileNums) {
            GameManager.get().getBoardManager().getBoard().get(tile).setColor(Colors.RESET.color);
            selectedTileNums.remove(tile);
        }
    }
}
