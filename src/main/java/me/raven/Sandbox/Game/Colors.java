package me.raven.Sandbox.Game;

import org.joml.Vector4f;

public enum Colors {

    BOARD_SELECTION(new Vector4f(1.f, 0.f, 0.f, 1.f)),
    PIECE_SELECTION(new Vector4f(0.f, 1.f, 0.f, 1.f)),
    RESET(new Vector4f(1.f));

    public Vector4f color;

    Colors(Vector4f color) {
        this.color = color;
    }
}
