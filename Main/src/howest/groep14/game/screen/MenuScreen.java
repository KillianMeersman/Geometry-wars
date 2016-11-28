package howest.groep14.game.screen;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;
import howest.groep14.game.CustomUtils;
import howest.groep14.game.GeometryWars;

public class MenuScreen implements Screen {
    private TextButton[] buttons = new TextButton[5];
    private final Stage stage;

    public MenuScreen(Viewport viewport, Skin skin) {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float buttonWidth = screenWidth / 2;

        stage = new Stage(viewport);

        TextButton campaignButton = CustomUtils.generateTextButton(skin, "C A M P A I G N (not available)", screenWidth / 4, screenHeight - 125, buttonWidth, 75);
        stage.addActor(campaignButton);

        TextButton skirmishButton = CustomUtils.generateTextButton(skin, "S K I R M I S H", screenWidth / 4, screenHeight - 215, buttonWidth, 75);
        skirmishButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GeometryWars main = GeometryWars.getInstance();
                main.setScreen(main.getGameScreen());
        }
        });
        stage.addActor(skirmishButton);

        TextButton multiPlayer = CustomUtils.generateTextButton(skin, "M U L T I P L A Y E R (not available)", screenWidth / 4, screenHeight - 305, buttonWidth, 75);
        stage.addActor(multiPlayer);

        TextButton quitButton = CustomUtils.generateTextButton(skin, "Q U I T", screenWidth / 4, screenHeight - 395, buttonWidth / 2 - 15, 75);
        stage.addActor(quitButton);
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        TextButton settingsButton = CustomUtils.generateTextButton(skin, "S E T T I N G S", screenWidth / 2 + 15, screenHeight - 395, buttonWidth / 2 - 15, 75);

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
