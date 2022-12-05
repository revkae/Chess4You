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
import java.util.concurrent.ConcurrentLinkedQueue;

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
    public Queue<Integer> possibleMoves;
    public List<Piece> possiblePreys;

    public boolean isSelected = false;

    public boolean isMovePressed = false;
    public boolean isPreyPressed = false;
    public boolean isSelectionPressed = false;

    public Piece(Vector2f scale, Texture texture, int value, PieceColor pieceColor, int tile) {
        this.board = GameManager.get().getBoardManager().getBoard();
        this.possibleMoves = new ConcurrentLinkedQueue<>();
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

        selectionChecker();
        moveChecker();
        preyChecker();

    }

    public abstract void specialMove();

    public void moveChecker() {
        if (MouseListener.isPressed(GLFW_MOUSE_BUTTON_LEFT) && isSelected && !isMovePressed) {
            for (Integer tile : possibleMoves) {
                if (board.get(tile).getCollision().isInside(new Vector3f(MouseListener.getCursorPos().x, MouseListener.getCursorPos().y, 0.0f))) {
                    movePiece(board.get(tile).getTransform().position, tile);
                    break;
                }
            }
            isMovePressed = true;
        } else if (MouseListener.isReleased(GLFW_MOUSE_BUTTON_LEFT) && isMovePressed) {
            isMovePressed = false;
        }
    }

    public void preyChecker() {
        if (MouseListener.isPressed(GLFW_MOUSE_BUTTON_LEFT) && isSelected && !isPreyPressed) {
            for (Piece prey : possiblePreys) {
                if (prey.getPiece().getCollision().isInside(new Vector3f(MouseListener.getCursorPos().x, MouseListener.getCursorPos().y, 0.0f))) {
                    movePieceAndEat(prey, prey.getPiece().getTransform().position, prey.tile);
                    break;
                }
            }
            isPreyPressed = true;
        } else if (MouseListener.isReleased(GLFW_MOUSE_BUTTON_LEFT) && isPreyPressed) {
            isPreyPressed = false;
        }
    }

    public void selectionChecker() {
        if (MouseListener.isPressed(GLFW_MOUSE_BUTTON_LEFT) && !isMovePressed && !isSelectionPressed) {
            if (piece.getCollision().isInside(new Vector3f(MouseListener.getCursorPos().x, MouseListener.getCursorPos().y, 0.0f))) {
                if (!isSelected) {
                    select();
                } else {
                    unselect();
                }
            }
            isSelectionPressed = true;
        } else if (MouseListener.isReleased(GLFW_MOUSE_BUTTON_LEFT) && isSelectionPressed) {
            isSelectionPressed = false;
        }
    }

    public boolean isMovePossible(int move) {
        if (move >= board.size() || move < 0) {
            return false;
        }
        for (Piece tempPiece : getPieceManager().getPieces()) {
            if (move == tempPiece.tile) {
                return false;
            }
        }
        for (Integer topTile : getBoardManager().getTopTiles()) {
            if (move == topTile + 1) {
                return false;
            }
        }
        return true;
    }

    public boolean isPreyPossible(int move) {
        if (move >= board.size() || move < 0) {
            return false;
        }
        for (Piece tempPiece : getPieceManager().getPieces()) {
            if (tempPiece.pieceColor == pieceColor) continue;

            if (tempPiece.tile == move) {
                return true;
            }
        }
        return false;
    }

    private void movePiece(Vector3f position, int tile) {
        piece.setPosition(position);
        this.tile = tile;
        getPieceManager().changeTurn();
        unselect();
    }

    private void movePieceAndEat(Piece prey, Vector3f position, int tile) {
        piece.setPosition(position);
        this.tile = tile;
        GameManager.get().getPieceManager().removePiece(prey);
        getPieceManager().changeTurn();
        unselect();
    }

    private void select() {
        piece.setColor(SELECTED_COLOR);
        isSelected = true;

        calculatePossibleMoves();
        calculatePossiblePreys();

        highlightPossibleMoves();
    }

    private void unselect() {
        piece.setColor(NORMAL_COLOR);
        isSelected = false;

        unHighlightPossibleMoves();

        possibleMoves.clear();
        possiblePreys.clear();
    }

    private void highlightPossibleMoves() {
        for (Integer move : possibleMoves) {
            getBoardManager().getBoard().get(move).setColor(SELECTED_COLOR);
        }
        for (Piece prey : possiblePreys) {
            getBoardManager().getBoard().get(prey.tile).setColor(SELECTED_COLOR);
        }
    }

    private void unHighlightPossibleMoves() {
        for (Integer move : possibleMoves) {
            getBoardManager().getBoard().get(move).setColor(NORMAL_COLOR);
        }
        for (Piece prey : possiblePreys) {
            getBoardManager().getBoard().get(prey.tile).setColor(NORMAL_COLOR);
        }
    }

    public void addMove(int num) {
        if (isMovePossible(tile + num)) {
            possibleMoves.add(tile + num);
        }
    }

    public void addPrey(int num) {
        if (isPreyPossible(num + tile)) {
            for (Piece tempPiece : getPieceManager().getPieces()) {
                if (tempPiece.tile == num + tile) {
                    possiblePreys.add(tempPiece);
                    break;
                }
            }
        }
    }

    public Quad getPiece() {
        return piece;
    }

    public Queue<Integer> getPossibleMoves() {
        return possibleMoves;
    }

    public List<Piece> getPossiblePreys() {
        return possiblePreys;
    }

    public PieceManager getPieceManager() {
        return GameManager.get().getPieceManager();
    }

    public BoardManager getBoardManager() {
        return GameManager.get().getBoardManager();
    }

    protected abstract void calculatePossibleMoves();
    protected abstract void calculatePossiblePreys();
}
