package howest.groep14.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.Viewport;
import howest.groep14.game.GeometryWars;

public class LoginScreen implements Screen {

    private int WIDTH;
    private int HEIGHT;

    private Stage stage;
    private Viewport viewport;
    private Skin skin;

    Texture background;
    Texture loginScreen;
    Texture loginButton;
    Texture registerButton;


    public LoginScreen(Viewport viewport, Skin skin) {
        this.viewport = viewport;
        this.skin = skin;
        this.WIDTH = viewport.getScreenWidth();
        this.HEIGHT = viewport.getScreenHeight();

        stage = new Stage(viewport);

        background = new Texture(Gdx.files.internal("screen.png"));
        loginScreen = new Texture(Gdx.files.internal("screen-login.png"));
        loginButton = new Texture(Gdx.files.internal("btn-login.png"));
        registerButton = new Texture(Gdx.files.internal("btn-register.png"));
    }

    public void doLogin(){
        GeometryWars main = GeometryWars.getInstance();
        main.setScreen(main.getMenuScreen());
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        float color = 230 / 255f;
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(color, color, color, 1);
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, WIDTH, HEIGHT);
        stage.getBatch().draw(loginScreen,WIDTH/2-WIDTH/4,HEIGHT/2-WIDTH/8+50,WIDTH/2,WIDTH/4);
        stage.getBatch().draw(loginButton,WIDTH/2+25,HEIGHT/2-WIDTH/8+70, WIDTH/4-50,WIDTH/20);
        if( WIDTH/2+25 < Gdx.input.getX() && Gdx.input.getX() < WIDTH/2+25 + WIDTH/4-50
                && HEIGHT/2-WIDTH/8+70 < HEIGHT - Gdx.input.getY() && HEIGHT - Gdx.input.getY() < HEIGHT/2-WIDTH/8+70 + WIDTH/20){
            if(Gdx.input.isTouched()){
                doLogin();
            }
        }

        stage.getBatch().draw(registerButton,WIDTH/2-25-(WIDTH/4-50),HEIGHT/2-WIDTH/8+70, WIDTH/4-50,WIDTH/20);
        stage.getBatch().end();

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
