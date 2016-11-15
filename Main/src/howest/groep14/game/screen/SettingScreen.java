package howest.groep14.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;

public class SettingScreen implements Screen {
    private Stage stage;

    public SettingScreen(Viewport viewport, Skin skin) {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        stage = new Stage(viewport);

        Label label = new Label("Player 1", skin);
        label.setPosition(screenWidth / 2, screenHeight - 10, Align.top);
        stage.addActor(label);

        label = new Label("Forward", skin);
        label.setPosition(10, screenHeight - 60);
        stage.addActor(label);

        TextField field = new TextField("", skin);
        field.setPosition(50, screenHeight - 20);
        field.setWidth(100);
        field.setHeight(50);
        stage.addActor(field);
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
