package me.raven.Sandbox.Game.Pieces;

import me.raven.Engine.Utils.Texture;
import me.raven.Sandbox.Game.Piece;
import me.raven.Sandbox.Game.PieceColor;
import org.joml.Vector2f;

public class Bishop extends Piece {
    public Bishop(Vector2f scale, Texture texture, PieceColor pieceColor, int tile) {
        super(scale, texture, 3, pieceColor, tile);
    }

    @Override
    public void specialMove() {

    }

    @Override
    protected void calculatePossibleMoves() {
        for (int i = 0; i < 8; i++) {
            addMove(i * (MOVE_RIGHT + MOVE_FORWARD));
            addMove(i * (MOVE_LEFT + MOVE_FORWARD));
            addMove(i * (MOVE_RIGHT + MOVE_BACKWARD));
            addMove(i * (MOVE_LEFT + MOVE_BACKWARD));
        }
    }

    @Override
    protected void calculatePossiblePreys() {
        for (int i = 0; i < 8; i++) {
            addPrey(i * (MOVE_RIGHT + MOVE_FORWARD));
            addPrey(i * (MOVE_LEFT + MOVE_FORWARD));
            addPrey(i * (MOVE_RIGHT + MOVE_BACKWARD));
            addPrey(i * (MOVE_LEFT + MOVE_BACKWARD));
        }
    }
}
