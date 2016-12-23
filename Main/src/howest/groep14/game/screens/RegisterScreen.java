package howest.groep14.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
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

    }

    private void createUI(int width, int height) {
        final float label_width = 300;
        final float x_center = Gdx.graphics.getWidth() / 2;

        stage.clear();
        Label usernameLabel = new Label("Username:", skin);
        usernameLabel.setWidth(label_width);
        usernameLabel.setHeight(20);
        usernameLabel.setPosition(x_center - 350, height - 50);
        stage.addActor(usernameLabel);

        TextField usernameField = new TextField("", skin);
        usernameField.setWidth(500);
        usernameField.setHeight(20);
        usernameField.setPosition(x_center - 250, height - 50);
        stage.addActor(usernameField);
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        createUI(width, height);
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
