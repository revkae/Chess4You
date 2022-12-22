package me.raven.Sandbox.Game.Piece.Pieces;

import me.raven.Engine.Utils.Texture;
import me.raven.Sandbox.Game.Board.BoardManager;
import me.raven.Sandbox.Game.Piece.Piece;
import me.raven.Sandbox.Game.Piece.PieceColors;
import me.raven.Sandbox.Game.Piece.PieceDirections;
import me.raven.Sandbox.Game.Piece.PieceManager;
import org.joml.Vector2f;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class King extends Piece {

    public Queue<Integer> blockedMoves = new ConcurrentLinkedQueue<>();
    public Queue<Piece> checkedBy = new ConcurrentLinkedQueue<>();
    public boolean isChecked = false;

    public King(Vector2f scale, Texture texture, PieceColors pieceColor, int tile) {
        super(scale, texture, 1, pieceColor, tile);
    }

    @Override
    public void specialMove() {

    }

    @Override
    public void calculatePossibleMoves(PieceDirections dir) {
        if (BoardManager.get().getTileCountToEdge(tempTile, dir) >= 1) {
            int nextTile = tempTile + dir.getValue();

            if (PieceManager.get().hasEnemy(this, nextTile)) return;
            if (PieceManager.get().hasAlly(this, nextTile)) return;

            PieceManager.get().isEmpty(this, nextTile);
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
