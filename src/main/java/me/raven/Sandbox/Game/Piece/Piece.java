package me.raven.Sandbox.Game.Piece;

import me.raven.Engine.Listeners.Mouse;
import me.raven.Engine.Renderer.Renderer;
import me.raven.Engine.Shapes.Quad;
import me.raven.Engine.Utils.Texture;
import me.raven.Sandbox.Game.Colors;
import me.raven.Sandbox.Game.Piece.Pieces.King;
import me.raven.Sandbox.Managers.GameManager;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import static org.lwjgl.glfw.GLFW.*;

public abstract class Piece {

    public PieceData data;
    public Queue<Integer> moves;
    public Queue<Piece> preys;

    public int tempTile;

    public boolean isSelected = false;
    public boolean isMovePressed = false;
    public boolean isPreyPressed = false;
    public boolean isSelectionPressed = false;

    public Piece(Vector2f scale, Texture texture, int value, PieceColors color, int tile) {
        this.moves = new ConcurrentLinkedQueue<>();
        this.preys = new ConcurrentLinkedQueue<>();
        this.data = new PieceData(
                new Quad(GameManager.get().getBoardManager().getBoard().get(tile).getTransform().position, scale, texture),
                tile,
                value,
                color);
        this.tempTile = tile;
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

    public void tryMove(int nextTile) {
        tempTile = nextTile;
    }

    public void invertMove() {
        tempTile = data.tile;
    }

    private void movePiece(Vector3f position, int tile) {
        King king = PieceManager.get().getKingByColor(data.color);
        data.updateData(position, tile);
        tempTile = data.tile;
        GameManager.get().getPieceManager().changeTurn();
        king.isChecked = false;
        king.checkedBy.clear();
        specialMove();
        unselect();
    }

    private void movePieceAndEat(Piece prey, Vector3f position, int tile) {
        King king = PieceManager.get().getKingByColor(data.color);
        data.updateData(position, tile);
        tempTile = data.tile;

        GameManager.get().getPieceManager().removePiece(prey);
        GameManager.get().getPieceManager().changeTurn();
        king.isChecked = false;
        king.checkedBy.clear();
        unselect();
    }

    public void addMove(int move) {
        this.moves.add(move);
    }

    public void addPrey(Piece prey) {
        this.preys.add(prey);
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
        clearAllMoves();

        for (PieceDirections dir : PieceDirections.values()) {
            calculatePossibleMoves(dir);
            calculatePossiblePreys(dir);
        }
        legalMoveControl();

        highlightPossibleMoves();

        isSelected = true;
    }

    private void unselect() {
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

        data.updateData(Colors.PIECE_SELECTION.color);
    }

    private void unHighlightPossibleMoves() {
        for (Integer move : moves) {
            GameManager.get().getBoardManager().getBoard().get(move).setColor(Colors.RESET.color);
        }
        for (Piece prey : preys) {
            GameManager.get().getBoardManager().getBoard().get(prey.data.tile).setColor(Colors.RESET.color);
        }
        data.updateData(Colors.RESET.color);
    }

    private void clearAllMoves() {
        moves.clear();
        preys.clear();
    }

    private boolean isInside(Quad quad, float x, float y) {
        return quad.getCollision().isInside(x, y);
    }

    private void legalMoveControl() {
        King king = PieceManager.get().getKingByColor(data.color);

        if (king.isChecked) {
            for (Piece piece : PieceManager.get().getPiecesByColor(data.color)) {
                for (PieceDirections dir : PieceDirections.values()) {
                    piece.calculatePossibleMoves(dir);
                    piece.calculatePossiblePreys(dir);
                }
                for (Integer move : piece.moves) {
                    piece.tryMove(move);
                    for (Piece checked : king.checkedBy) {
                        for (PieceDirections dir : PieceDirections.values()) {
                            checked.calculatePossiblePreys(dir);
                        }
                        if (checked.preys.contains(king)) {
                            piece.moves.remove(move);
                        }
                        checked.preys.clear();
                    }
                    for (Piece checked : PieceManager.get().getPiecesByColor(PieceColors.getOpposite(data.color))) {
                        for (PieceDirections dir : PieceDirections.values()) {
                            checked.calculatePossiblePreys(dir);
                        }
                        if (checked.preys.contains(king)) {
                            piece.moves.remove(move);
                        }
                        checked.preys.clear();
                    }
                    piece.invertMove();
                }

                for (Piece prey : piece.preys) {
                    for (Piece checked : king.checkedBy) {
                        if (!checked.equals(prey)) {
                            preys.remove(prey);
                        }
                    }
                }

                for (Piece prey : piece.preys) {
                    piece.tryMove(prey.tempTile);
                    prey.tryMove(50);

                    for (Piece opposite : PieceManager.get().getPiecesByColor(PieceColors.getOpposite(data.color))) {
                        for (PieceDirections dir : PieceDirections.values()) {
                            opposite.calculatePossiblePreys(dir);
                        }
                        if (opposite.preys.contains(king)) {
                            piece.preys.remove(prey);
                        }
                        opposite.preys.clear();
                    }

                    prey.invertMove();
                    piece.invertMove();
                }
            }

        } else {
            for (Piece piece : PieceManager.get().getPiecesByColor(data.color)) {
                for (PieceDirections dir : PieceDirections.values()) {
                    piece.calculatePossibleMoves(dir);
                    piece.calculatePossiblePreys(dir);
                }
                for (Integer move : piece.moves) {
                    piece.tryMove(move);
                    for (Piece opposite : PieceManager.get().getPiecesByColor(PieceColors.getOpposite(data.color))) {
                        for (PieceDirections dir : PieceDirections.values()) {
                            opposite.calculatePossiblePreys(dir);
                        }
                        if (opposite.preys.contains(king)) {
                            piece.moves.remove(move);
                        }
                        opposite.preys.clear();
                    }
                    piece.invertMove();
                }

                for (Piece prey : piece.preys) {
                    piece.tryMove(prey.tempTile);
                    prey.tryMove(50);

                    for (Piece opposite : PieceManager.get().getPiecesByColor(PieceColors.getOpposite(data.color))) {
                        for (PieceDirections dir : PieceDirections.values()) {
                            opposite.calculatePossiblePreys(dir);
                        }
                        if (opposite.preys.contains(king)) {
                            piece.preys.remove(prey);
                        }
                        opposite.preys.clear();
                    }

                    prey.invertMove();
                    piece.invertMove();
                }
            }
        }
    }

    protected abstract void specialMove();
    public abstract void calculatePossibleMoves(PieceDirections dir);
    protected abstract void calculatePossiblePreys(PieceDirections dir);
}
