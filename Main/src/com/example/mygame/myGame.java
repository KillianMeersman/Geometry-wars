package com.example.mygame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;

//This is the entry point for the game
public class myGame extends com.badlogic.gdx.Game {
    public static int WIDTH;
    public static int HEIGHT;

    @Override
    public void create(){
        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();
    }

    public void render(){
        //clear screen to black
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
    }
}
