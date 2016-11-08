package com.example.mygame;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


//This is the entry point for the game
public class myGame extends ApplicationAdapter{
    public static int WIDTH; //800px
    public static int HEIGHT; //480px
    Stage stage;

    @Override
    public void create(){
        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();

        OrthographicCamera camera = new OrthographicCamera(WIDTH,HEIGHT);   //set Camera to the gamesize
        camera.translate(WIDTH/2, HEIGHT/2);                                //Change the position of the camera (By default the origin is centered)
        camera.update(); //Update camera to new location

        stage = new Stage(new ScreenViewport(camera));
        Gdx.input.setInputProcessor(stage);
        PlayerActor player = new PlayerActor();
        stage.addActor(player);
        stage.setKeyboardFocus(player);
    }

    public void render(){
        //clear screen to black
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}
