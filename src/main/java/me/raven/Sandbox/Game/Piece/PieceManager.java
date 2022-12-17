package me.raven.Sandbox.Game.Piece;

import me.raven.Engine.Renderer.Renderer;
import me.raven.Engine.Utils.Texture;
import me.raven.Sandbox.Game.Board.PiecePlacerFEN;
import me.raven.Sandbox.Game.Piece.Pieces.*;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class PieceManager {
    private List<Piece> pieces = new ArrayList<>();

    private PieceColors turn = PieceColors.WHITE;

    public PieceManager() {
//        Texture blackPawn = new Texture("resources/black_pawn.png");
//        pieces.add(new Pawn(new Vector2f(100.f, 100.f), blackPawn, PieceColors.BLACK, 3));
//        Texture whitePawn = new Texture("resources/white_pawn.png");
//        pieces.add(new Pawn(new Vector2f(100.f, 100.f), whitePawn, PieceColors.WHITE, 13));
//        Texture whiteHorse = new Texture("resources/white_horse.png");
//        pieces.add(new Knight(new Vector2f(100.f, 100.f), whiteHorse, PieceColors.WHITE, 34));
//        Texture whiteRock = new Texture("resources/white_rock.png");
//        pieces.add(new Rock(new Vector2f(100.f, 100.f), whiteRock, PieceColors.WHITE, 0));
//        Texture whiteBishop = new Texture("resources/white_bishop.png");
//        pieces.add(new Bishop(new Vector2f(100.f, 100.f), whiteBishop, PieceColors.WHITE, 25));
//        Texture whiteKing = new Texture("resources/white_king.png");
//        pieces.add(new King(new Vector2f(100.f, 100.f), whiteKing, PieceColors.WHITE, 24));
//        Texture whiteQueen = new Texture("resources/white_queen.png");
//        pieces.add(new Queen(new Vector2f(100.f, 100.f), whiteQueen, PieceColors.WHITE, 10));
        PiecePlacerFEN.placePieces("r1b1k1nr/p2p1pNp/n2B4/1p1NP2P/6P1/3P1Q2/P1P1K3/q5b1", this);
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
        //turn = PieceColor.changeTurn(turn);
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public void addPawn(PieceColors color, int tile) {
        Texture texture = new Texture("resources/" + color.get() + "_queen.png");
        pieces.add(new Queen(new Vector2f(100.f, 100.f), texture, color, tile));
    }

    public void addKnight(PieceColors color, int tile) {
        Texture texture = new Texture("resources/" + color.get() + "_knight.png");
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
