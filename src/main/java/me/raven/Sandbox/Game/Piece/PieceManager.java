package me.raven.Sandbox.Game.Piece;

import me.raven.Engine.Renderer.Renderer;
import me.raven.Engine.Utils.Texture;
import me.raven.Sandbox.Game.Board.PiecePlacerFEN;
import me.raven.Sandbox.Game.Piece.Pieces.*;
import org.joml.Vector2f;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PieceManager {

    private static PieceManager instance;
    private Queue<Piece> pieces = new ConcurrentLinkedQueue<>();
    private PieceColors turn = PieceColors.WHITE;

    Texture blackPawn = new Texture("resources/black_pawn.png");
    Texture whitePawn = new Texture("resources/white_pawn.png");

    public PieceManager() {
        instance = this;
        PiecePlacerFEN.placePieces("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR", this);
    }

    public void onUpdate() {
        for (Piece piece : pieces) {
            if (piece.data.color == turn) {
                piece.onUpdate();
            }
         }
    }

    public void onRender(Renderer renderer) {
        pieces.forEach(p -> p.onRender(renderer));
    }

    public void removePiece(Piece piece) {
        pieces.remove(piece);
    }

    public void changeTurn(Piece piecePar) {
        turn = PieceColors.changeTurn(turn);
    }

    private void calculateKingChecks(PieceColors color) {
        King king = getKingByColor(color);
    }

    public Queue<Piece> getPieces() {
        return pieces;
    }

    public Queue<Piece> getPiecesByColor(PieceColors color) {
        Queue<Piece> temp = new ConcurrentLinkedQueue<>();
        for (Piece piece : pieces) {
            if (piece.data.color == color) temp.add(piece);
        }
        return temp;
    }

    public boolean isKingChecked(PieceColors color) {
        for (Piece piece : pieces) {
            if (PieceClass.isInstance(piece, PieceClass.KING) && piece.data.color == color) {
                King king = (King) piece;
                return king.isChecked;
            }
        }
        return false;
    }

    public Queue<Piece> piecesCheckedBy(PieceColors color) {
        for (Piece piece : pieces) {
            if (PieceClass.isInstance(piece, PieceClass.KING) && piece.data.color == color) {
                King king = (King) piece;
                return king.checkedBy;
            }
        }
        return null;
    }

    public boolean canBlockByPrey(int nextTile, PieceColors color) {
        for (Piece piece : piecesCheckedBy(color)) {
            if (piece.data.tile != nextTile) continue;
            System.out.println("block by prey");
            return true;
        }
        return false;
    }

    public King getKingByColor(PieceColors color) {
        for (Piece piece : getPiecesByColor(color)) {
            if (PieceClass.isInstance(piece, PieceClass.KING)) return (King) piece;
        }
        return null;
    }

    public static PieceManager get() {
        return instance;
    }

    public void addPawn(PieceColors color, int tile) {
        if (color == PieceColors.BLACK)
            pieces.add(new Pawn(new Vector2f(100.f, 100.f), blackPawn, color, tile));
        else
            pieces.add(new Pawn(new Vector2f(100.f, 100.f), whitePawn, color, tile));
    }

    public void addKnight(PieceColors color, int tile) {
        Texture texture = new Texture("resources/" + color.get() + "_horse.png");
        pieces.add(new Knight(new Vector2f(100.f, 100.f), texture, color, tile));
    }

    public void addBishop(PieceColors color, int tile) {
        Texture texture = new Texture("resources/" + color.get() + "_bishop.png");
        pieces.add(new Bishop(new Vector2f(100.f, 100.f), texture, color, tile));
    }

    public void addQueen(PieceColors color, int tile) {
        Texture texture = new Texture("resources/" + color.get() + "_queen.png");
        pieces.add(new Queen(new Vector2f(100.f, 100.f), texture, color, tile));
    }

    public void addKing(PieceColors color, int tile) {
        Texture texture = new Texture("resources/" + color.get() + "_king.png");
        pieces.add(new King(new Vector2f(100.f, 100.f), texture, color, tile));
    }

    public void addRook(PieceColors color, int tile) {
        Texture texture = new Texture("resources/" + color.get() + "_rock.png");
        pieces.add(new Rock(new Vector2f(100.f, 100.f), texture, color, tile));
    }
}
