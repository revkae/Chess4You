package me.raven.Sandbox.Game.Piece;

import me.raven.Sandbox.Managers.GameManager;

import java.util.ArrayList;
import java.util.List;

public interface PreyCalculator {

    default Piece hasPrey(int tileNum, PieceColors color) {
        for (Piece piece : GameManager.get().getPieceManager().getPieces()) {
            if (piece.data.tile == tileNum && piece.data.color != color) return piece;
        }
        return null;
    }

    default List<Piece> generateRookPreys(int tileNum, PieceColors color) {
        List<Piece> temp = new ArrayList<>();
        List<List<Integer>> tileNumsToEdge = GameManager.get().getBoardManager().getTileNumsToEdge();

        for (PieceDirections value : PieceDirections.values()) {
            if (value.isDiagonal()) continue;

            for (Integer i = 1; i < tileNumsToEdge.get(tileNum).get(value.getDir()) + 1; i++) {
                Piece prey = hasPrey(tileNum + i * value.getValue(), color);
                if (prey == null) continue;

                temp.add(prey);
            }
        }
        return temp;
    }

    default List<Piece> generateQueenPreys(int tileNum, PieceColors color) {
        List<Piece> temp = new ArrayList<>();
        List<List<Integer>> tileNumsToEdge = GameManager.get().getBoardManager().getTileNumsToEdge();

        for (PieceDirections value : PieceDirections.values()) {
            for (Integer i = 1; i < tileNumsToEdge.get(tileNum).get(value.getDir()) + 1; i++) {
                Piece prey = hasPrey(tileNum + i * value.getValue(), color);
                if (prey == null) break;

                temp.add(prey);
            }
        }

        return temp;
    }

    default List<Piece> generateBishopPreys(int tileNum, PieceColors color) {
        List<Piece> temp = new ArrayList<>();
        List<List<Integer>> tileNumsToEdge = GameManager.get().getBoardManager().getTileNumsToEdge();

        for (PieceDirections value : PieceDirections.values()) {
            if (!value.isDiagonal()) continue;

            for (Integer i = 1; i < tileNumsToEdge.get(tileNum).get(value.getDir()) + 1; i++) {
                Piece prey = hasPrey(tileNum + i * value.getValue(), color);
                if (prey == null) break;

                temp.add(prey);
            }
        }

        return temp;
    }

    default List<Piece> generateKingPreys(int tileNum, PieceColors color) {
        List<Piece> temp = new ArrayList<>();
        List<List<Integer>> tileNumsToEdge = GameManager.get().getBoardManager().getTileNumsToEdge();

        for (PieceDirections value : PieceDirections.values()) {
            if (tileNumsToEdge.get(tileNum).get(value.getDir()) != 0) {
                Piece prey = hasPrey(tileNum + value.getValue(), color);
                if (prey == null) continue;

                temp.add(prey);
            }
        }

        return temp;
    }

    default List<Piece> generatePawnPreys(int tileNum, PieceColors pieceColor, boolean isFirstMove) {
        List<Piece> temp = new ArrayList<>();
        List<List<Integer>> tileNumsToEdge = GameManager.get().getBoardManager().getTileNumsToEdge();

        if (pieceColor.equals(PieceColors.WHITE)) {
            if (tileNumsToEdge.get(tileNum).get(PieceDirections.SOUTH.getDir()) != 0) {
                if (isFirstMove) {
                    Piece prey1 = hasPrey(tileNum + PieceDirections.SOUTH.getValue(), pieceColor);
                    Piece prey2 = hasPrey(tileNum + 2 * PieceDirections.SOUTH.getValue(), pieceColor);
                    if (prey1 != null)
                        temp.add(prey1);
                    if (prey2 != null)
                        temp.add(prey2);
                } else {
                    Piece prey1 = hasPrey(tileNum + PieceDirections.SOUTH.getValue(), pieceColor);
                    if (prey1 == null) return null;

                    temp.add(prey1);
                }
            }
        } else {
            if (tileNumsToEdge.get(tileNum).get(PieceDirections.NORTH.getDir()) != 0) {
                if (isFirstMove) {
                    Piece prey1 = hasPrey(tileNum + PieceDirections.NORTH.getValue(), pieceColor);
                    Piece prey2 = hasPrey(tileNum + 2 * PieceDirections.NORTH.getValue(), pieceColor);
                    if (prey1 != null)
                        temp.add(prey1);
                    if (prey2 != null)
                        temp.add(prey2);
                } else {
                    Piece prey1 = hasPrey(tileNum + PieceDirections.SOUTH.getValue(), pieceColor);
                    if (prey1 == null) return null;

                    temp.add(prey1);
                }
            }
        }

        return temp;
    }

