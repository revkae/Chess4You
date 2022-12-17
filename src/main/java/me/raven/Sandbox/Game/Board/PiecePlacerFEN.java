package me.raven.Sandbox.Game.Board;

import me.raven.Sandbox.Game.Piece.PieceColors;
import me.raven.Sandbox.Game.Piece.PieceManager;
import org.apache.commons.lang3.StringUtils;

public class PiecePlacerFEN {

    public static void placePieces(String fen, PieceManager pieceManager) {
        int file = 0;
        int rank = 0;
        int tile;
        PieceColors color;

        for (char c : fen.toCharArray()) {
            if (StringUtils.isNumeric(String.valueOf(c))) {
                rank = c;
            } else if (c == '/') {
                file += 1;
            } else if (Character.isUpperCase(c)) {
                color = PieceColors.WHITE;
                tile = 7 * file + rank;
                System.out.println(tile);
                tile--;
                switch (c) {
                    case 'B' -> pieceManager.addBishop(color, tile);
                    case 'K' -> pieceManager.addKing(color, tile);
                    case 'P' -> pieceManager.addPawn(color, tile);
                    case 'N' -> pieceManager.addKnight(color, tile);
                    case 'Q' -> pieceManager.addQueen(color, tile);
                    case 'R' -> pieceManager.addRook(color, tile);
                }
            } else if (Character.isLowerCase(c)) {
                color = PieceColors.BLACK;
                tile = 7 * file + rank;
                switch (c) {
                    case 'b' -> pieceManager.addBishop(color, tile);
                    case 'k' -> pieceManager.addKing(color, tile);
                    case 'p' -> pieceManager.addPawn(color, tile);
                    case 'n' -> pieceManager.addKnight(color, tile);
                    case 'q' -> pieceManager.addQueen(color, tile);
                    case 'r' -> pieceManager.addRook(color, tile);
                }
            }
        }
    }
}
