package me.raven.Sandbox.Game.Piece.Pieces;

import me.raven.Engine.Utils.Texture;
import me.raven.Sandbox.Game.Board.BoardManager;
import me.raven.Sandbox.Game.Piece.Piece;
import me.raven.Sandbox.Game.Piece.PieceColors;
import me.raven.Sandbox.Game.Piece.PieceDirections;
import me.raven.Sandbox.Game.Piece.PieceManager;
import org.joml.Vector2f;

public class Rock extends Piece {

    public boolean isFirstMove = true;

    public Rock(Vector2f scale, Texture texture, PieceColors pieceColor, int tile) {
        super(scale, texture, 5, pieceColor, tile);

        if (data.color == PieceColors.BLACK && tile != 56 && tile != 0) {
            isFirstMove = false;
        } else if (data.color == PieceColors.WHITE && tile != 7 && tile != 63) {
            isFirstMove = false;
        }
    }

    @Override
    public void specialMove(int nextTile) {
        if (isFirstMove)
            isFirstMove = false;
    }

    @Override
    public void calculatePossibleMoves(PieceDirections dir) {
        if (dir.isDiagonal()) return;

        for (int i = 1; i < BoardManager.get().getTileCountToEdge(tempTile, dir) + 1; i++) {
            int nextTile = tempTile + i * dir.getValue();

            if (PieceManager.get().hasAlly(this, nextTile)) return;
            if (PieceManager.get().hasEnemy(this, nextTile)) return;
            PieceManager.get().isEmpty(this, nextTile);
        }
    }

    @Override
    protected void calculatePossiblePreys(PieceDirections dir) {
        if (dir.isDiagonal()) return;

        for (int i = 1; i < BoardManager.get().getTileCountToEdge(tempTile, dir) + 1; i++) {
            int nextTile = tempTile + i * dir.getValue();

            if (PieceManager.get().hasAlly(this, nextTile)) return;
            if (PieceManager.get().isEnemy(this, nextTile)) return;
        }
    }
}
