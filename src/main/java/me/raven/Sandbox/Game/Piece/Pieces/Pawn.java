package me.raven.Sandbox.Game.Piece.Pieces;

import me.raven.Engine.Utils.Texture;
import me.raven.Sandbox.Game.Piece.Piece;
import me.raven.Sandbox.Game.Piece.PieceColors;
import org.joml.Vector2f;

public class Pawn extends Piece {

    private boolean isFirstMove = false;

    public Pawn(Vector2f scale, Texture texture, PieceColors color, int tile) {
        super(scale, texture, 1, color, tile);
    }

    @Override
    public void specialMove() {
        /*
        @ called every move
         */
        if (!isFirstMove)
            isFirstMove = true;
    }

    @Override
    protected void calculatePossibleMoves() {
        addMoves(this.generatePawnMoves(this.data.tile, this.data.color, isFirstMove));
    }

    @Override
    protected void calculatePossiblePreys() {

    }

}
