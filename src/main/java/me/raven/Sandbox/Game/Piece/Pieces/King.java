package me.raven.Sandbox.Game.Piece.Pieces;

import me.raven.Engine.Utils.Texture;
import me.raven.Sandbox.Game.Board.BoardManager;
import me.raven.Sandbox.Game.Piece.Piece;
import me.raven.Sandbox.Game.Piece.PieceColors;
import me.raven.Sandbox.Game.Piece.PieceDirections;
import me.raven.Sandbox.Managers.GameManager;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class King extends Piece {

    public Queue<Piece> checkedBy = new ConcurrentLinkedQueue<>();
    public boolean isChecked = false;

    public King(Vector2f scale, Texture texture, PieceColors pieceColor, int tile) {
        super(scale, texture, 1, pieceColor, tile);
    }

    @Override
    public void specialMove() {

    }

    @Override
    protected void checkLooker() {

    }

    public void setChecked(Piece piece) {
        System.out.println("CHECKED");
        checkedBy.add(piece);
        isChecked = true;
    }

    public void setUnChecked() {
        System.out.println("UNCHECKED");
        checkedBy.clear();
        isChecked = false;
    }

    private boolean isPieceLooking(int nextTile) {
        for (Piece piece : GameManager.get().getPieceManager().getPieces()) {
            if (piece.moves.contains(nextTile)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void calculatePossibleMoves(PieceDirections dir) {
        if (BoardManager.get().getTileCountToEdge(data.tile, dir) >= 1) {
            int nextTile = data.tile + dir.getValue();

            if (hasEnemy(nextTile)) return;
            if (hasAlly(nextTile)) return;
            if (isPieceLooking(nextTile)) return;

            isEmpty(nextTile);
        }
    }

    @Override
    protected void calculatePossiblePreys(PieceDirections dir) {
        if (BoardManager.get().getTileCountToEdge(data.tile, dir) >= 1) {
            int nextTile = data.tile + dir.getValue();

            if (hasAlly(nextTile)) return;
            isEnemy(nextTile);
        }
    }
}
