package me.raven.Sandbox.Game.Piece.Pieces;

import me.raven.Engine.Utils.Texture;
import me.raven.Sandbox.Game.Piece.Piece;
import me.raven.Sandbox.Game.Piece.PieceColors;
import org.javatuples.Pair;
import org.joml.Vector2f;

import java.util.List;

public class Pawn extends Piece {

    private boolean isFirstMove = true;

    public Pawn(Vector2f scale, Texture texture, PieceColors color, int tile) {
        super(scale, texture, 1, color, tile);
    }

    @Override
    public void specialMove() {
        /*
        @ called every move
         */
        if (isFirstMove)
            isFirstMove = false;
    }

    @Override
    protected void calculatePossibleMoves() {
        Pair<List<Integer>, List<Piece>> temp = generatePawnMoves(this.data.tile, this.data.color, isFirstMove);
        try {
            addMoves(temp.getValue0() == null ? null : temp.getValue0());
            addPreys(temp.getValue1() == null ? null : temp.getValue1());
        } catch (NullPointerException e) {

        }
    }

    @Override
    protected void calculatePossiblePreys() {

    }

}
