package me.raven.Sandbox.Game.Piece;

import me.raven.Sandbox.Game.Board.BoardManager;
import me.raven.Sandbox.Managers.GameManager;

import java.util.ArrayList;
import java.util.List;

public interface MoveCalculator {

    default List<Integer> generateRookMoves(int tileNum) {
        List<Integer> temp = new ArrayList<>();
        BoardManager boardManager = GameManager.get().getBoardManager();

        for (Integer i = 1; i < boardManager.getTileNumsToEdge().get(tileNum).get(PieceDirections.NORTH.getDir()) + 1; i++) {
            temp.add(tileNum + i * PieceDirections.NORTH.getValue());
        }
        for (Integer i = 1; i < boardManager.getTileNumsToEdge().get(tileNum).get(PieceDirections.SOUTH.getDir()) + 1; i++) {
            temp.add(tileNum + i * PieceDirections.SOUTH.getValue());
        }
        for (Integer i = 1; i < boardManager.getTileNumsToEdge().get(tileNum).get(PieceDirections.WEST.getDir()) + 1; i++) {
            temp.add(tileNum + i * PieceDirections.WEST.getValue());
        }
        for (Integer i = 1; i < boardManager.getTileNumsToEdge().get(tileNum).get(PieceDirections.EAST.getDir()) + 1; i++) {
            temp.add(tileNum + i * PieceDirections.EAST.getValue());
        }
        return temp;
    }

    default List<Integer> generateQueenMoves(int tileNum) {
        List<Integer> temp = new ArrayList<>();
        BoardManager boardManager = GameManager.get().getBoardManager();

        //
        for (Integer i = 1; i < boardManager.getTileNumsToEdge().get(tileNum).get(PieceDirections.NORTH.getDir()) + 1; i++) {
            temp.add(tileNum + i * PieceDirections.NORTH.getValue());
        }
        for (Integer i = 1; i < boardManager.getTileNumsToEdge().get(tileNum).get(PieceDirections.SOUTH.getDir()) + 1; i++) {
            temp.add(tileNum + i * PieceDirections.SOUTH.getValue());
        }
        for (Integer i = 1; i < boardManager.getTileNumsToEdge().get(tileNum).get(PieceDirections.WEST.getDir()) + 1; i++) {
            temp.add(tileNum + i * PieceDirections.WEST.getValue());
        }
        for (Integer i = 1; i < boardManager.getTileNumsToEdge().get(tileNum).get(PieceDirections.EAST.getDir()) + 1; i++) {
            temp.add(tileNum + i * PieceDirections.EAST.getValue());
        }
        //
        for (Integer i = 1; i < boardManager.getTileNumsToEdge().get(tileNum).get(PieceDirections.NORTH_EAST.getDir()) + 1; i++) {
            temp.add(tileNum + i * PieceDirections.NORTH_EAST.getValue());
        }
        for (Integer i = 1; i < boardManager.getTileNumsToEdge().get(tileNum).get(PieceDirections.NORTH_WEST.getDir()) + 1; i++) {
            temp.add(tileNum + i * PieceDirections.NORTH_WEST.getValue());
        }
        for (Integer i = 1; i < boardManager.getTileNumsToEdge().get(tileNum).get(PieceDirections.SOUTH_EAST.getDir()) + 1; i++) {
            temp.add(tileNum + i * PieceDirections.SOUTH_EAST.getValue());
        }
        for (Integer i = 1; i < boardManager.getTileNumsToEdge().get(tileNum).get(PieceDirections.SOUTH_WEST.getDir()) + 1; i++) {
            temp.add(tileNum + i * PieceDirections.SOUTH_WEST.getValue());
        }
        //
        return temp;
    }

    default List<Integer> generateBishopMoves(int tileNum) {
        List<Integer> temp = new ArrayList<>();
        BoardManager boardManager = GameManager.get().getBoardManager();

        //
        for (Integer i = 1; i < boardManager.getTileNumsToEdge().get(tileNum).get(PieceDirections.NORTH_EAST.getDir()) + 1; i++) {
            temp.add(tileNum + i * PieceDirections.NORTH_EAST.getValue());
        }
        for (Integer i = 1; i < boardManager.getTileNumsToEdge().get(tileNum).get(PieceDirections.NORTH_WEST.getDir()) + 1; i++) {
            temp.add(tileNum + i * PieceDirections.NORTH_WEST.getValue());
        }
        for (Integer i = 1; i < boardManager.getTileNumsToEdge().get(tileNum).get(PieceDirections.SOUTH_EAST.getDir()) + 1; i++) {
            temp.add(tileNum + i * PieceDirections.SOUTH_EAST.getValue());
        }
        for (Integer i = 1; i < boardManager.getTileNumsToEdge().get(tileNum).get(PieceDirections.SOUTH_WEST.getDir()) + 1; i++) {
            temp.add(tileNum + i * PieceDirections.SOUTH_WEST.getValue());
        }
        //
        return temp;
    }

