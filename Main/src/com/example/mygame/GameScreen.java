package com.example.mygame;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GameScreen extends ApplicationAdapter{
    static int WIDTH; //800px
    static int HEIGHT; //480px
    private Skin skin;
    private GameStage stage;
    private Viewport viewport;
    private OrthographicCamera camera;

    private Label score1Label;
    private static GameScreen instance = new GameScreen();

    public static GameScreen getInstance() {
        return instance;
    }

    private GameScreen() {

    }

    @Override
    public void create(){
        skin = new Skin();
        BitmapFont bfont = new BitmapFont();
        skin.add("default", bfont);

        WIDTH = 1200;
        HEIGHT = 650;


        camera = new OrthographicCamera(WIDTH,HEIGHT);   //set Camera to the gamesize
        //camera.translate(WIDTH/2, HEIGHT/2);                                //Change the position of the camera (By default the origin is centered)
        camera.update(); //Update camera to new location
        viewport = new ScreenViewport(camera);

        stage = new GameStage(viewport);
        createUI();

        Gdx.input.setInputProcessor(stage);
        PlayerActor player = new PlayerActor(stage);
        player.setPosition(50, 50);
        stage.addPlayer(player);
        stage.setKeyboardFocus(player);
    }

    // Create UI elements
    public void createUI() {
        Label.LabelStyle style = new Label.LabelStyle(skin.getFont("default"), Color.WHITE);
        skin.add("default", style);
        score1Label = new Label("TEST", style);
        score1Label.setPosition(10, HEIGHT - 30);
        stage.addActor(score1Label);
    }

    public void render(){
        //clear screen to black
        Gdx.gl.glClear(GL30.GL_COLOR_BUFFER_BIT);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void setScore1Label(String text) {
        score1Label.setText(text);
    }

    // what to do when screen is resized
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.update();
        WIDTH = viewport.getScreenWidth();
        HEIGHT = viewport.getScreenHeight();
    }
}
