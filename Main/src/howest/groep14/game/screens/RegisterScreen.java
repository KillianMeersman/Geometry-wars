package howest.groep14.game.screens;


import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.Viewport;

public class RegisterScreen implements Screen {
    private Stage stage;
    private Viewport viewport;
    private Skin skin;

    public RegisterScreen(Viewport viewport, Skin skin) {
        this.viewport = viewport;
        this.skin = skin;
        stage = new Stage(viewport);
    }

    @Override
    public void show() {
        stage.clear();
        Label dick = new Label()
    }

    @Override
    public void render(float delta) {

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
