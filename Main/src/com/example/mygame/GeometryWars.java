package com.example.mygame;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GeometryWars extends ApplicationAdapter{
    public static int WIDTH; //800px
    public static int HEIGHT; //480px
    Skin skin;
    Stage stage;

    PlayerActor player;

    @Override
    public void create(){
        skin = new Skin();
        BitmapFont bfont = new BitmapFont();
        skin.add("default", bfont);

        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();


        OrthographicCamera camera = new OrthographicCamera(WIDTH,HEIGHT);   //set Camera to the gamesize
        camera.translate(WIDTH/2, HEIGHT/2);                                //Change the position of the camera (By default the origin is centered)
        camera.update(); //Update camera to new location

        stage = new Stage(new ScreenViewport(camera));
        Gdx.input.setInputProcessor(stage);
        PlayerActor player = new PlayerActor();
        this.player = player;
        player.setPosition(50, 50);
        stage.addActor(player);
        stage.setKeyboardFocus(player);
        createUI();
    }

    public void createUI() {
        Label.LabelStyle style = new Label.LabelStyle(skin.getFont("default"), Color.WHITE);
        skin.add("default", style);
        Label label = new Label("TEST", style);
        label.setPosition(10, HEIGHT - 30);
        stage.addActor(label);
    }

    public void render(){
        //clear screen to black
        for (Actor actor : stage.getActors()) {
            if (actor instanceof Label) {
                ((Label) actor).setText(Integer.toString(player.projectilesFired) + " ROUNDS FIRED");
            }
        }
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }
}
