package me.raven.Sandbox.Game;

import me.raven.Engine.Renderer.Renderer;
import me.raven.Engine.Utils.Texture;
import me.raven.Sandbox.Game.Pieces.*;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class PieceManager {

    private List<Piece> pieces = new ArrayList<>();

    private PieceColor turn = PieceColor.WHITE;

    public PieceManager() {
        Texture blackPawn = new Texture("resources/black_pawn.png");
        pieces.add(new Pawn(new Vector2f(50.f, 50.f), blackPawn, PieceColor.BLACK, 3));
        Texture whitePawn = new Texture("resources/white_pawn.png");
        pieces.add(new Pawn(new Vector2f(50.f, 50.f), whitePawn, PieceColor.WHITE, 13));
        Texture whiteHorse = new Texture("resources/white_horse.png");
        pieces.add(new Horse(new Vector2f(50.f, 50.f), whiteHorse, PieceColor.WHITE, 34));
        Texture whiteRock = new Texture("resources/white_rock.png");
        pieces.add(new Rock(new Vector2f(50.f, 50.f), whiteRock, PieceColor.WHITE, 0));
        Texture whiteBishop = new Texture("resources/white_bishop.png");
        pieces.add(new Bishop(new Vector2f(50.f, 50.f), whiteBishop, PieceColor.WHITE, 63));
        Texture whiteKing = new Texture("resources/white_king.png");
        pieces.add(new King(new Vector2f(50.f, 50.f), whiteKing, PieceColor.WHITE, 24));
        Texture whiteQueen = new Texture("resources/white_queen.png");
        pieces.add(new Queen(new Vector2f(50.f, 50.f), whiteQueen, PieceColor.WHITE, 10));
    }

    public void onUpdate() {
        for (Piece piece : pieces) {
            if (piece.pieceColor == turn) {
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

}
