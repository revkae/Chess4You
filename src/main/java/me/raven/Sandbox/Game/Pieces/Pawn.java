package me.raven.Sandbox.Game.Pieces;

import me.raven.Engine.Utils.Texture;
import me.raven.Sandbox.Game.Piece;
import me.raven.Sandbox.Game.PieceColor;
import org.joml.Vector2f;

public class Pawn extends Piece {

    private boolean isFirstMove = true;

    public Pawn(Vector2f scale, Texture texture, PieceColor color, int tile) {
        super(scale, texture, 1, color, tile);
    }

    @Override
    public void specialMove() {

    }

    @Override
    protected void calculatePossibleMoves() {
        if (isFirstMove) {
            if (pieceColor == PieceColor.BLACK) {
                addMove(MOVE_FORWARD);
                addMove(MOVE_FORWARD * 2);
            } else {
                addMove(MOVE_BACKWARD);
                addMove(MOVE_BACKWARD * 2);
            }

            isFirstMove = false;
        } else {
            if (pieceColor == PieceColor.BLACK) {
                addMove(MOVE_FORWARD);
            } else {
                addMove(MOVE_BACKWARD);
            }
        }
    }

    @Override
    protected void calculatePossiblePreys() {
        addPrey(MOVE_FORWARD + MOVE_LEFT);
        addPrey(MOVE_FORWARD + MOVE_RIGHT);
    }

}