    default List<Integer> generateKingMoves(int tileNum) {
        List<Integer> temp = new ArrayList<>();
        List<List<Integer>> tileNumsToEdge = GameManager.get().getBoardManager().getTileNumsToEdge();

        //
        if (tileNumsToEdge.get(tileNum).get(PieceDirections.NORTH.getDir()) != 0) {
            temp.add(tileNum + PieceDirections.NORTH.getValue());
        }
        if (tileNumsToEdge.get(tileNum).get(PieceDirections.SOUTH.getDir()) != 0) {
            temp.add(tileNum + PieceDirections.SOUTH.getValue());
        }
        if (tileNumsToEdge.get(tileNum).get(PieceDirections.EAST.getDir()) != 0) {
            temp.add(tileNum + PieceDirections.EAST.getValue());
        }
        if (tileNumsToEdge.get(tileNum).get(PieceDirections.WEST.getDir()) != 0) {
            temp.add(tileNum + PieceDirections.WEST.getValue());
        }
        //
        if (tileNumsToEdge.get(tileNum).get(PieceDirections.NORTH_EAST.getDir()) != 0) {
            temp.add(tileNum + PieceDirections.NORTH_EAST.getValue());
        }
        if (tileNumsToEdge.get(tileNum).get(PieceDirections.SOUTH_EAST.getDir()) != 0) {
            temp.add(tileNum + PieceDirections.SOUTH_EAST.getValue());
        }
        if (tileNumsToEdge.get(tileNum).get(PieceDirections.NORTH_WEST.getDir()) != 0) {
            temp.add(tileNum + PieceDirections.NORTH_WEST.getValue());
        }
        if (tileNumsToEdge.get(tileNum).get(PieceDirections.SOUTH_WEST.getDir()) != 0) {
            temp.add(tileNum + PieceDirections.SOUTH_WEST.getValue());
        }
        //
        return temp;
    }

    default List<Integer> generatePawnMoves(int tileNum, PieceColors pieceColor, boolean isFirstMove) {
        List<Integer> temp = new ArrayList<>();
        List<List<Integer>> tileNumsToEdge = GameManager.get().getBoardManager().getTileNumsToEdge();

        //
        if (pieceColor.equals(PieceColors.WHITE)) {
            if (tileNumsToEdge.get(tileNum).get(PieceDirections.NORTH.getDir()) != 0) {
                if (isFirstMove) {
                    temp.add(tileNum + PieceDirections.NORTH.getValue());
                    temp.add(tileNum + 2 * PieceDirections.NORTH.getValue());
                } else {
                    temp.add(tileNum + PieceDirections.NORTH.getValue());
                }
            }
        } else {
            if (tileNumsToEdge.get(tileNum).get(PieceDirections.SOUTH.getDir()) != 0) {
                if (isFirstMove) {
                    temp.add(tileNum + PieceDirections.SOUTH.getValue());
                    temp.add(tileNum + 2 * PieceDirections.SOUTH.getValue());
                } else {
                    temp.add(tileNum + PieceDirections.SOUTH.getValue());
                }
            }
        }

        //
        return temp;
    }

    default List<Integer> generateKnightMoves(int tileNum) {
        List<Integer> temp = new ArrayList<>();
        List<List<Integer>> tileNumsToEdge = GameManager.get().getBoardManager().getTileNumsToEdge();

        //
        if (tileNumsToEdge.get(tileNum).get(PieceDirections.NORTH.getDir()) >= 2
                && tileNumsToEdge.get(tileNum).get(PieceDirections.NORTH_EAST.getDir()) >= 1) {
            temp.add(tileNum + 1 * PieceDirections.NORTH.getValue() + PieceDirections.NORTH_EAST.getValue());
        }
        if (tileNumsToEdge.get(tileNum).get(PieceDirections.NORTH.getDir()) >= 2
                && tileNumsToEdge.get(tileNum).get(PieceDirections.NORTH_WEST.getDir()) >= 1) {
            temp.add(tileNum + 1 * PieceDirections.NORTH.getValue() + PieceDirections.NORTH_WEST.getValue());
        }
        //
        if (tileNumsToEdge.get(tileNum).get(PieceDirections.WEST.getDir()) >= 2
                && tileNumsToEdge.get(tileNum).get(PieceDirections.SOUTH.getDir()) >= 1) {
            temp.add(tileNum + 2 * PieceDirections.WEST.getValue() + PieceDirections.SOUTH.getValue());
        }
        if (tileNumsToEdge.get(tileNum).get(PieceDirections.WEST.getDir()) >= 2
                && tileNumsToEdge.get(tileNum).get(PieceDirections.NORTH.getDir()) >= 1) {
            temp.add(tileNum + 2 * PieceDirections.WEST.getValue() + PieceDirections.NORTH.getValue());
        }
        //
        if (tileNumsToEdge.get(tileNum).get(PieceDirections.SOUTH.getDir()) >= 2
                && tileNumsToEdge.get(tileNum).get(PieceDirections.WEST.getDir()) >= 1) {
            temp.add(tileNum + 2 * PieceDirections.SOUTH.getValue() + PieceDirections.WEST.getValue());
        }
        if (tileNumsToEdge.get(tileNum).get(PieceDirections.SOUTH.getDir()) >= 2
                && tileNumsToEdge.get(tileNum).get(PieceDirections.EAST.getDir()) >= 1) {
            temp.add(tileNum + 2 * PieceDirections.SOUTH.getValue() + PieceDirections.EAST.getValue());
        }
        //
        if (tileNumsToEdge.get(tileNum).get(PieceDirections.EAST.getDir()) >= 2
                && tileNumsToEdge.get(tileNum).get(PieceDirections.NORTH.getDir()) >= 1) {
            temp.add(tileNum + 2 * PieceDirections.EAST.getValue() + PieceDirections.NORTH.getValue());
        }
        if (tileNumsToEdge.get(tileNum).get(PieceDirections.EAST.getDir()) >= 2
                && tileNumsToEdge.get(tileNum).get(PieceDirections.SOUTH.getDir()) >= 1) {
            temp.add(tileNum + 2 * PieceDirections.EAST.getValue() + PieceDirections.SOUTH.getValue());
        }
        //
        return temp;
    }
}
