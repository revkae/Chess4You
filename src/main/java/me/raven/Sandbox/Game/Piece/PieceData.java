package me.raven.Sandbox.Game.Piece;

import me.raven.Engine.Shapes.Quad;
import org.joml.Vector3f;
import org.joml.Vector4f;

public class PieceData {

    public Quad piece;
    public int tile;
    public int value;
    public PieceColors color;

    public PieceData(Quad piece, int tile, int value, PieceColors color) {
        this.piece = piece;
        this.tile = tile;
        this.value = value;
        this.color = color;
    }

    public void updateData(Vector3f position, int tile) {
        this.piece.setPosition(position);
        this.tile = tile;
    }

    public void updateData(Vector4f color) {
        this.piece.setColor(color);
    }
}
