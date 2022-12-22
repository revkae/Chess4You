package me.raven.Sandbox.Game.Piece.Pieces;

import me.raven.Engine.Utils.Texture;
import me.raven.Sandbox.Game.Board.BoardManager;
import me.raven.Sandbox.Game.Piece.*;
import org.joml.Vector2f;

public class Knight extends Piece {

    public Knight(Vector2f scale, Texture texture, PieceColors pieceColor, int tile) {
        super(scale, texture, 3, pieceColor, tile);
    }

    @Override
    public void specialMove(int nextTile) {

    }

    private void calcEmptyMoves(PieceDirections one, PieceDirections two) {
        if (BoardManager.get().getTileCountToEdge(this.tempTile, two) >= 2
                && BoardManager.get().getTileCountToEdge(this.tempTile, one) >= 1) {
            int nextTile = tempTile + one.getValue() + two.getValue() * 2;

            if (PieceManager.get().hasEnemy(this, nextTile)) return;
            if (PieceManager.get().hasAlly(this, nextTile)) return;
            PieceManager.get().isEmpty(this, nextTile);
        }
    }

    private void calcEmptyPreys(PieceDirections one, PieceDirections two) {
        if (BoardManager.get().getTileCountToEdge(this.tempTile, two) >= 2
                && BoardManager.get().getTileCountToEdge(this.tempTile, one) >= 1) {
            int nextTile = tempTile + one.getValue() + two.getValue() * 2;

            if (PieceManager.get().hasAlly(this, nextTile)) return;
            PieceManager.get().isEnemy(this, nextTile);
        }
    }

    @Override
    public void calculatePossibleMoves(PieceDirections dir) {
        calcEmptyMoves(PieceDirections.EAST, PieceDirections.NORTH);
        calcEmptyMoves(PieceDirections.WEST, PieceDirections.NORTH);
        calcEmptyMoves(PieceDirections.SOUTH, PieceDirections.WEST);
        calcEmptyMoves(PieceDirections.NORTH, PieceDirections.WEST);
        calcEmptyMoves(PieceDirections.WEST, PieceDirections.SOUTH);
        calcEmptyMoves(PieceDirections.EAST, PieceDirections.SOUTH);
        calcEmptyMoves(PieceDirections.NORTH, PieceDirections.EAST);
        calcEmptyMoves(PieceDirections.SOUTH, PieceDirections.EAST);
    }

    @Override
    protected void calculatePossiblePreys(PieceDirections dir) {
        calcEmptyPreys(PieceDirections.EAST, PieceDirections.NORTH);
        calcEmptyPreys(PieceDirections.WEST, PieceDirections.NORTH);
        calcEmptyPreys(PieceDirections.SOUTH, PieceDirections.WEST);
        calcEmptyPreys(PieceDirections.NORTH, PieceDirections.WEST);
        calcEmptyPreys(PieceDirections.WEST, PieceDirections.SOUTH);
        calcEmptyPreys(PieceDirections.EAST, PieceDirections.SOUTH);
        calcEmptyPreys(PieceDirections.NORTH, PieceDirections.EAST);
        calcEmptyPreys(PieceDirections.SOUTH, PieceDirections.EAST);
    }
}
