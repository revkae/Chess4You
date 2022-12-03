package me.raven.Sandbox.Game;

import me.raven.Engine.Renderer.Renderer;
import me.raven.Engine.Shapes.Quad;
import me.raven.Engine.Utils.Texture;
import me.raven.Sandbox.Game.Pieces.Pawn;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

public class PieceManager {

    private List<Piece> pieces = new ArrayList<>();
    private Queue<Integer> pieceTiles = new ConcurrentLinkedQueue<>();

    public PieceManager() {
        Texture whitePawn = new Texture("resources/black_pawn.png");
        pieces.add(new Pawn(new Vector2f(50.f, 50.f), whitePawn, 9));
    }

    public void onUpdate() {
        pieces.forEach(Piece::onUpdate);
    }

    public void onRender(Renderer renderer) {
        pieces.forEach(p -> p.onRender(renderer));
    }

    public void removePiece(Piece piece) {
        pieces.remove(piece);
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public Queue<Integer> getPieceTiles() {
        return pieceTiles;
    }
}
