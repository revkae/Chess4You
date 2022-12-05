package me.raven.Sandbox.Game.Pieces;

import me.raven.Engine.Utils.Texture;
import me.raven.Sandbox.Game.Piece;
import me.raven.Sandbox.Game.PieceColor;
import org.joml.Vector2f;

public class Horse extends Piece {

    public Horse(Vector2f scale, Texture texture, PieceColor pieceColor, int tile) {
        super(scale, texture, 3, pieceColor, tile);
    }

    @Override
    public void specialMove() {

    }

    @Override
    protected void calculatePossibleMoves() {
        addMove(MOVE_FORWARD * 2 + MOVE_RIGHT);
        addMove(MOVE_FORWARD * 2 + MOVE_LEFT);
        addMove(MOVE_FORWARD + MOVE_RIGHT * 2);
        addMove(MOVE_FORWARD + MOVE_LEFT * 2);
        addMove(MOVE_BACKWARD + MOVE_RIGHT * 2);
        addMove(MOVE_BACKWARD + MOVE_LEFT * 2);
        addMove(MOVE_BACKWARD * 2 + MOVE_RIGHT);
        addMove(MOVE_BACKWARD * 2 + MOVE_LEFT);
    }

    @Override
    protected void calculatePossiblePreys() {
        addPrey(MOVE_FORWARD * 2 + MOVE_RIGHT);
        addPrey(MOVE_FORWARD * 2 + MOVE_LEFT);
        addPrey(MOVE_FORWARD + MOVE_RIGHT * 2);
        addPrey(MOVE_FORWARD + MOVE_LEFT * 2);
        addPrey(MOVE_BACKWARD + MOVE_RIGHT * 2);
        addPrey(MOVE_BACKWARD + MOVE_LEFT * 2);
        addPrey(MOVE_BACKWARD * 2 + MOVE_RIGHT);
        addPrey(MOVE_BACKWARD * 2 + MOVE_LEFT);
    }
}
