package me.raven;

import me.raven.Sandbox.Managers.GameManager;

public class Main {

    public static void main(String[] args) {
        GameManager gameManager = new GameManager();
        gameManager.loop();
    }
}