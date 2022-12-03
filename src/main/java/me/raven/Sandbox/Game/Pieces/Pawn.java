package me.raven.Sandbox.Game.Pieces;

import me.raven.Engine.Drawable;
import me.raven.Engine.Listeners.MouseListener;
import me.raven.Engine.Renderer.Renderer;
import me.raven.Engine.Shapes.Quad;
import me.raven.Engine.Utils.Texture;
import me.raven.Sandbox.Game.BoardManager;
import me.raven.Sandbox.Game.Piece;
import me.raven.Sandbox.Game.PieceColor;
import me.raven.Sandbox.Managers.GameManager;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.GLFW_MOUSE_BUTTON_LEFT;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

public class Pawn extends Piece {

    private boolean isFirstMove = false;

    public Pawn(Vector2f scale, Texture texture, int tile) {
        super(scale, texture, 1, PieceColor.WHITE, tile);
    }

    @Override
    public void specialMove() {
        if (isFirstMove)
            isFirstMove = false;
    }

    @Override
    protected void calculatePossibleMoves() {
        if (isFirstMove) {
            possibleMoves.add(tile + MOVE_FORWARD);
            possibleMoves.add(tile + MOVE_FORWARD * 2);
        } else {
            possibleMoves.add(tile + MOVE_FORWARD);
        }
    }

    @Override
    protected void calculatePossiblePreys() {
        List<Integer> moves = new ArrayList<>(2);
        moves.add(tile + MOVE_FORWARD + MOVE_LEFT);
        moves.add(tile + MOVE_FORWARD + MOVE_RIGHT);
        for (Integer num : getPieceManager().getPieceTiles()) {
            if (possiblePreys.size() == 2) {
                break;
            }
            if (moves.contains(num)) {
                if (!PieceColor.equals(getPieceManager().getPieces().get(num).pieceColor, pieceColor)) {
                    possiblePreys.add(getPieceManager().getPieces().get(num));
                }
            }
        }
    }

}
