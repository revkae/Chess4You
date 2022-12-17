package me.raven.Sandbox.Game.Piece;

public enum PieceColors {

    BLACK("black"),
    WHITE("white");

    private String value;

    PieceColors(String value) {
        this.value = value;
    }

    public static boolean equals(PieceColors c0, PieceColors c1) {
        return c0 == c1;
    }

    public static PieceColors changeTurn(PieceColors turn) {
        if (turn == BLACK) {
            return WHITE;
        } else {
            return BLACK;
        }
    }

    public static PieceColors getOpposite(PieceColors color) {
        if (color == BLACK) {
            return WHITE;
        } else {
            return BLACK;
        }
    }

    public String get() {
        return value;
    }
}
