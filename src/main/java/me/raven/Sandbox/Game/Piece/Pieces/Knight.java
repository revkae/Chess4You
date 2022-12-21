package me.raven.Sandbox.Game.Piece.Pieces;

import me.raven.Engine.Utils.Texture;
import me.raven.Sandbox.Game.Board.BoardManager;
import me.raven.Sandbox.Game.Piece.*;
import org.joml.Vector2f;
import org.lwjgl.openal.SOFTDeferredUpdates;
import org.lwjgl.openal.SOFTOutputLimiter;

public class Knight extends Piece {

    public Knight(Vector2f scale, Texture texture, PieceColors pieceColor, int tile) {
        super(scale, texture, 3, pieceColor, tile);
    }

    @Override
    public void specialMove() {

    }

    @Override
    protected void checkLooker() {

    }

    private void calcEmptyMoves(PieceDirections one, PieceDirections two) {
        if (BoardManager.get().getTileCountToEdge(this.data.tile, two) >= 2
                && BoardManager.get().getTileCountToEdge(this.data.tile, one) >= 1) {
            int nextTile = data.tile + one.getValue() + two.getValue() * 2;

            if (hasEnemy(nextTile)) return;
            if (hasAlly(nextTile)) return;
            System.out.println("check contro: " + PieceManager.get().isKingChecked(data.color));
            if (PieceManager.get().isKingChecked(data.color) && PieceManager.get().canBlockByMove(nextTile, data.color)) {
                isEmpty(nextTile);
            } else if (!PieceManager.get().isKingChecked(data.color)) {
                isEmpty(nextTile);
            }
        }
    }

    private void calcEmptyPreys(PieceDirections one, PieceDirections two) {
        if (BoardManager.get().getTileCountToEdge(this.data.tile, two) >= 2
                && BoardManager.get().getTileCountToEdge(this.data.tile, one) >= 1) {
            int nextTile = data.tile + one.getValue() + two.getValue() * 2;

            addAttackMove(nextTile);
            if (hasAlly(nextTile)) return;
            if (PieceManager.get().isKingChecked(data.color) && PieceManager.get().canBlockByPrey(nextTile, data.color)) {
                isEnemy(nextTile);
            } else if (!PieceManager.get().isKingChecked(data.color)) {
                isEnemy(nextTile);
            }
        }
        clearAttackMoves();
    }

    @Override
    protected void calculatePossibleMoves(PieceDirections dir) {
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
