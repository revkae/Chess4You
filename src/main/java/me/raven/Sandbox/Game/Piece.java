package me.raven.Sandbox.Game;

import me.raven.Engine.Listeners.MouseListener;
import me.raven.Engine.Renderer.Renderer;
import me.raven.Engine.Shapes.Quad;
import me.raven.Engine.Utils.Texture;
import me.raven.Sandbox.Managers.GameManager;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import static org.lwjgl.glfw.GLFW.*;

public abstract class Piece {

    public final int MOVE_LEFT = -8;
    public final int MOVE_RIGHT = 8;
    public final int MOVE_FORWARD = 1;
    public final int MOVE_BACKWARD = -1;
    public final Vector4f NORMAL_COLOR = new Vector4f(1.f);
    public final Vector4f SELECTED_COLOR = new Vector4f(0.f, 1.f, 0.f, 1.f);

    public Quad piece;
    public int tile;
    public int value;
    public PieceColor pieceColor;

    public List<Quad> board;
    public List<Integer> possibleMoves;
    public List<Piece> possiblePreys;

    public boolean isSelected = false;

    public Piece(Vector2f scale, Texture texture, int value, PieceColor pieceColor, int tile) {
        this.board = GameManager.get().getBoardManager().getBoard();
        this.possibleMoves = new ArrayList<>();
        this.possiblePreys = new ArrayList<>();
        this.piece = new Quad(board.get(tile).getTransform().position, scale, texture);
        this.value = value;
        this.pieceColor = pieceColor;
        this.tile = tile;
    }

    public void onRender(Renderer renderer) {
        renderer.texturedDraw(piece);
    }

    public void onUpdate() {

        moveChecker();
        preyChecker();
        selectionChecker();
    }

    public abstract void specialMove();

    public void moveChecker() {
        if (MouseListener.isPressed(GLFW_MOUSE_BUTTON_LEFT) && isSelected) {
            for (Integer tile : possibleMoves) {
                if (board.get(tile).getCollision().isInside(new Vector3f(MouseListener.getCursorPos().x, MouseListener.getCursorPos().y, 0.0f))) {
                    movePiece(board.get(tile).getTransform().position);
                    specialMove();
                }
            }
        }
    }

    public void preyChecker() {
        if (MouseListener.isPressed(GLFW_MOUSE_BUTTON_LEFT) && isSelected) {
            for (Piece prey : possiblePreys) {
                if (prey.getPiece().getCollision().isInside(new Vector3f(MouseListener.getCursorPos().x, MouseListener.getCursorPos().y, 0.0f))) {
                    GameManager.get().getPieceManager().removePiece(prey);
                }
            }
        }
    }

    public void selectionChecker() {
        if (MouseListener.isPressed(GLFW_MOUSE_BUTTON_LEFT)) {
            System.out.println("hello");
            if (piece.getCollision().isInside(new Vector3f(MouseListener.getCursorPos().x, MouseListener.getCursorPos().y, 0.0f))) {
                if (!isSelected) {
                    System.out.println("select");
                    select();
                } else {
                    System.out.println("unselect");
                    unselect();
                }
            }
        }
    }

    private void movePiece(Vector3f position) {
        piece.setPosition(position);
        unselect();
    }

    private void select() {
        piece.setColor(SELECTED_COLOR);
        isSelected = true;

        calculatePossibleMoves();
        calculatePossiblePreys();
    }

    private void unselect() {
        piece.setColor(NORMAL_COLOR);
        isSelected = false;
        possibleMoves.clear();
        possiblePreys.clear();
    }

    public Quad getPiece() {
        return piece;
    }

    public List<Integer> getPossibleMoves() {
        return possibleMoves;
    }

    public List<Piece> getPossiblePreys() {
        return possiblePreys;
    }

    public PieceManager getPieceManager() {
        return GameManager.get().getPieceManager();
    }

    protected abstract void calculatePossibleMoves();
    protected abstract void calculatePossiblePreys();
}
