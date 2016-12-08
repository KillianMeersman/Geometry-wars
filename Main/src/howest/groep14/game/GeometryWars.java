package howest.groep14.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.*;
import howest.groep14.game.player.PlayerRepository;
import howest.groep14.game.screens.GameScreen;
import howest.groep14.game.screens.MenuScreen;
import howest.groep14.game.screens.SettingScreen;

import java.sql.SQLException;

public class GeometryWars extends Game {
    private Skin skin;

    private Viewport viewport;
    private GameScreen gameScreen;
    private MenuScreen menuScreen;
    private SettingScreen settingScreen;

    private PlayerRepository playerRepository;

    private boolean dbConnection = true;

    private static GeometryWars instance = new GeometryWars();

    public static GeometryWars getInstance() {
        return instance;
    }

    private GeometryWars() {
        try {
            PlayerRepository.init();
            playerRepository = PlayerRepository.getInstance();
        } catch (Exception e) {
            dbConnection = false;
        }
        /*
        try {
            playerRepository.createPlayer("waddup", "", "testing");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        */
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

    public Viewport getViewPort() {
        return viewport;
    }

    public void newGame() {
        gameScreen = new GameScreen(viewport, skin);
        setScreen(gameScreen);
    }

    @Override
    public void create(){
        skin = generateSkin();

        //camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());   //set Camera to the gamesize
        //camera.translate(WIDTH/2, HEIGHT/2);           //Change the position of the camera (By default the origin is centered)
        //camera.update(); //Update camera to new location
        /*
        Gdx.graphics.setDisplayMode(
                Gdx.graphics.getDesktopDisplayMode().width,
                Gdx.graphics.getDesktopDisplayMode().height,
                true);
                */
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

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
        skin.add("solid", new Texture(pixmap));

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = bfont;
        textFieldStyle.fontColor = Color.WHITE;
        textFieldStyle.background = skin.newDrawable("solid", Color.GRAY);
        skin.add("default", textFieldStyle);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("solid", Color.LIGHT_GRAY);
        textButtonStyle.down =  skin.newDrawable("solid", Color.DARK_GRAY);
        textButtonStyle.over = skin.newDrawable("solid", Color.BLUE);
        //textButtonStyle.checked = skin.newDrawable("white", Color.BLACK);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        Label.LabelStyle labelStyle = new Label.LabelStyle(skin.getFont("default"), Color.DARK_GRAY);
        skin.add("default", labelStyle);

        return skin;
    }

    public void render(){
        //clear screens to black
        float color = 230 / 255f;
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(color, color, color, 1);
        getScreen().render(Gdx.graphics.getDeltaTime());
    }

    // what to do when screens is resized
    public void resize(int width, int height) {
        viewport.update(width, height);
        //camera.update();
        //getScreen().resize(width, height);
    }

    public PlayerRepository getPlayerRepository() {
        return playerRepository;
    }

    public boolean connectionReady() {
        return dbConnection;
    }
}
