package me.raven.Sandbox.Game.Piece;

import me.raven.Engine.Listeners.Mouse;
import me.raven.Engine.Renderer.Renderer;
import me.raven.Engine.Shapes.Quad;
import me.raven.Engine.Utils.Texture;
import me.raven.Sandbox.Game.Colors;
import me.raven.Sandbox.Managers.GameManager;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.lwjgl.glfw.GLFW.*;

public abstract class Piece {

    public PieceData data;
    public Queue<Integer> moves;
    public List<Piece> preys;

    public boolean isSelected = false;
    public boolean isMovePressed = false;
    public boolean isPreyPressed = false;
    public boolean isSelectionPressed = false;

    public Piece(Vector2f scale, Texture texture, int value, PieceColors color, int tile) {
        this.moves = new ConcurrentLinkedQueue<>();
        this.preys = new ArrayList<>();
        this.data = new PieceData(
                new Quad(GameManager.get().getBoardManager().getBoard().get(tile).getTransform().position, scale, texture),
                tile,
                value,
                color);
    }

    public void onRender(Renderer renderer) {
        renderer.texturedDraw(data.piece);
    }

    public void onUpdate() {
        selectionChecker();
        moveChecker();
        preyChecker();
    }

    public void moveChecker() {
        if (Mouse.isPressed(GLFW_MOUSE_BUTTON_LEFT) && isSelected && !isMovePressed) {
            for (Integer tile : moves) {
                if (isInside(GameManager.get().getBoardManager().getBoard().get(tile), Mouse.getCursorPos().x, Mouse.getCursorPos().y)) {
                    movePiece(GameManager.get().getBoardManager().getBoard().get(tile).getTransform().position, tile);
                    break;
                }
            }
            isMovePressed = true;
        } else if (Mouse.isReleased(GLFW_MOUSE_BUTTON_LEFT) && isMovePressed) {
            isMovePressed = false;
        }
    }

    public void preyChecker() {
        if (Mouse.isPressed(GLFW_MOUSE_BUTTON_LEFT) && isSelected && !isPreyPressed) {
            for (Piece prey : preys) {
                if (isInside(prey.data.piece, Mouse.getCursorPos().x, Mouse.getCursorPos().y)) {
                    movePieceAndEat(prey, prey.data.piece.getTransform().position, prey.data.tile);
                    break;
                }
            }
            isPreyPressed = true;
        } else if (Mouse.isReleased(GLFW_MOUSE_BUTTON_LEFT) && isPreyPressed) {
            isPreyPressed = false;
        }
    }

    public void selectionChecker() {
        if (Mouse.isPressed(GLFW_MOUSE_BUTTON_LEFT) && !isMovePressed && !isSelectionPressed) {
            if (isInside(data.piece, Mouse.getCursorPos().x, Mouse.getCursorPos().y)) selection();

            isSelectionPressed = true;
        } else if (Mouse.isReleased(GLFW_MOUSE_BUTTON_LEFT) && isSelectionPressed) {
            isSelectionPressed = false;
        }
    }

    private void movePiece(Vector3f position, int tile) {
        data.updateData(position, tile);

        GameManager.get().getPieceManager().changeTurn();

        specialMove();

        unselect();
    }

    private void movePieceAndEat(Piece prey, Vector3f position, int tile) {
        data.updateData(position, tile);

        GameManager.get().getPieceManager().removePiece(prey);
        GameManager.get().getPieceManager().changeTurn();

        unselect();
    }

    public void addMove(int move) {
        this.moves.add(move);
    }

    public void addMoves(List<Integer> moves) {
        if (moves == null) return;

        this.moves.addAll(moves);
    }

    public void addPrey(Piece prey) {
        this.preys.add(prey);
    }

    public void addPreys(List<Piece> preys) {
        if (preys == null) return;

        this.preys.addAll(preys);
    }

    private void selection() {
        for (Piece piece : GameManager.get().getPieceManager().getPieces()) {
            if (piece == this) continue;

            if (piece.isSelected) return;
        }

        if (!isSelected) select();
        else unselect();
    }

    private void select() {
        data.updateData(Colors.PIECE_SELECTION.color);

        for (PieceDirections dir : PieceDirections.values()) {
            calculatePossibleMoves(dir);
            calculatePossiblePreys(dir);
        }

        highlightPossibleMoves();

        isSelected = true;
    }

    private void unselect() {
        data.updateData(Colors.RESET.color);

        unHighlightPossibleMoves();

        clearAllMoves();

        isSelected = false;
    }

    private void highlightPossibleMoves() {
        for (Integer move : moves) {
            GameManager.get().getBoardManager().getBoard().get(move).setColor(Colors.PIECE_SELECTION.color);
        }
        for (Piece prey : preys) {
            GameManager.get().getBoardManager().getBoard().get(prey.data.tile).setColor(Colors.PIECE_SELECTION.color);
        }
    }

    private void unHighlightPossibleMoves() {
        for (Integer move : moves) {
            GameManager.get().getBoardManager().getBoard().get(move).setColor(Colors.RESET.color);
        }
        for (Piece prey : preys) {
            GameManager.get().getBoardManager().getBoard().get(prey.data.tile).setColor(Colors.RESET.color);
        }
    }

    private void clearAllMoves() {
        moves.clear();
        preys.clear();
    }

    private boolean isInside(Quad quad, float x, float y) {
        return quad.getCollision().isInside(x, y);
    }

    public void isEmpty(int nextTile) {
        for (Piece temp : PieceManager.get().getPieces()) {
            if (temp.data.tile != nextTile) {
                if (moves.contains(nextTile)) return;
                addMove(nextTile);
                return;
            }
        }
    }

    public boolean hasAlly(int nextTile) {
        for (Piece temp : PieceManager.get().getPieces()) {
            if (temp.data.tile == nextTile && temp.data.color == data.color) {
                return true;
            }
        }
        return false;
    }

    public boolean hasEnemy(int nextTile) {
        for (Piece temp : PieceManager.get().getPieces()) {
            if (temp.data.tile == nextTile && temp.data.color != data.color) {
                return true;
            }
        }
        return false;
    }

    public boolean isEnemy(int nextTile) {
        for (Piece temp : PieceManager.get().getPieces()) {
            if (temp.data.tile == nextTile && temp.data.color != data.color) {
                if (preys.contains(temp)) return true;
                addPrey(temp);
                return true;
            }
        }
        return false;
    }

    protected abstract void specialMove();
    protected abstract void checkLooker();
    protected abstract void calculatePossibleMoves(PieceDirections dir);
    protected abstract void calculatePossiblePreys(PieceDirections dir);
}
