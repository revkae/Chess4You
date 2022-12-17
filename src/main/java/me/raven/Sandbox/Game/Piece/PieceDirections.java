package me.raven.Sandbox.Game.Piece;

import org.javatuples.Pair;

public enum PieceDirections {

    NORTH(Pair.with(0, 1)),
    SOUTH(Pair.with(1, -1)),
    EAST(Pair.with(2, 8)),
    WEST(Pair.with(3, -8)),
    NORTH_EAST(Pair.with(4, 9)),
    NORTH_WEST(Pair.with(5, -7)),
    SOUTH_EAST(Pair.with(6, 7)),
    SOUTH_WEST(Pair.with(7, -9));

    private Pair<Integer, Integer> value;

    PieceDirections(Pair<Integer, Integer> value) {
        this.value = value;
    }

    public int getDir() {
        return value.getValue0();
    }

    public int getValue() {
        return value.getValue1();
    }
}
