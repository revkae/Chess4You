package me.raven.Sandbox.Game.Piece;

import org.javatuples.Pair;

public enum PieceDirections {

    NORTH(Pair.with(0, 1), false),
    SOUTH(Pair.with(1, -1), false),
    EAST(Pair.with(2, 8), false),
    WEST(Pair.with(3, -8), false),
    NORTH_EAST(Pair.with(4, 9), true),
    NORTH_WEST(Pair.with(5, -7), true),
    SOUTH_EAST(Pair.with(6, 7), true),
    SOUTH_WEST(Pair.with(7, -9), true);

    private Pair<Integer, Integer> value;
    private boolean diagonal;

    PieceDirections(Pair<Integer, Integer> value, boolean diagonal) {
        this.value = value;
        this.diagonal = diagonal;
    }

    public int getDir() {
        return value.getValue0();
    }

    public int getValue() {
        return value.getValue1();
    }

    public boolean isDiagonal() {
        return diagonal;
    }
}
