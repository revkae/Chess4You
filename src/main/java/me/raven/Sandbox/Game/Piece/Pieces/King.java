package me.raven.Sandbox.Game.Piece.Pieces;

import me.raven.Engine.Utils.Texture;
import me.raven.Sandbox.Game.Board.BoardManager;
import me.raven.Sandbox.Game.Piece.Piece;
import me.raven.Sandbox.Game.Piece.PieceColors;
import me.raven.Sandbox.Game.Piece.PieceDirections;
import me.raven.Sandbox.Game.Piece.PieceManager;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class King extends Piece {

    public Queue<Piece> checkedBy = new ConcurrentLinkedQueue<>();
    public boolean isChecked = false;
    public List<Integer> castleTiles = Arrays.asList(16, 48, 23, 55);
    public boolean isFirstMove = true;

    public King(Vector2f scale, Texture texture, PieceColors pieceColor, int tile) {
        super(scale, texture, 1, pieceColor, tile);
        if (data.color == PieceColors.WHITE && tile != 39) {
            isFirstMove = false;
        } else if (data.color == PieceColors.BLACK && tile != 32) {
            isFirstMove = false;
        }
    }

    @Override
    public void specialMove(int nextTile) {
        if (isFirstMove)
            isFirstMove = false;

        if (data.color == PieceColors.BLACK) {
            if (nextTile == castleTiles.get(0)) {
                PieceManager.get().removePiece(PieceManager.get().getRookByColor(data.color).get(0));
                PieceManager.get().addRook(PieceColors.BLACK, 24);
            } else if (nextTile == castleTiles.get(1)) {
                PieceManager.get().removePiece(PieceManager.get().getRookByColor(data.color).get(1));
                PieceManager.get().addRook(PieceColors.BLACK, 40);
            }
        } else {
            if (nextTile == castleTiles.get(2)) {
                PieceManager.get().removePiece(PieceManager.get().getRookByColor(data.color).get(0));
                PieceManager.get().addRook(PieceColors.WHITE, 31);
            } else if (nextTile == castleTiles.get(3)) {
                PieceManager.get().removePiece(PieceManager.get().getRookByColor(data.color).get(1));
                PieceManager.get().addRook(PieceColors.WHITE, 47);
            }
        }

    }

    private boolean isLeftClear() {
        if (PieceManager.get().getRookByColor(data.color) == null) return false;
        Rock leftRock = PieceManager.get().getRookByColor(data.color).get(0);
        if (leftRock == null) return false;
        if (!leftRock.isFirstMove) return false;

        for (int i = 1; i < BoardManager.get().getTileCountToEdge(tempTile, PieceDirections.WEST) - 1; i++) {
            int nextTile = tempTile + PieceDirections.WEST.getValue() * i;

            if (PieceManager.get().hasAlly(this, nextTile)) return false;
        }
        return true;
    }

    private boolean isRightClear() {
        if (PieceManager.get().getRookByColor(data.color) == null) return false;
        Rock rightRock = PieceManager.get().getRookByColor(data.color).get(1);
        if (rightRock == null) return false;
        if (!rightRock.isFirstMove) return false;

        for (int i = 1; i < BoardManager.get().getTileCountToEdge(tempTile, PieceDirections.EAST) - 1; i++) {
            int nextTile = tempTile + PieceDirections.EAST.getValue() * i;

            if (PieceManager.get().hasAlly(this, nextTile)) return false;
        }
        return true;
    }

    @Override
    public void calculatePossibleMoves(PieceDirections dir) {
        if (BoardManager.get().getTileCountToEdge(tempTile, dir) >= 1) {
            int nextTile = tempTile + dir.getValue();

            if (PieceManager.get().hasEnemy(this, nextTile)) return;
            if (PieceManager.get().hasAlly(this, nextTile)) return;

            PieceManager.get().isEmpty(this, nextTile);
        }
        if (isFirstMove) {
            if (data.color == PieceColors.BLACK) {
                if (isLeftClear()) {
                    addMove(castleTiles.get(0));
                }
                if (isRightClear()) {
                    addMove(castleTiles.get(1));
                }
            } else {
                if (isLeftClear()) {
                    addMove(castleTiles.get(2));
                }
                if (isRightClear()) {
                    addMove(castleTiles.get(3));
                }
            }
        }
    }

    @Override
    protected void calculatePossiblePreys(PieceDirections dir) {
        if (BoardManager.get().getTileCountToEdge(tempTile, dir) >= 1) {
            int nextTile = tempTile + dir.getValue();

            if (PieceManager.get().hasAlly(this, nextTile)) return;

            PieceManager.get().isEnemy(this, nextTile);
        }
    }
}
