package com.example.mygame;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.example.mygame.enemy.EnemyActor;
import com.example.mygame.enemy.KamikazeBehavior;

public class GeometryWars extends ApplicationAdapter{
    public static int WIDTH; //800px
    public static int HEIGHT; //480px
    Skin skin;
    GameStage stage;
    Viewport viewport;
    OrthographicCamera camera;

    PlayerActor player;

    @Override
    public void create(){
        skin = new Skin();
        BitmapFont bfont = new BitmapFont();
        skin.add("default", bfont);

        WIDTH = Gdx.graphics.getWidth();
        HEIGHT = Gdx.graphics.getHeight();


        camera = new OrthographicCamera(WIDTH,HEIGHT);   //set Camera to the gamesize
        //camera.translate(WIDTH/2, HEIGHT/2);                                //Change the position of the camera (By default the origin is centered)
        camera.update(); //Update camera to new location
        viewport = new ScreenViewport(camera);

        stage = new GameStage(viewport);
        createUI();

        Gdx.input.setInputProcessor(stage);
        PlayerActor player = new PlayerActor();
        this.player = player;
        player.setPosition(50, 50);
        stage.addActor(player);
        stage.setKeyboardFocus(player);


        Texture bacteria2 = new Texture("Desktop/Assets/bacteria2.png");
        Sprite bacteria2Sprite = new Sprite(bacteria2);
        for (int i = 0; i < 7; i++) {
            EnemyActor enemy = new EnemyActor(bacteria2Sprite, 0.5f);
            enemy.setBehavior(new KamikazeBehavior(enemy, player, 3));
            stage.addActor(enemy);
        }
    }

    // Create UI elements
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

    // what to do when screen is resized
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.update();
        WIDTH = viewport.getScreenWidth();
        HEIGHT = viewport.getScreenHeight();
    }
}
