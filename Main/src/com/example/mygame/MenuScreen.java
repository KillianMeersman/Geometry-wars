package com.example.mygame;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

public class MenuScreen implements Screen {
    private TextButton[] buttons = new TextButton[5];
    private Stage stage;

    public MenuScreen(Viewport viewport, Skin skin) {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float buttonWidth = screenWidth / 2;

        stage = new Stage(viewport);
        Gdx.input.setInputProcessor(stage);
        TextButton campaignButton = generateButton(skin, "C A M P A I G N", screenWidth / 4, screenHeight - 125, buttonWidth, 75);
        stage.addActor(campaignButton);
        TextButton skirmishButton = generateButton(skin, "S K I R M I S H", screenWidth / 4, screenHeight - 215, buttonWidth, 75);
        skirmishButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GeometryWars main = GeometryWars.getInstance();
                main.setScreen(main.getGameScreen());
        }
        });
        stage.addActor(skirmishButton);
        TextButton multiPlayer = generateButton(skin, "M U L T I P L A Y E R", screenWidth / 4, screenHeight - 305, buttonWidth, 75);
        stage.addActor(multiPlayer);

    }

    private TextButton generateButton(Skin skin, String text, float x, float y, float width, float height) {
        TextButton button = new TextButton(text, skin);
        button.setWidth(width);
        button.setHeight(height);
        button.setPosition(x, y);
        return button;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
