package me.raven.Sandbox.Game.Piece;

import me.raven.Sandbox.Managers.GameManager;
import org.javatuples.Pair;

import java.util.ArrayList;
import java.util.List;

public interface MoveCalculator {
    
    default Piece hasPiece(int tileNum) {
        for (Piece piece : GameManager.get().getPieceManager().getPieces()) {
            if (piece.data.tile == tileNum) {
                return piece;
            }
        }
        return null;
    }

    default boolean isDifColor(int tileNum, PieceColors color) {
        for (Piece piece : GameManager.get().getPieceManager().getPieces()) {
            if (piece.data.tile == tileNum && piece.data.color != color) {
                return true;
            }
        }
        return false;
    }

    default Pair<List<Integer>, List<Piece>> generateRookMoves(int tileNum, PieceColors color) {
        List<Integer> temp = new ArrayList<>();
        List<Piece> pieces = new ArrayList<>();
        List<List<Integer>> tileNumsToEdge = GameManager.get().getBoardManager().getTileNumsToEdge();

        for (PieceDirections value : PieceDirections.values()) {
            if (value.isDiagonal()) continue;

            for (Integer i = 1; i < tileNumsToEdge.get(tileNum).get(value.getDir()) + 1; i++) {
                Piece prey = hasPiece(tileNum + 1 * value.getValue());
                if (prey != null) {
                    if (isDifColor(tileNum + 1 * value.getValue(), color))
                        pieces.add(prey);
                    break;
                }

                temp.add(tileNum + i * value.getValue());
            }
        }
        return Pair.with(temp, pieces);
    }

    default Pair<List<Integer>, List<Piece>> generateQueenMoves(int tileNum, PieceColors color) {
        List<Integer> temp = new ArrayList<>();
        List<Piece> pieces = new ArrayList<>();
        List<List<Integer>> tileNumsToEdge = GameManager.get().getBoardManager().getTileNumsToEdge();

        for (PieceDirections value : PieceDirections.values()) {
            for (Integer i = 1; i < tileNumsToEdge.get(tileNum).get(value.getDir()) + 1; i++) {
                Piece prey = hasPiece(tileNum + i * value.getValue());
                if (prey != null) {
                    if (isDifColor(tileNum + i * value.getValue(), color))
                        pieces.add(prey);
                    break;
                }

                temp.add(tileNum + i * value.getValue());
            }
        }

        return Pair.with(temp, pieces);
    }

    default Pair<List<Integer>, List<Piece>> generateBishopMoves(int tileNum, PieceColors color) {
        List<Integer> temp = new ArrayList<>();
        List<Piece> pieces = new ArrayList<>();
        List<List<Integer>> tileNumsToEdge = GameManager.get().getBoardManager().getTileNumsToEdge();

        for (PieceDirections value : PieceDirections.values()) {
            if (!value.isDiagonal()) continue;

            for (Integer i = 1; i < tileNumsToEdge.get(tileNum).get(value.getDir()) + 1; i++) {
                Piece prey = hasPiece(tileNum + i * value.getValue());
                if (prey != null) {
                    if (isDifColor(tileNum + i * value.getValue(), color))
                        pieces.add(prey);
                    break;
                }

                temp.add(tileNum + i * value.getValue());
            }
        }

        return Pair.with(temp, pieces);
    }

    default Pair<List<Integer>, List<Piece>> generateKingMoves(int tileNum, PieceColors color) {
        List<Integer> temp = new ArrayList<>();
        List<Piece> pieces = new ArrayList<>();
        List<List<Integer>> tileNumsToEdge = GameManager.get().getBoardManager().getTileNumsToEdge();

        for (PieceDirections value : PieceDirections.values()) {
            if (tileNumsToEdge.get(tileNum).get(value.getDir()) != 0) {
                Piece prey = hasPiece(tileNum + value.getValue());
                if (prey != null) {
                    if (isDifColor(tileNum + value.getValue(), color))
                        pieces.add(prey);
                    continue;
                }

                temp.add(tileNum + value.getValue());
            }
        }

        return Pair.with(temp, pieces);
    }

