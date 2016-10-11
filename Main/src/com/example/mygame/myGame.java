package com.example.mygame;

import GameManagers.GameInputProcessor;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;

//This is the entry point for the game
public class myGame extends com.badlogic.gdx.Game {
    public static int WIDTH; //800px
    public static int HEIGHT; //480px

    @Override
    public void create(){
        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();

        OrthographicCamera camera = new OrthographicCamera(WIDTH,HEIGHT);   //set Camera to the gamesize
        camera.translate(WIDTH/2, HEIGHT/2);                                //Change the position of the camera (By default the origin is centered)
        camera.update();                                                    //Update camera to new location

        Gdx.input.setInputProcessor(new GameInputProcessor());              //Set InputProcessor to our /GameManagers/GameInputProcessor

    }

    public void render(){
        //clear screen to black
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
    }
}
