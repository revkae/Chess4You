package me.raven.Sandbox.Game.Piece.Pieces;

import me.raven.Engine.Utils.Texture;
import me.raven.Sandbox.Game.Board.BoardManager;
import me.raven.Sandbox.Game.Piece.Piece;
import me.raven.Sandbox.Game.Piece.PieceColors;
import me.raven.Sandbox.Game.Piece.PieceDirections;
import org.joml.Vector2f;

public class Queen extends Piece {
    public Queen(Vector2f scale, Texture texture, PieceColors pieceColor, int tile) {
        super(scale, texture, 8, pieceColor, tile);
    }

    @Override
    public void specialMove() {

    }

    @Override
    protected void checkLooker() {

    }

    @Override
    protected void calculatePossibleMoves(PieceDirections dir) {
        for (int i = 1; i < BoardManager.get().getTileCountToEdge(data.tile, dir); i++) {
            int nextTile = data.tile + i * dir.getValue();

            if (hasAlly(nextTile)) return;
            if (hasEnemy(nextTile)) return;

            isEmpty(nextTile);
        }
    }

    @Override
    protected void calculatePossiblePreys(PieceDirections dir) {
        for (int i = 1; i < BoardManager.get().getTileCountToEdge(data.tile, dir); i++) {
            int nextTile = data.tile + i * dir.getValue();

            if (hasAlly(nextTile)) return;
            if (isEnemy(nextTile)) return;
        }
    }
}
