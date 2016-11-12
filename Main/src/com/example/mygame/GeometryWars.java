package com.example.mygame;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class GeometryWars extends Game {
    static int WIDTH; //800px
    static int HEIGHT; //480px
    private Skin skin;

    private Viewport viewport;
    private OrthographicCamera camera;
    private GameScreen gameScreen;
    private MenuScreen menuScreen;

    private static GeometryWars instance = new GeometryWars();

    public static GeometryWars getInstance() {
        return instance;
    }

    private GeometryWars() {

    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    @Override
    public void create(){
        skin = new Skin();
        BitmapFont bfont = new BitmapFont();
        skin.add("default", bfont);

        Pixmap pixmap = new Pixmap(100, 100, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.DARK_GRAY);
        pixmap.fill();

        skin.add("white", new Texture(pixmap));

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("white", Color.LIGHT_GRAY);
        textButtonStyle.down =  skin.newDrawable("white", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("white", Color.BLUE);
        //textButtonStyle.checked = skin.newDrawable("white", Color.BLACK);
        textButtonStyle.font = skin.getFont("default");

        skin.add("default", textButtonStyle);

        WIDTH = 1200;
        HEIGHT = 650;

        camera = new OrthographicCamera(WIDTH,HEIGHT);   //set Camera to the gamesize
        //camera.translate(WIDTH/2, HEIGHT/2);                                //Change the position of the camera (By default the origin is centered)
        camera.update(); //Update camera to new location
        viewport = new ScreenViewport(camera);

        gameScreen = new GameScreen(viewport, skin);
        menuScreen = new MenuScreen(viewport, skin);
        setScreen(menuScreen);
    }

    public void render(){
        //clear screen to black
        float color = 230 / 255f;
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(color, color, color, 1);
        getScreen().render(Gdx.graphics.getDeltaTime());
    }

    // what to do when screen is resized
    public void resize(int width, int height) {
        viewport.update(width, height);
        camera.update();
        WIDTH = viewport.getScreenWidth();
        HEIGHT = viewport.getScreenHeight();
    }
}
