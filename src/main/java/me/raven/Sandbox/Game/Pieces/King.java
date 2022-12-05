package me.raven.Sandbox.Game.Pieces;

import me.raven.Engine.Utils.Texture;
import me.raven.Sandbox.Game.Piece;
import me.raven.Sandbox.Game.PieceColor;
import org.joml.Vector2f;

public class King extends Piece {
    public King(Vector2f scale, Texture texture, PieceColor pieceColor, int tile) {
        super(scale, texture, 1, pieceColor, tile);
    }

    @Override
    public void specialMove() {

    }

    @Override
    protected void calculatePossibleMoves() {
        addMove(MOVE_FORWARD);
        addMove(MOVE_BACKWARD);
        addMove(MOVE_FORWARD + MOVE_RIGHT);
        addMove(MOVE_FORWARD + MOVE_LEFT);
        addMove(MOVE_RIGHT);
        addMove(MOVE_LEFT);
        addMove(MOVE_BACKWARD + MOVE_RIGHT);
        addMove(MOVE_BACKWARD + MOVE_LEFT);
    }

    @Override
    protected void calculatePossiblePreys() {
        addPrey(MOVE_FORWARD);
        addPrey(MOVE_BACKWARD);
        addPrey(MOVE_FORWARD + MOVE_RIGHT);
        addPrey(MOVE_FORWARD + MOVE_LEFT);
        addPrey(MOVE_RIGHT);
        addPrey(MOVE_LEFT);
        addPrey(MOVE_BACKWARD + MOVE_RIGHT);
        addPrey(MOVE_BACKWARD + MOVE_LEFT);
    }
}
