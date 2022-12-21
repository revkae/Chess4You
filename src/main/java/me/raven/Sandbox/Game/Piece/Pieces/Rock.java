package me.raven.Sandbox.Game.Piece.Pieces;

import me.raven.Engine.Utils.Texture;
import me.raven.Sandbox.Game.Board.BoardManager;
import me.raven.Sandbox.Game.Piece.Piece;
import me.raven.Sandbox.Game.Piece.PieceColors;
import me.raven.Sandbox.Game.Piece.PieceDirections;
import me.raven.Sandbox.Game.Piece.PieceManager;
import org.joml.Vector2f;

public class Rock extends Piece {
    public Rock(Vector2f scale, Texture texture, PieceColors pieceColor, int tile) {
        super(scale, texture, 5, pieceColor, tile);
    }

    @Override
    public void specialMove() {

    }

    @Override
    protected void checkLooker() {

    }

    @Override
    protected void calculatePossibleMoves(PieceDirections dir) {
        if (dir.isDiagonal()) return;

        for (int i = 1; i < BoardManager.get().getTileCountToEdge(data.tile, dir) + 1; i++) {
            int nextTile = data.tile + i * dir.getValue();

            if (hasAlly(nextTile)) return;
            if (hasEnemy(nextTile)) return;
            if (PieceManager.get().isKingChecked(data.color) && PieceManager.get().canBlockByMove(nextTile, data.color)) {
                isEmpty(nextTile);
                return;
            } else if (!PieceManager.get().isKingChecked(data.color)) {
                isEmpty(nextTile);
            }
        }
    }

    @Override
    protected void calculatePossiblePreys(PieceDirections dir) {
        if (dir.isDiagonal()) return;

        for (int i = 1; i < BoardManager.get().getTileCountToEdge(data.tile, dir) + 1; i++) {
            int nextTile = data.tile + i * dir.getValue();

            addAttackMove(nextTile);
            if (hasAlly(nextTile)) return;
            if (PieceManager.get().isKingChecked(data.color) && PieceManager.get().canBlockByPrey(nextTile, data.color)) {
                isEnemy(nextTile);
                return;
            } else if (!PieceManager.get().isKingChecked(data.color)) {
                isEnemy(nextTile);
            }
        }
        clearAttackMoves();
    }
}