    default List<Piece> generateKnightPreys(int tileNum, PieceColors color) {
        List<Piece> temp = new ArrayList<>();
        List<List<Integer>> tileNumsToEdge = GameManager.get().getBoardManager().getTileNumsToEdge();

        //
        if (tileNumsToEdge.get(tileNum).get(PieceDirections.NORTH.getDir()) >= 2
                && tileNumsToEdge.get(tileNum).get(PieceDirections.NORTH_EAST.getDir()) >= 1) {
            Piece prey = hasPrey(tileNum + 1 * PieceDirections.NORTH.getValue() + PieceDirections.NORTH_EAST.getValue(), color);
            if (prey == null) return null;

            temp.add(prey);
        }
        if (tileNumsToEdge.get(tileNum).get(PieceDirections.NORTH.getDir()) >= 2
                && tileNumsToEdge.get(tileNum).get(PieceDirections.NORTH_WEST.getDir()) >= 1) {
            Piece prey = hasPrey(tileNum + 1 * PieceDirections.NORTH.getValue() + PieceDirections.NORTH_WEST.getValue(), color);
            if (prey == null) return null;

            temp.add(prey);
        }
        //
        if (tileNumsToEdge.get(tileNum).get(PieceDirections.WEST.getDir()) >= 2
                && tileNumsToEdge.get(tileNum).get(PieceDirections.SOUTH.getDir()) >= 1) {
            Piece prey = hasPrey(tileNum + 2 * PieceDirections.WEST.getValue() + PieceDirections.SOUTH.getValue(), color);
            if (prey == null) return null;

            temp.add(prey);
        }
        if (tileNumsToEdge.get(tileNum).get(PieceDirections.WEST.getDir()) >= 2
                && tileNumsToEdge.get(tileNum).get(PieceDirections.NORTH.getDir()) >= 1) {
            Piece prey = hasPrey(tileNum + 2 * PieceDirections.WEST.getValue() + PieceDirections.NORTH.getValue(), color);
            if (prey == null) return null;

            temp.add(prey);
        }
        //
        if (tileNumsToEdge.get(tileNum).get(PieceDirections.SOUTH.getDir()) >= 2
                && tileNumsToEdge.get(tileNum).get(PieceDirections.WEST.getDir()) >= 1) {
            Piece prey = hasPrey(tileNum + 2 * PieceDirections.SOUTH.getValue() + PieceDirections.WEST.getValue(), color);
            if (prey == null) return null;

            temp.add(prey);
        }
        if (tileNumsToEdge.get(tileNum).get(PieceDirections.SOUTH.getDir()) >= 2
                && tileNumsToEdge.get(tileNum).get(PieceDirections.EAST.getDir()) >= 1) {
            Piece prey = hasPrey(tileNum + 2 * PieceDirections.SOUTH.getValue() + PieceDirections.EAST.getValue(), color);
            if (prey == null) return null;

            temp.add(prey);
        }
        //
        if (tileNumsToEdge.get(tileNum).get(PieceDirections.EAST.getDir()) >= 2
                && tileNumsToEdge.get(tileNum).get(PieceDirections.NORTH.getDir()) >= 1) {
            Piece prey = hasPrey(tileNum + 2 * PieceDirections.EAST.getValue() + PieceDirections.NORTH.getValue(), color);
            if (prey == null) return null;

            temp.add(prey);
        }
        if (tileNumsToEdge.get(tileNum).get(PieceDirections.EAST.getDir()) >= 2
                && tileNumsToEdge.get(tileNum).get(PieceDirections.SOUTH.getDir()) >= 1) {
            Piece prey = hasPrey(tileNum + 2 * PieceDirections.EAST.getValue() + PieceDirections.SOUTH.getValue(), color);
            if (prey == null) return null;

            temp.add(prey);
        }
        //
        return temp;
    }
}
