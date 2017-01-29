package howest.groep14.game;


import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.*;
import howest.groep14.game.player.Player;
import howest.groep14.game.player.PlayerRepository;
import howest.groep14.game.screens.*;

public class GeometryWars extends Game {
    private Skin skin;
    private Skin transparantSkin;

    private Viewport viewport;
    private GameScreen gameScreen;
    private MenuScreen menuScreen;
    private MenuScreenOld menuScreenOld;
    private LoginScreen loginScreen;
    private RegisterScreenOld registerScreenOld;
    private SettingScreenOld settingScreenOld;
    private PlayerRepository playerRepository;

    private boolean dbConnection = true;

    private static GeometryWars instance = new GeometryWars();

    public static GeometryWars getInstance() {
        return instance;
    }

    private GeometryWars() {}

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public MenuScreen getMenuScreen() {
        return menuScreen;
    }

    public MenuScreenOld getMenuScreenOld() {return menuScreenOld;}

    public SettingScreenOld getSettingScreenOld() {
        return settingScreenOld;
    }

    public LoginScreen getLoginScreen(){return loginScreen;}

    public Viewport getViewPort() {
        return viewport;
    }

    public void newGame(Player player) {
        gameScreen = new GameScreen(viewport, skin, player);
        setScreen(gameScreen);
    }

    @Override
    public void create(){
        SpriteRepository.init();
        try {
            PlayerRepository.init();
        } catch (Exception e) {
            dbConnection= false;
        }
        skin = generateSkin();
        transparantSkin = generateTransSkin();

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

        //gameScreen.setPlayerHealthEnabled(false);
        menuScreen = new MenuScreen(viewport, skin);
        settingScreenOld = new SettingScreenOld(viewport, skin);
        loginScreen = new LoginScreen(viewport,transparantSkin);
        registerScreenOld = new RegisterScreenOld(viewport, skin);
        menuScreenOld = new MenuScreenOld(viewport,skin);
        setScreen(loginScreen);
    }

    private Skin generateTransSkin() {
        Skin skin = new Skin();

        BitmapFont bfont = new BitmapFont();
        bfont.getData().setScale(2);
        skin.add("default", bfont);

        Pixmap pixmap = new Pixmap(100, 100, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.DARK_GRAY);
        pixmap.fill();
        skin.add("solid", new Texture(pixmap));

        TextField.TextFieldStyle textFieldStyle = new TextField.TextFieldStyle();
        textFieldStyle.font = bfont;
        textFieldStyle.fontColor = Color.DARK_GRAY;
        textFieldStyle.background = skin.newDrawable("solid", Color.CLEAR);
        skin.add("default", textFieldStyle);

        Window.WindowStyle windowStyle = new Window.WindowStyle();
        windowStyle.titleFont= skin.getFont("default");
        windowStyle.background = skin.newDrawable("solid", Color.DARK_GRAY);
        skin.add("default", windowStyle);

        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.up = skin.newDrawable("solid", Color.CLEAR);
        textButtonStyle.down =  skin.newDrawable("solid", Color.CLEAR);
        textButtonStyle.over = skin.newDrawable("solid", Color.CLEAR);
        textButtonStyle.font = skin.getFont("default");
        skin.add("default", textButtonStyle);

        Label.LabelStyle labelStyle = new Label.LabelStyle(skin.getFont("default"), Color.DARK_GRAY);
        skin.add("default", labelStyle);

        return skin;
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
        textFieldStyle.fontColor = Color.DARK_GRAY;
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

    public RegisterScreenOld getRegisterScreenOld() {
        return registerScreenOld;
    }
}
