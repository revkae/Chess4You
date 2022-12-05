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
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.lwjgl.glfw.GLFW.*;

public class BoardManager {

    private final Vector4f SELECTED_TILE_COLOR = new Vector4f(1.f, 0.f, 0.f, 1.f);
    private final Vector4f NORMAL_BLACK_COLOR = new Vector4f(0.f, 0.f, 0.f, 1.f);
    private final Vector4f NORMAL_WHITE_COLOR = new Vector4f(1.f);
    private List<Integer> whiteTiles = new ArrayList<>(32);
    private List<Integer> blackTiles = new ArrayList<>(32);
    private List<Quad> board = new ArrayList<>(64);
    private Queue<Integer> selectedTiles = new ConcurrentLinkedQueue<>();

    private List<Integer> topTiles = new ArrayList<>();
    private List<Integer> bottomTiles = new ArrayList<>();
    private List<Integer> rightTiles = new ArrayList<>();
    private List<Integer> leftTiles = new ArrayList<>();

    private boolean isRightPressed = false;
    private boolean isLeftPressed = false;

    public BoardManager() {
        Texture whiteTexture = new Texture("resources/awesomeface.jpg");
        Texture blackTexture = new Texture("resources/wall.jpg");
        int num = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((i + j) % 2 == 0) {
                    whiteTiles.add(num);
                    board.add(new Quad(new Vector3f(0.f + i * 50.f, 0.f + j * 50.f, 0.0f), new Vector2f(45.f, 45.f), whiteTexture));
                }
                else {
                    blackTiles.add(num);
                    board.add(new Quad(new Vector3f(0.f + i * 50.f, 0.f + j * 50.f, 0.0f), new Vector2f(45.f, 45.f), blackTexture));
                }
                num++;
            }
        }
        for (int i = 1; i < 9; i++) {
            topTiles.add((8 * i) - 1);
        }

        for (int i = 1; i < 8; i++) {
            bottomTiles.add((8 * i));
        }

        for (int i = 0; i < 8; i++) {
            leftTiles.add(i);
        }

        for (int i = 0; i < 8; i++) {
            rightTiles.add(63 - i);
        }
    }

    public void onUpdate() {
        if (MouseListener.isPressed(GLFW_MOUSE_BUTTON_RIGHT) && !isRightPressed) {
            for (int i = 0; i < board.size(); i++) {
                Quad quad = board.get(i);
                if (quad.getCollision().isInside(new Vector3f(MouseListener.getCursorPos().x, MouseListener.getCursorPos().y, 0.0f))) {
                    if (quad.getColor().equals(SELECTED_TILE_COLOR)) {
                        unselectTile(i);
                    } else {
                        System.out.println(i);
                        selectTile(i);
                    }
                }
            }
            isRightPressed = true;
        }
        else if (MouseListener.isReleased(GLFW_MOUSE_BUTTON_RIGHT) && isRightPressed) {
            isRightPressed = false;
        }
        else if (MouseListener.isPressed(GLFW_MOUSE_BUTTON_LEFT) && !isLeftPressed) {
            unselectAllTiles();
            isLeftPressed = true;
        }
        else if (MouseListener.isReleased(GLFW_MOUSE_BUTTON_LEFT) && isLeftPressed) {
            isLeftPressed = false;
        }
    }

    private void selectTile(int num) {
        board.get(num).setColor(SELECTED_TILE_COLOR);
        selectedTiles.add(num);
    }

    private void unselectTile(int num) {
        if (!selectedTiles.contains(num)) return;

        board.get(num).setColor(NORMAL_WHITE_COLOR);
        selectedTiles.remove(Integer.valueOf(num));
    }

    private void unselectAllTiles() {
        for (Integer tile : selectedTiles) {
            board.get(tile).setColor(NORMAL_WHITE_COLOR);
            selectedTiles.remove(tile);
        }
    }

    public void onRender(Renderer renderer) {
        for (Quad quad : board) {
            renderer.texturedDraw(quad);
        }
    }

    public List<Quad> getBoard() {
        return board;
    }

    public List<Integer> getTopTiles() {
        return topTiles;
    }
}
