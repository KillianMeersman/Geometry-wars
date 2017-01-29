package howest.groep14.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import howest.groep14.game.CustomUtils;
import howest.groep14.game.GeometryWars;
import howest.groep14.game.player.GameMapper;
import howest.groep14.game.player.Player;
import howest.groep14.game.player.PlayerRepository;

public class LoginScreen implements Screen {

    private int WIDTH;
    private int HEIGHT;
    private int BUTTON_WIDTH;
    private int BUTTON_HEIGHT;

    private Stage stage;
    private Viewport viewport;
    private Skin skin;
    private Player loggedInPlayer;

    Texture background;
    Texture loginScreen;
    Texture loginButton;
    Texture registerButton;

    TextField usernameField;
    TextField passwordField;
    Label loginNotification;


    public LoginScreen(Viewport viewport, Skin skin) {
        this.viewport = viewport;
        this.skin = skin;
        this.WIDTH = Gdx.graphics.getWidth();
        this.HEIGHT = Gdx.graphics.getHeight();
        this.BUTTON_WIDTH =WIDTH/4-50;
        this.BUTTON_HEIGHT=WIDTH/20;

        stage = new Stage(viewport);

        background = new Texture(Gdx.files.internal("screen.png"));
        loginScreen = new Texture(Gdx.files.internal("screen-login.png"));
        loginButton = new Texture(Gdx.files.internal("btn-login.png"));
        registerButton = new Texture(Gdx.files.internal("btn-register.png"));
    }

    public void doLogin(){
        try {
            PlayerRepository repo = PlayerRepository.getInstance();
            if (repo.loginPlayer(usernameField.getText(), passwordField.getText())) {
                loggedInPlayer = PlayerRepository.getInstance().getPlayerByUsername(usernameField.getText());
                loggedInPlayer.getShips().addAll(PlayerRepository.getInstance().getPlayerShipsByPlayerID(loggedInPlayer));
                GeometryWars main = GeometryWars.getInstance();
                main.setScreen(main.getMenuScreen());
            } else {
                loginNotification.setText("INVALID LOGIN");
            }
        } catch (Exception e) {
            loginNotification.setText("USER NOT FOUND");
        }

    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
;    }

    private void createUI(){
        usernameField = new TextField("", skin);
        usernameField.setWidth(WIDTH/4);
        usernameField.setHeight(WIDTH/20);
        usernameField.setPosition(WIDTH/2 - 70, HEIGHT/2 + 42);
        stage.addActor(usernameField);

        passwordField = new TextField("", skin);
        passwordField.setWidth(WIDTH/4);
        passwordField.setHeight(WIDTH/20);
        passwordField.setPosition(WIDTH/2 - 70, HEIGHT/2 - 17);
        stage.addActor(passwordField);

        loginNotification = new Label("", skin);
        loginNotification.setWidth(WIDTH/4);
        loginNotification.setHeight(WIDTH/20);
        loginNotification.setPosition(WIDTH/2 - loginNotification.getWidth()/2 - 25, HEIGHT/2 - 175);
        stage.addActor(loginNotification);
        if (!GeometryWars.getInstance().connectionReady()) {
            loginNotification.setText("CONNECTION UNAVAILABLE");
        }
    }

    @Override
    public void render(float delta) {

        float color = 230 / 255f;
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(color, color, color, 1);
        stage.getBatch().begin();
        //Buttons
        stage.getBatch().draw(background, 0, 0, WIDTH, HEIGHT);
        stage.getBatch().draw(loginScreen,WIDTH/2-WIDTH/4,HEIGHT/2-WIDTH/8+50,WIDTH/2,WIDTH/4);
        stage.getBatch().draw(loginButton,WIDTH/2+25,HEIGHT/2-WIDTH/8+70, BUTTON_WIDTH,BUTTON_HEIGHT);

        TextButton loginBtn = CustomUtils.generateTextButton(skin, "", WIDTH/2+25, HEIGHT/2-WIDTH/8+70, BUTTON_WIDTH, BUTTON_HEIGHT);
        loginBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                doLogin();
            }
        });
        stage.addActor(loginBtn);

        stage.getBatch().draw(registerButton,WIDTH/2-25-(WIDTH/4-50),HEIGHT/2-WIDTH/8+70, WIDTH/4-50,WIDTH/20);
        TextButton registerBtn = CustomUtils.generateTextButton(skin, "", WIDTH/2-25-(WIDTH/4-50),HEIGHT/2-WIDTH/8+70, BUTTON_WIDTH, BUTTON_HEIGHT);
        registerBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                doRegister();
            }
        });
        stage.addActor(registerBtn);


        stage.getBatch().end();
        stage.draw();
       

    }

    private void doRegister() {
        GeometryWars main = GeometryWars.getInstance();
        main.setScreen(main.getRegisterScreenOld());
    }

    @Override
    public void resize(int width, int height) {
        createUI();
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
