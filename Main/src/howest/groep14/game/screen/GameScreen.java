package howest.groep14.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import howest.groep14.game.CustomUtils;
import howest.groep14.game.GameStage;
import howest.groep14.game.GeometryWars;
import howest.groep14.game.actor.PlayerActor;

public class GameScreen implements Screen {
    private GameStage stage;
    private Label score1Label, score2Label, centerLabel, debugLabel;
    private TextButton toMenuButton, restartButton;
    private Skin skin;

    // State variables
    private boolean paused = false;
    private boolean gameOver = false;
    private float lastDelta = 0.2f;

    public GameScreen(Viewport viewport, Skin skin) {
        stage = new GameStage(viewport);
        this.skin = skin;
        PlayerActor player = new PlayerActor(stage);
        player.setPosition(50, 50);
        stage.addPlayer(player);
        stage.setKeyboardFocus(player);
        createUI();
    }

    // Create UI elements
    public void createUI() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        score1Label = new Label("TEST", skin);
        score1Label.setPosition(10, screenHeight - 10, Align.topLeft);
        stage.addActor(score1Label);

        score2Label = new Label("NO SECOND PLAYER", skin);
        score2Label.setPosition(screenWidth - 10, screenHeight - 10, Align.topRight);
        stage.addActor(score2Label);

        centerLabel = new Label("", skin);
        centerLabel.setVisible(false);
        centerLabel.setPosition(screenWidth / 2, screenHeight / 2, Align.center);
        stage.addActor(centerLabel);

        debugLabel = new Label("", skin);
        debugLabel.setVisible(false);
        debugLabel.setPosition(25, 25, Align.bottomLeft);
        stage.addActor(debugLabel);

        toMenuButton = CustomUtils.generateTextButton(skin, "T O  M E N U", screenWidth / 2 - 25, screenHeight - 75, 150, 50);
        toMenuButton.setVisible(false);
        toMenuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GeometryWars main = GeometryWars.getInstance();
                main.setScreen(main.getMenuScreen());
            }
        });
        stage.addActor(toMenuButton);

        restartButton = CustomUtils.generateTextButton(skin, "R E S T A R T", screenWidth / 2 - 25, screenHeight - 135, 150, 50);
        restartButton.setVisible(false);
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                GeometryWars main = GeometryWars.getInstance();
                main.newGame();
            }
        });
        stage.addActor(restartButton);
    }

    public Label getScore1Label() {
        return score1Label;
    }

    public Label getScore2Label() {
        return score2Label;
    }

    public Label getCenterLabel() {
        return centerLabel;
    }

    public Label getDebugLabel() {
        return debugLabel;
    }

    public GameStage getStage() {
        return stage;
    }

    public void gameOver() {
        pause();
        gameOver = true;
        for (Actor actor : stage.getActors()) {
            actor.setVisible(false);
        }
        centerLabel.setText("GAME OVER");
        centerLabel.setVisible(true);
        toMenuButton.setVisible(true);
        restartButton.setVisible(true);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) && !gameOver) {
            if (lastDelta > 0.1f) {
                if (paused) {
                    resume();
                } else {
                    pause();
                }
                lastDelta = 0;
            } else {
                lastDelta += delta;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.C)) {
            stage.setCollisionsEnabled(true);
        }

        if (!paused) {
            score1Label.setText(stage.getPlayers().get(0).getScore() + " KILLS");
            stage.act(delta);
        }
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        //GeometryWars.getInstance().getViewPort().setWorldSize(width, height);
    }

    @Override
    public void pause() {
        paused  = true;
        toMenuButton.setVisible(true);
        restartButton.setVisible(true);
        centerLabel.setText("PAUSED");
        centerLabel.setVisible(true);
    }

    @Override
    public void resume() {
        paused = false;
        toMenuButton.setVisible(false);
        restartButton.setVisible(false);
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
