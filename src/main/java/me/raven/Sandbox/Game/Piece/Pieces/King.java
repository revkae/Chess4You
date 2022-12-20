package me.raven.Sandbox.Game.Piece.Pieces;

import me.raven.Engine.Utils.Texture;
import me.raven.Sandbox.Game.Board.BoardManager;
import me.raven.Sandbox.Game.Piece.Piece;
import me.raven.Sandbox.Game.Piece.PieceColors;
import me.raven.Sandbox.Game.Piece.PieceDirections;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class King extends Piece {

    public List<Piece> checkedBy = new ArrayList<>();
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

    @Override
    protected void calculatePossibleMoves(PieceDirections dir) {
        if (BoardManager.get().getTileCountToEdge(data.tile, dir) >= 1) {
            int nextTile = data.tile + dir.getValue();
            System.out.println(nextTile);

            if (hasEnemy(nextTile)) return;
            if (hasAlly(nextTile)) return;

            isEmpty(nextTile);
        }
    }

    @Override
    protected void calculatePossiblePreys(PieceDirections dir) {
        if (BoardManager.get().getTileCountToEdge(data.tile, dir) >= 1) {
            int nextTile = data.tile + dir.getValue();

            if (hasAlly(nextTile)) return;
            if (isEnemy(nextTile)) return;
        }
    }
}
