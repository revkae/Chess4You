package me.raven.Sandbox.Game.Piece.Pieces;

import me.raven.Engine.Utils.Texture;
import me.raven.Sandbox.Game.Board.BoardManager;
import me.raven.Sandbox.Game.Piece.Piece;
import me.raven.Sandbox.Game.Piece.PieceColors;
import me.raven.Sandbox.Game.Piece.PieceDirections;
import me.raven.Sandbox.Game.Piece.PieceManager;
import org.joml.Vector2f;

public class Pawn extends Piece {

    public boolean isFirstMove = true;

    public Pawn(Vector2f scale, Texture texture, PieceColors color, int tile) {
        super(scale, texture, 1, color, tile);

        if (BoardManager.get().getTileCountToEdge(tempTile, PieceDirections.SOUTH) != 1 && data.color == PieceColors.BLACK) {
            isFirstMove = false;
        } else if (BoardManager.get().getTileCountToEdge(tempTile, PieceDirections.NORTH) != 1 && data.color == PieceColors.WHITE) {
            isFirstMove = false;
        }
    }

    @Override
    public void specialMove(int nextTile) {
        if (isFirstMove)
            isFirstMove = false;
        if (BoardManager.get().getTileCountToEdge(tempTile, PieceDirections.NORTH) == 0 && data.color == PieceColors.BLACK) {
            PieceManager.get().getPieces().remove(this);
            PieceManager.get().addQueen(data.color, tempTile);
        }
        else if (BoardManager.get().getTileCountToEdge(tempTile, PieceDirections.SOUTH) == 0 && data.color == PieceColors.WHITE) {
            PieceManager.get().getPieces().remove(this);
            PieceManager.get().addQueen(data.color, tempTile);
        }
    }

    private void calcEmptyMoves(PieceDirections dir) {
        if (BoardManager.get().getTileCountToEdge(tempTile, dir) != 0) {
            if (isFirstMove) {
                int nextTile = tempTile + dir.getValue();
                int nextTile2 = tempTile + 2 * dir.getValue();

                if (!PieceManager.get().hasAlly(this, nextTile) && !PieceManager.get().hasEnemy(this, nextTile)) {
                    PieceManager.get().isEmpty(this, nextTile);
                }
                if (!PieceManager.get().hasAlly(this, nextTile) && !PieceManager.get().hasEnemy(this, nextTile) &&
                        !PieceManager.get().hasAlly(this, nextTile2) && !PieceManager.get().hasEnemy(this, nextTile2)) {
                    PieceManager.get().isEmpty(this, nextTile2);
                }
            } else {
                int nextTile = tempTile + dir.getValue();

                if (!PieceManager.get().hasAlly(this, nextTile) && !PieceManager.get().hasEnemy(this, nextTile)) {
                    PieceManager.get().isEmpty(this, nextTile);
                }
            }
        }
    }

    private void calcEmptyPreys(PieceDirections dir) {
        if (BoardManager.get().getTileCountToEdge(data.tile, dir) != 0) {
            int nextTile = data.tile + dir.getValue();

            if (PieceManager.get().hasAlly(this, nextTile)) return;
            PieceManager.get().isEnemy(this, nextTile);
        }
    }

    @Override
    public void calculatePossibleMoves(PieceDirections dir) {
        if (data.color == PieceColors.WHITE)
            calcEmptyMoves(PieceDirections.SOUTH);
        else
            calcEmptyMoves(PieceDirections.NORTH);

    }

    @Override
    protected void calculatePossiblePreys(PieceDirections dir) {
        if (data.color == PieceColors.WHITE) {
            calcEmptyPreys(PieceDirections.SOUTH_EAST);
            calcEmptyPreys(PieceDirections.SOUTH_WEST);
        } else {
            calcEmptyPreys(PieceDirections.NORTH_EAST);
            calcEmptyPreys(PieceDirections.NORTH_WEST);
        }
    }

}
