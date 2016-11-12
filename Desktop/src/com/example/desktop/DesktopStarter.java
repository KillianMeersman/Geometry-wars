package com.example.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.example.mygame.GameScreen;

//This is the frontend, running this class will display a screen
public class DesktopStarter {
    public static void main(String[] args){
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Example";
        cfg.width = 1200;
        cfg.height = 650;
        new LwjglApplication(GameScreen.getInstance(),cfg);
    }
}
