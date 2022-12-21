package me.raven.Sandbox.Game.Piece.Pieces;

import me.raven.Engine.Utils.Texture;
import me.raven.Sandbox.Game.Board.BoardManager;
import me.raven.Sandbox.Game.Piece.Piece;
import me.raven.Sandbox.Game.Piece.PieceColors;
import me.raven.Sandbox.Game.Piece.PieceDirections;
import me.raven.Sandbox.Game.Piece.PieceManager;
import org.joml.Vector2f;

public class Pawn extends Piece {

    public boolean isFirstMove = true;

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
    protected void checkLooker() {

    }

    private void calcEmptyMoves(PieceDirections dir) {
        if (BoardManager.get().getTileCountToEdge(data.tile, dir) != 0) {
            if (isFirstMove) {
                int nextTile = data.tile + dir.getValue();
                int nextTile2 = data.tile + 2 * dir.getValue();

                if (!hasAlly(nextTile) && !hasEnemy(nextTile)) {
                    if (PieceManager.get().isKingChecked(data.color) && PieceManager.get().canBlockByMove(nextTile, data.color)) {
                        isEmpty(nextTile);
                        return;
                    } else if (!PieceManager.get().isKingChecked(data.color)) {
                        isEmpty(nextTile);
                    }
                }
                if (!hasAlly(nextTile2) && !hasEnemy(nextTile2)) {
                    if (PieceManager.get().isKingChecked(data.color) && PieceManager.get().canBlockByMove(nextTile, data.color)) {
                        isEmpty(nextTile2);
                        return;
                    } else if (!PieceManager.get().isKingChecked(data.color)) {
                        isEmpty(nextTile2);
                    }
                }
            } else {
                int nextTile = data.tile + dir.getValue();

                if (!hasAlly(nextTile) && !hasEnemy(nextTile)) {
                    if (PieceManager.get().isKingChecked(data.color) && PieceManager.get().canBlockByMove(nextTile, data.color)) {
                        isEmpty(nextTile);
                        return;
                    } else if (!PieceManager.get().isKingChecked(data.color)) {
                        isEmpty(nextTile);
                    }
                }
            }
        }
    }

    private void calcEmptyPreys(PieceDirections dir) {
        if (BoardManager.get().getTileCountToEdge(data.tile, dir) != 0) {
            int nextTile = data.tile + dir.getValue();

            addAttackMove(nextTile);
            if (hasAlly(nextTile)) return;
            if (PieceManager.get().isKingChecked(data.color) && PieceManager.get().canBlockByPrey(nextTile, data.color)) {
                isEnemy(nextTile);
                return;
            } else if (!PieceManager.get().isKingChecked(data.color)) {
                isEnemy(nextTile);
            }
        }
        clearAttackMoves();
    }

    @Override
    protected void calculatePossibleMoves(PieceDirections dir) {
        if (data.color == PieceColors.WHITE)
            calcEmptyMoves(PieceDirections.SOUTH);
        else
            calcEmptyMoves(PieceDirections.NORTH);

    }

    @Override
    protected void calculatePossiblePreys(PieceDirections dir) {
        if (data.color == PieceColors.WHITE) {
            calcEmptyPreys(PieceDirections.SOUTH_EAST);
            calcEmptyPreys(PieceDirections.SOUTH_WEST);
        } else {
            calcEmptyPreys(PieceDirections.NORTH_EAST);
            calcEmptyPreys(PieceDirections.NORTH_WEST);
        }
    }

}
