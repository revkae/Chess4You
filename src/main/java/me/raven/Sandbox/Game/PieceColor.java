package me.raven.Sandbox.Game;

public enum PieceColor {

    BLACK,
    WHITE;

    public static boolean equals(PieceColor c0, PieceColor c1) {
        return c0 == c1;
    }

    public static PieceColor getOpposite(PieceColor color) {
        if (color == BLACK) {
            return WHITE;
        } else {
            return BLACK;
        }
    }
}
