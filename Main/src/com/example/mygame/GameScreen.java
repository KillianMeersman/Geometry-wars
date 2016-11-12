package com.example.mygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sun.corba.se.impl.oa.poa.ActiveObjectMap;

public class GameScreen implements Screen {
    private GameStage stage;
    private Label score1Label;
    private Label centerLabel;
    private Skin skin;
    private boolean paused = false;
    private float lastDelta = 0.2f;

    public GameScreen(Viewport viewport, Skin skin) {
        stage = new GameStage(viewport);
        Gdx.input.setInputProcessor(stage);
        this.skin = skin;
        PlayerActor player = new PlayerActor(stage);
        player.setPosition(50, 50);
        stage.addPlayer(player);
        stage.setKeyboardFocus(player);
        createUI();
    }

    // Create UI elements
    private void createUI() {
        Label.LabelStyle style = new Label.LabelStyle(skin.getFont("default"), Color.DARK_GRAY);
        skin.add("default", style);
        score1Label = new Label("TEST", style);
        score1Label.setPosition(10, Gdx.graphics.getHeight() - 30);
        stage.addActor(score1Label);
    }

    public void setScore1Label(String text) {
        score1Label.setText(text);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            if (lastDelta > 0.1f) {
                if (paused) {
                    resume();
                } else {
                    pause();
                }
                lastDelta = 0;
            } else {
                lastDelta += delta;
            }
        }

        if (!paused) {
            setScore1Label(stage.getPlayers().get(0).getScore() + " KILLS");
            stage.act(delta);
        } else {
            setScore1Label("PAUSED");
        }
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {
        paused  = true;
    }

    @Override
    public void resume() {
        paused = false;
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
