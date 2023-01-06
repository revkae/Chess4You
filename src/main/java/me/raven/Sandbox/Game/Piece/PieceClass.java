package me.raven.Sandbox.Game.Piece;

import me.raven.Sandbox.Game.Piece.Pieces.*;

public enum PieceClass {

    BISHOP(Bishop.class),
    KING(King.class),
    KNIGHT(Knight.class),
    PAWN(Pawn.class),
    QUEEN(Queen.class),
    ROOK(Rock.class);

    private Class pieceClass;

    PieceClass(Class pieceClass) {
        this.pieceClass = pieceClass;
    }

    public static boolean isInstance(Piece piece, PieceClass instance) {
        return piece.getClass().isAssignableFrom(instance.getValue());
    }

    public Class getValue() {
        return pieceClass;
    }
}
