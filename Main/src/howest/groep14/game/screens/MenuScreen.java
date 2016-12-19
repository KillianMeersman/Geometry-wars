package howest.groep14.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import howest.groep14.game.CustomUtils;
import howest.groep14.game.GeometryWars;
import howest.groep14.game.actor.SpriteActor;
import howest.groep14.game.player.PlayerRepository;

public class MenuScreen implements Screen {
    private Stage stage;
    private Viewport viewport;
    private Skin skin;

    public MenuScreen(Viewport viewport, Skin skin) {
        this.viewport = viewport;
        this.skin = skin;
        stage = new Stage(viewport);
    }

    private void createUI( int width, int height) {
        stage.clear();
        float buttonWidth = width / 2;
        float element_width;
        float element_height;
        final float margin = 15;


        element_width = 300;
        element_height = 25;
        final TextField usernameField = new TextField("", skin);
        usernameField.setWidth(element_width);
        usernameField.setHeight(element_height);
        usernameField.setPosition(width - element_width - margin, height - element_height - margin);
        stage.addActor(usernameField);

        final TextField passwordField = new TextField("", skin);
        passwordField.setWidth(element_width);
        passwordField.setHeight(element_height);
        passwordField.setPosition(width - element_width - margin, height - element_height * 2 - margin * 2);
        stage.addActor(passwordField);

        final Label loginNotification = new Label("", skin);
        loginNotification.setWidth(300);
        loginNotification.setHeight(50);
        loginNotification.setPosition(width - 300 - margin, height - 205);
        stage.addActor(loginNotification);
        if (!GeometryWars.getInstance().connectionReady()) {
            loginNotification.setText("CONNECTION UNAVAILABLE");
        }

        element_width = 140;
        element_height = 50;
        TextButton loginButton = CustomUtils.generateTextButton(skin, "LOGIN", width - 300 - margin, height - 150, element_width,element_height);
        loginButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    PlayerRepository repo = GeometryWars.getInstance().getPlayerRepository();
                    if (repo.loginPlayer(usernameField.getText(), passwordField.getText())) {
                        loginNotification.setText("WELCOME " + repo.getActivePlayer().getUsername());
                    } else {
                        loginNotification.setText("INVALID LOGIN");
                    }
                } catch (Exception e) {
                    loginNotification.setText("USER NOT FOUND");
                }
            }
        });
        stage.addActor(loginButton);

        TextButton registerButton = CustomUtils.generateTextButton(skin, "REGISTER", width - element_width - margin, height - 150, element_width, element_height);
        stage.addActor(registerButton);

        TextButton campaignButton = CustomUtils.generateTextButton(skin, "C A M P A I G N (not available)", width / 4, height - 125, buttonWidth, 75);
        stage.addActor(campaignButton);

        TextButton skirmishButton = CustomUtils.generateTextButton(skin, "S K I R M I S H", width / 4, height - 215, buttonWidth, 75);
        skirmishButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GeometryWars main = GeometryWars.getInstance();
                main.setScreen(main.getGameScreen());
            }
        });
        stage.addActor(skirmishButton);

        TextButton multiPlayer = CustomUtils.generateTextButton(skin, "M U L T I P L A Y E R (not available)", width / 4, height - 305, buttonWidth, 75);
        stage.addActor(multiPlayer);

        TextButton quitButton = CustomUtils.generateTextButton(skin, "Q U I T", width / 4, height - 395, buttonWidth / 2 - 15, 75);
        stage.addActor(quitButton);
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        TextButton settingsButton = CustomUtils.generateTextButton(skin, "S E T T I N G S", width / 2 + 15, height - 395, buttonWidth / 2 - 15, 75);

        settingsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GeometryWars main = GeometryWars.getInstance();
                main.setScreen(main.getSettingScreen());
            }
        });

        stage.addActor(settingsButton);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        //GeometryWars.getInstance().getViewPort().setWorldSize(width, height);
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
