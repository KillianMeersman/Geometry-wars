package main.game;

import main.engine.core.GameEngine;
import main.engine.core.IGameLogic;

public class Main {

    public static void main(String[] args) {
        try {
            boolean vSync = true;
            IGameLogic gameLogic = DummyGame.getInstance();
            GameEngine engine = new GameEngine("LWJGL test",
                    800, 600, vSync, gameLogic);
            engine.start();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }
}