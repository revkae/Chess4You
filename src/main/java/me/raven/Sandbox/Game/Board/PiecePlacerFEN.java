package me.raven.Sandbox.Game.Board;

import me.raven.Sandbox.Game.Piece.PieceColors;
import me.raven.Sandbox.Game.Piece.PieceManager;
import org.apache.commons.lang3.StringUtils;

public class PiecePlacerFEN {

    public static void placePieces(String fen, PieceManager pieceManager) {
        int file = 0;
        int rank = 0;
        int tile = 0;
        PieceColors color;

        for (int i = 0; i < fen.length(); i++) {
            if (StringUtils.isNumeric(String.valueOf(fen.charAt(i)))) {
                file += Integer.parseInt(String.valueOf(fen.charAt(i)));
            }
            else if (fen.charAt(i) == '/') {
                rank += 1;
                file = 0;
            } else if (Character.isUpperCase(fen.charAt(i))) {
                color = PieceColors.WHITE;
                tile = 8 * file + rank;
                if (tile > 63 || tile < 0) continue;
                switch (fen.charAt(i)) {
                    case 'B' -> pieceManager.addBishop(color, tile);
                    case 'K' -> pieceManager.addKing(color, tile);
                    case 'P' -> pieceManager.addPawn(color, tile);
                    case 'N' -> pieceManager.addKnight(color, tile);
                    case 'Q' -> pieceManager.addQueen(color, tile);
                    case 'R' -> pieceManager.addRook(color, tile);
                }

                file += 1;
            }
            else if (Character.isLowerCase(fen.charAt(i))) {
                color = PieceColors.BLACK;
                tile = 8 * file + rank;
                if (tile > 63 || tile < 0) continue;
                switch (fen.charAt(i)) {
                    case 'b' -> pieceManager.addBishop(color, tile);
                    case 'k' -> pieceManager.addKing(color, tile);
                    case 'p' -> pieceManager.addPawn(color, tile);
                    case 'n' -> pieceManager.addKnight(color, tile);
                    case 'q' -> pieceManager.addQueen(color, tile);
                    case 'r' -> pieceManager.addRook(color, tile);
                }
                file += 1;
            }
        }
    }
}