    default Pair<List<Integer>, List<Piece>> generatePawnMoves(int tileNum, PieceColors pieceColor, boolean isFirstMove) {
        List<Integer> temp = new ArrayList<>();
        List<Piece> pieces = new ArrayList<>();
        List<List<Integer>> tileNumsToEdge = GameManager.get().getBoardManager().getTileNumsToEdge();

        if (pieceColor.equals(PieceColors.WHITE)) {

            if (tileNumsToEdge.get(tileNum).get(PieceDirections.SOUTH.getDir()) != 0) {
                if (isFirstMove) {
                    Piece prey1 = hasPiece(tileNum + PieceDirections.SOUTH.getValue());
                    Piece prey2 = hasPiece(tileNum + 2 * PieceDirections.SOUTH.getValue());

                    if (prey1 == null)
                        temp.add(tileNum + PieceDirections.SOUTH.getValue());
                    if (prey2 == null)
                        temp.add(tileNum + 2 * PieceDirections.SOUTH.getValue());
                } else {
                    Piece prey1 = hasPiece(tileNum + PieceDirections.SOUTH.getValue());

                    if (prey1 == null)
                        temp.add(tileNum + PieceDirections.SOUTH.getValue());
                }
            }
            if (tileNumsToEdge.get(tileNum).get(PieceDirections.SOUTH_EAST.getDir()) != 0) {
                Piece prey = hasPiece(tileNum + PieceDirections.SOUTH_EAST.getValue());
                if (prey != null) {
                    if (isDifColor(tileNum + PieceDirections.SOUTH_EAST.getValue(), pieceColor))
                        pieces.add(prey);
                }
            }
            if (tileNumsToEdge.get(tileNum).get(PieceDirections.SOUTH_WEST.getDir()) != 0) {
                Piece prey = hasPiece(tileNum + PieceDirections.SOUTH_WEST.getValue());
                if (prey != null) {
                    if (isDifColor(tileNum + PieceDirections.SOUTH_WEST.getValue(), pieceColor))
                        pieces.add(prey);
                }
            }

        } else {
            if (tileNumsToEdge.get(tileNum).get(PieceDirections.NORTH.getDir()) != 0) {
                if (isFirstMove) {
                    Piece prey1 = hasPiece(tileNum + PieceDirections.NORTH.getValue());
                    Piece prey2 = hasPiece(tileNum + 2 * PieceDirections.NORTH.getValue());
                    if (prey1 == null)
                        temp.add(tileNum + PieceDirections.NORTH.getValue());
                    if (prey2 == null)
                        temp.add(tileNum + 2 * PieceDirections.NORTH.getValue());
                } else {
                    Piece prey1 = hasPiece(tileNum + PieceDirections.NORTH.getValue());

                    if (prey1 == null)
                        temp.add(tileNum + PieceDirections.NORTH.getValue());
                }
            }

            if (tileNumsToEdge.get(tileNum).get(PieceDirections.NORTH_EAST.getDir()) != 0) {
                Piece prey = hasPiece(tileNum + PieceDirections.NORTH_EAST.getValue());
                if (prey != null) {
                    if (isDifColor(tileNum + PieceDirections.NORTH_EAST.getValue(), pieceColor))
                        pieces.add(prey);
                }
            }
            if (tileNumsToEdge.get(tileNum).get(PieceDirections.NORTH_WEST.getDir()) != 0) {
                Piece prey = hasPiece(tileNum + PieceDirections.NORTH_WEST.getValue());
                if (prey != null) {
                    if (isDifColor(tileNum + PieceDirections.NORTH_WEST.getValue(), pieceColor))
                        pieces.add(prey);
                }
            }
        }

        return Pair.with(temp, pieces);
    }

