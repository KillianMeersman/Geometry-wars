package howest.groep14.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.utils.viewport.*;
import howest.groep14.game.screen.GameScreen;
import howest.groep14.game.screen.MenuScreen;
import howest.groep14.game.screen.SettingScreen;

public class GeometryWars extends Game {
    static int WIDTH; //800px
    static int HEIGHT; //480px
    private Skin skin;

    private Viewport viewport;
    private OrthographicCamera camera;
    private GameScreen gameScreen;
    private MenuScreen menuScreen;
    private SettingScreen settingScreen;

    private static GeometryWars instance = new GeometryWars();

    public static GeometryWars getInstance() {
        return instance;
    }

    private GeometryWars() {

    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public MenuScreen getMenuScreen() {
        return menuScreen;
    }

    public SettingScreen getSettingScreen() {
        return settingScreen;
    }

    public void newGame() {
        gameScreen = new GameScreen(viewport, skin);
        setScreen(gameScreen);
    }

    public Camera getCamera() {
        return camera;
    }

    public Viewport getViewPort() {
        return viewport;
    }

    @Override
    public void create(){
        skin = generateSkin();

        WIDTH = 1200;
        HEIGHT = 650;

        camera = new OrthographicCamera(WIDTH,HEIGHT);   //set Camera to the gamesize
        //camera.translate(WIDTH/2, HEIGHT/2);           //Change the position of the camera (By default the origin is centered)
        camera.update(); //Update camera to new location
        //viewport = new ScreenViewport(camera);
        viewport = new ScreenViewport(camera);

        gameScreen = new GameScreen(viewport, skin);
        gameScreen.getStage().setCollisionsEnabled(false);
        menuScreen = new MenuScreen(viewport, skin);
        settingScreen = new SettingScreen(viewport, skin);
        setScreen(menuScreen);
    }

    private Skin generateSkin() {
        Skin skin = new Skin();

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

        Label.LabelStyle style = new Label.LabelStyle(skin.getFont("default"), Color.DARK_GRAY);
        skin.add("default", style);

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = bfont;
        skin.add("default", textFieldStyle);

        return skin;
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

    }
}
