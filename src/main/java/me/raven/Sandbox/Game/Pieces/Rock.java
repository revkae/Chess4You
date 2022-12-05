package me.raven.Sandbox.Game.Pieces;

import me.raven.Engine.Utils.Texture;
import me.raven.Sandbox.Game.Piece;
import me.raven.Sandbox.Game.PieceColor;
import org.joml.Vector2f;

public class Rock extends Piece {
    public Rock(Vector2f scale, Texture texture, PieceColor pieceColor, int tile) {
        super(scale, texture, 5, pieceColor, tile);
    }

    @Override
    public void specialMove() {

    }

    @Override
    protected void calculatePossibleMoves() {
        for (int i = 0; i < 8; i++) {
            addMove(MOVE_FORWARD * i);
            addMove(MOVE_BACKWARD * i);
            addMove(MOVE_RIGHT * i);
            addMove(MOVE_LEFT * i);
        }
    }

    @Override
    protected void calculatePossiblePreys() {
        for (int i = 0; i < 8; i++) {
            addPrey(MOVE_FORWARD * i);
            addPrey(MOVE_BACKWARD * i);
            addPrey(MOVE_RIGHT * i);
            addPrey(MOVE_LEFT * i);
        }
    }
}