    default Pair<List<Integer>, List<Piece>> generateKnightMoves(int tileNum, PieceColors color) {
        List<Integer> temp = new ArrayList<>();
        List<Piece> pieces = new ArrayList<>();
        List<List<Integer>> tileNumsToEdge = GameManager.get().getBoardManager().getTileNumsToEdge();

        //
        if (tileNumsToEdge.get(tileNum).get(PieceDirections.NORTH.getDir()) >= 2
                && tileNumsToEdge.get(tileNum).get(PieceDirections.NORTH_EAST.getDir()) >= 1) {
            Piece prey = hasPiece(tileNum + 1 * PieceDirections.NORTH.getValue() + PieceDirections.NORTH_EAST.getValue());
            if (prey == null) {
                temp.add(tileNum + 1 * PieceDirections.NORTH.getValue() + PieceDirections.NORTH_EAST.getValue());
            } else {
                if (isDifColor(tileNum + 1 * PieceDirections.NORTH.getValue() + PieceDirections.NORTH_EAST.getValue(), color))
                    pieces.add(prey);
            }
        }
        if (tileNumsToEdge.get(tileNum).get(PieceDirections.NORTH.getDir()) >= 2
                && tileNumsToEdge.get(tileNum).get(PieceDirections.NORTH_WEST.getDir()) >= 1) {
            Piece prey = hasPiece(tileNum + 1 * PieceDirections.NORTH.getValue() + PieceDirections.NORTH_WEST.getValue());
            if (prey == null) {
                temp.add(tileNum + 1 * PieceDirections.NORTH.getValue() + PieceDirections.NORTH_WEST.getValue());
            } else {
                if (isDifColor(tileNum + 1 * PieceDirections.NORTH.getValue() + PieceDirections.NORTH_EAST.getValue(), color))
                    pieces.add(prey);
            }
        }
        //
        if (tileNumsToEdge.get(tileNum).get(PieceDirections.WEST.getDir()) >= 2
                && tileNumsToEdge.get(tileNum).get(PieceDirections.SOUTH.getDir()) >= 1) {
            Piece prey = hasPiece(tileNum + 2 * PieceDirections.WEST.getValue() + PieceDirections.SOUTH.getValue());
            if (prey == null) {
                temp.add(tileNum + 2 * PieceDirections.WEST.getValue() + PieceDirections.SOUTH.getValue());
            } else {
                if (isDifColor(tileNum + 2 * PieceDirections.WEST.getValue() + PieceDirections.SOUTH.getValue(), color))
                    pieces.add(prey);
            }
        }
        if (tileNumsToEdge.get(tileNum).get(PieceDirections.WEST.getDir()) >= 2
                && tileNumsToEdge.get(tileNum).get(PieceDirections.NORTH.getDir()) >= 1) {
            Piece prey = hasPiece(tileNum + 2 * PieceDirections.WEST.getValue() + PieceDirections.NORTH.getValue());
            if (prey == null) {
                temp.add(tileNum + 2 * PieceDirections.WEST.getValue() + PieceDirections.NORTH.getValue());
            } else {
                if (isDifColor(tileNum + 2 * PieceDirections.WEST.getValue() + PieceDirections.NORTH.getValue(), color))
                    pieces.add(prey);
            }
        }
        //
        if (tileNumsToEdge.get(tileNum).get(PieceDirections.SOUTH.getDir()) >= 2
                && tileNumsToEdge.get(tileNum).get(PieceDirections.WEST.getDir()) >= 1) {
            Piece prey = hasPiece(tileNum + 2 * PieceDirections.SOUTH.getValue() + PieceDirections.WEST.getValue());
            if (prey == null) {
                temp.add(tileNum + 2 * PieceDirections.SOUTH.getValue() + PieceDirections.WEST.getValue());
            } else {
                if (isDifColor(tileNum + 2 * PieceDirections.SOUTH.getValue() + PieceDirections.WEST.getValue(), color))
                    pieces.add(prey);
            }
        }
        if (tileNumsToEdge.get(tileNum).get(PieceDirections.SOUTH.getDir()) >= 2
                && tileNumsToEdge.get(tileNum).get(PieceDirections.EAST.getDir()) >= 1) {
            Piece prey = hasPiece(tileNum + 2 * PieceDirections.SOUTH.getValue() + PieceDirections.EAST.getValue());
            if (prey == null) {
                temp.add(tileNum + 2 * PieceDirections.SOUTH.getValue() + PieceDirections.EAST.getValue());
            } else {
                if (isDifColor(tileNum + 2 * PieceDirections.SOUTH.getValue() + PieceDirections.EAST.getValue(), color))
                    pieces.add(prey);
            }
        }
        //
        if (tileNumsToEdge.get(tileNum).get(PieceDirections.EAST.getDir()) >= 2
                && tileNumsToEdge.get(tileNum).get(PieceDirections.NORTH.getDir()) >= 1) {
            Piece prey = hasPiece(tileNum + 2 * PieceDirections.EAST.getValue() + PieceDirections.NORTH.getValue());
            if (prey == null)  {
                temp.add(tileNum + 2 * PieceDirections.EAST.getValue() + PieceDirections.NORTH.getValue());
            } else {
                if (isDifColor(tileNum + 2 * PieceDirections.EAST.getValue() + PieceDirections.NORTH.getValue(), color))
                    pieces.add(prey);
            }
        }
        if (tileNumsToEdge.get(tileNum).get(PieceDirections.EAST.getDir()) >= 2
                && tileNumsToEdge.get(tileNum).get(PieceDirections.SOUTH.getDir()) >= 1) {
            Piece prey = hasPiece(tileNum + 2 * PieceDirections.EAST.getValue() + PieceDirections.SOUTH.getValue());
            if (prey == null) {
                temp.add(tileNum + 2 * PieceDirections.EAST.getValue() + PieceDirections.SOUTH.getValue());
            } else {
                if (isDifColor(tileNum + 2 * PieceDirections.EAST.getValue() + PieceDirections.SOUTH.getValue(), color))
                    pieces.add(prey);
            }
        }
        //

        return Pair.with(temp, pieces);
    }
}
