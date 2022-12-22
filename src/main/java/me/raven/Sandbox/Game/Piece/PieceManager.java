package me.raven.Sandbox.Game.Piece;

import me.raven.Engine.Renderer.Renderer;
import me.raven.Engine.Utils.Texture;
import me.raven.Sandbox.Game.Board.PiecePlacerFEN;
import me.raven.Sandbox.Game.Piece.Pieces.*;
import org.joml.Vector2f;

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

    public void changeTurn() {
        calculateKingChecks(turn);
        turn = PieceColors.changeTurn(turn);
        calculateKingChecks(turn);
    }

    private void calculateKingChecks(PieceColors color) {
        King king = getKingByColor(color);

        for (Piece piece : getPiecesByColor(PieceColors.getOpposite(color))) {
            for (PieceDirections dir : PieceDirections.values()) {
                piece.calculatePossiblePreys(dir);
            }
            if (piece.preys.contains(king)) {
                System.out.println("checked");
                king.checkedBy.add(piece);
                king.isChecked = true;
            }
        }
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

    public King getKingByColor(PieceColors color) {
        for (Piece piece : getPiecesByColor(color)) {
            if (PieceClass.isInstance(piece, PieceClass.KING)) return (King) piece;
        }
        return null;
    }

    public void isEmpty(Piece piece, int nextTile) {
        for (Piece temp : PieceManager.get().getPieces()) {
            if (temp.tempTile != nextTile) {
                if (piece.moves.contains(nextTile)) return;

                piece.addMove(nextTile);
                return;
            }
        }
    }

    public boolean hasAlly(Piece piece, int nextTile) {
        for (Piece temp : PieceManager.get().getPieces()) {
            if (temp.tempTile == nextTile && temp.data.color == piece.data.color) {
                return true;
            }
        }
        return false;
    }

    public boolean hasEnemy(Piece piece, int nextTile) {
        for (Piece temp : PieceManager.get().getPieces()) {
            if (temp.tempTile == nextTile && temp.data.color != piece.data.color) {
                return true;
            }
        }
        return false;
    }

    public boolean isEnemy(Piece piece, int nextTile) {
        for (Piece temp : PieceManager.get().getPieces()) {
            if (temp.tempTile == nextTile && temp.data.color != piece.data.color) {
                if (piece.preys.contains(temp)) return true;

                piece.addPrey(temp);
                return true;
            }
        }
        return false;
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
