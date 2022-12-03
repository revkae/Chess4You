package me.raven.Sandbox.Game;

import me.raven.Engine.Listeners.MouseListener;
import me.raven.Engine.Renderer.Renderer;
import me.raven.Engine.Shapes.Quad;
import me.raven.Engine.Utils.Texture;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;

public class BoardManager {

    private final Vector4f SELECTED_TILE_COLOR = new Vector4f(1.f, 0.f, 0.f, 1.f);
    private final Vector4f NORMAL_BLACK_COLOR = new Vector4f(0.f, 0.f, 0.f, 1.f);
    private final Vector4f NORMAL_WHITE_COLOR = new Vector4f(1.f);
    private List<Integer> whiteTiles = new ArrayList<>(32);
    private List<Integer> blackTiles = new ArrayList<>(32);

    private List<Quad> board = new ArrayList<>(64);
    private List<Integer> selectedTiles = new ArrayList<>(64);

    public BoardManager() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) {
                    whiteTiles.add(board.size() - 1);
                    board.add(new Quad(new Vector3f(0.f + i * 50.f, 0.f + j * 50.f, 0.0f), new Vector2f(45.f, 45.f), 0, new Texture("wall.jpg")));
                }
                else {
                    blackTiles.add(board.size() - 1);
                    board.add(new Quad(new Vector3f(0.f + i * 50.f, 0.f + j * 50.f, 0.0f), new Vector2f(45.f, 45.f), 1, new Texture("awesomeface.jpg")));
                }
            }
        }
    }

    public void onUpdate() {
        if (MouseListener.isPressed(GLFW_MOUSE_BUTTON_RIGHT)) {
            for (int i = 0; i < board.size(); i++) {
                Quad quad = board.get(i);
                if (quad.getCollision().isInside(new Vector3f(MouseListener.getCursorPos().x, MouseListener.getCursorPos().y, 0.0f))) {
                    if (quad.getColor().equals(SELECTED_TILE_COLOR)) {
                        unselectTile(i);
                    } else {
                        selectTile(i);
                    }
                }
            }
        }
    }

    private void selectTile(int num) {
        System.out.println("hey");
        board.get(num).setColor(new Vector4f(0.f));
        selectedTiles.add(num);
    }

    private void unselectTile(int num) {
        if (whiteTiles.contains(num)) {
            board.get(num).setColor(NORMAL_WHITE_COLOR);
            selectedTiles.remove(Integer.valueOf(num));
        } else if (blackTiles.contains(num)) {
            board.get(num).setColor(NORMAL_BLACK_COLOR);
            selectedTiles.remove(Integer.valueOf(num));
        }
    }

    public void onRender(Renderer renderer) {
        for (int i = 0; i < board.size(); i++) {
            renderer.texturedDraw(board.get(i));
        }
    }
}
