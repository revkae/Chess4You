package me.raven.Sandbox.Game.Piece.Pieces;

import me.raven.Engine.Utils.Texture;
import me.raven.Sandbox.Game.Board.BoardManager;
import me.raven.Sandbox.Game.Piece.Piece;
import me.raven.Sandbox.Game.Piece.PieceColors;
import me.raven.Sandbox.Game.Piece.PieceDirections;
import me.raven.Sandbox.Game.Piece.PieceManager;
import org.joml.Vector2f;

public class Queen extends Piece {
    public Queen(Vector2f scale, Texture texture, PieceColors pieceColor, int tile) {
        super(scale, texture, 8, pieceColor, tile);
    }

    @Override
    public void specialMove(int nextTile) {

    }

    @Override
    public void calculatePossibleMoves(PieceDirections dir) {
        for (int i = 1; i < BoardManager.get().getTileCountToEdge(tempTile, dir) + 1; i++) {
            int nextTile = tempTile + i * dir.getValue();

            if (PieceManager.get().hasAlly(this, nextTile)) return;
            if (PieceManager.get().hasEnemy(this, nextTile)) return;
            PieceManager.get().isEmpty(this, nextTile);
        }
    }

    @Override
    protected void calculatePossiblePreys(PieceDirections dir) {
        for (int i = 1; i < BoardManager.get().getTileCountToEdge(tempTile, dir) + 1; i++) {
            int nextTile = tempTile + i * dir.getValue();

            if (PieceManager.get().hasAlly(this, nextTile)) return;
            if (PieceManager.get().isEnemy(this, nextTile)) return;
        }
    }
}
