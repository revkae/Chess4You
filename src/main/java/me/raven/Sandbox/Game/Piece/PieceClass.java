package me.raven.Sandbox.Game.Piece;

import me.raven.Sandbox.Game.Piece.Pieces.Bishop;
import me.raven.Sandbox.Game.Piece.Pieces.King;
import me.raven.Sandbox.Game.Piece.Pieces.Knight;
import me.raven.Sandbox.Game.Piece.Pieces.Pawn;
import me.raven.Sandbox.Game.Piece.Pieces.Queen;
import me.raven.Sandbox.Game.Piece.Pieces.Rock;

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
