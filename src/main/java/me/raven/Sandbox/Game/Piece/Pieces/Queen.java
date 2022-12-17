package me.raven.Sandbox.Game.Piece.Pieces;

import me.raven.Engine.Utils.Texture;
import me.raven.Sandbox.Game.Piece.Piece;
import me.raven.Sandbox.Game.Piece.PieceColors;
import org.joml.Vector2f;

public class Queen extends Piece {
    public Queen(Vector2f scale, Texture texture, PieceColors pieceColor, int tile) {
        super(scale, texture, 8, pieceColor, tile);
    }

    @Override
    public void specialMove() {

    }

    @Override
    protected void calculatePossibleMoves() {
        addMoves(this.generateQueenMoves(this.data.tile));
    }

    @Override
    protected void calculatePossiblePreys() {

    }
}
