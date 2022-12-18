package me.raven.Sandbox.Game.Piece.Pieces;

import me.raven.Engine.Utils.Texture;
import me.raven.Sandbox.Game.Piece.Piece;
import me.raven.Sandbox.Game.Piece.PieceColors;
import org.javatuples.Pair;
import org.joml.Vector2f;

import java.util.List;

public class Queen extends Piece {
    public Queen(Vector2f scale, Texture texture, PieceColors pieceColor, int tile) {
        super(scale, texture, 8, pieceColor, tile);
    }

    @Override
    public void specialMove() {

    }

    @Override
    protected void calculatePossibleMoves() {
        Pair<List<Integer>, List<Piece>> temp = generateQueenMoves(this.data.tile, this.data.color);
        addMoves(temp.getValue0());
        addPreys(temp.getValue1());
    }

    @Override
    protected void calculatePossiblePreys() {

    }
}
