package me.raven.Sandbox.Game.Board;

import me.raven.Engine.Renderer.Renderer;
import me.raven.Engine.Shapes.Quad;
import me.raven.Engine.Utils.Texture;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.*;

public class BoardManager {

    private BoardSelection boardSelection;
    private List<Quad> board = new ArrayList<>(64);
    private List<List<Integer>> tileNumsToEdge = new ArrayList<>(64);

    public BoardManager() {
        this.boardSelection = new BoardSelection();
        Texture whiteTexture = new Texture("resources/whitetile.jpg");
        Texture blackTexture = new Texture("resources/blacktile.jpg");

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                loadTiles(x, y, whiteTexture, blackTexture);
            }
        }

        calculateSlidingMoves();
    }

    private void calculateSlidingMoves() {
        int count = 0;
        for (int file = 0; file < 8; file++) {
            for (int rank = 0; rank < 8; rank++) {

                int numNorth = 7 - rank;
                int numSouth = rank;
                int numEast = 7 - file;
                int numWest = file;

                tileNumsToEdge.add(count, Arrays.asList(
                        numNorth,
                        numSouth,
                        numEast,
                        numWest,
                        Math.min(numNorth, numEast),
                        Math.min(numNorth, numWest),
                        Math.min(numSouth, numEast),
                        Math.min(numSouth, numWest))
                );
                ++count;
            }
        }
    }

    private void loadTiles(int x, int y, Texture whiteTexture, Texture blackTexture) {
        if ((x + y) % 2 == 0) {
            board.add(new Quad(new Vector3f(0.f + x * 100.f, 0.f + y * 100.f, 0.0f), new Vector2f(100.f, 100.f), whiteTexture));
        }
        else {
            board.add(new Quad(new Vector3f(0.f + x * 100.f, 0.f + y * 100.f, 0.0f), new Vector2f(100.f, 100.f), blackTexture));
        }
    }

    public void onUpdate() {
        boardSelection.onUpdate();
    }

    public void onRender(Renderer renderer) {
        for (Quad quad : board) {
            renderer.texturedDraw(quad);
        }
    }

    public List<Quad> getBoard() {
        return board;
    }

    public List<List<Integer>> getTileNumsToEdge() {
        return tileNumsToEdge;
    }
}
