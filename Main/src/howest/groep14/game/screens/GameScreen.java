package howest.groep14.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import howest.groep14.game.*;
import howest.groep14.game.actor.DroneActor;
import howest.groep14.game.actor.PlayerActor;
import howest.groep14.game.actor.ProjectileActor;
import howest.groep14.game.actor.attack.SnipeAttack;
import howest.groep14.game.actor.attack.SnipeEnemiesAttack;
import howest.groep14.game.actor.collision.DamageEnemyActor;
import howest.groep14.game.actor.health.Shield;
import howest.groep14.game.actor.health.StandardHealth;
import howest.groep14.game.actor.movement.StayAroundActor;
import howest.groep14.game.player.GameMapper;
import howest.groep14.game.player.Player;
import howest.groep14.game.player.PlayerRepository;

import java.sql.SQLException;

public class GameScreen implements Screen {
    private GameStage stage;
    private Label score1Label, score2Label, centerLabel, centerTopLabel, debugLabel;
    private TextButton toMenuButton, restartButton;
    private Skin skin;

    // State variables
    private boolean paused = false;
    private boolean gameOver = false;
    private float lastDelta = 0.2f;
    private float messageDelta, messageDuration;
    private boolean secondPlayer = false;

    private boolean playerHealthEnabled = true;

    public GameScreen(Viewport viewport, Skin skin, Player player) {
        stage = new GameStage(viewport);
        this.skin = skin;

        Sprite projectileSprite = SpriteRepository.getBlueProjectile();
        projectileSprite.setScale(0.15f * SettingsRepository.getInstance().getActorScale());
        PlayerActor playerActor = new PlayerActor(stage, SpriteRepository.getArrow(), player, projectileSprite);
        playerActor.setScale(0.3f * SettingsRepository.getInstance().getActorScale());
        playerActor.setPosition(CustomUtils.getCenterCoordinates(playerActor, stage));
        stage.addPlayer(playerActor);

        DroneActor droneActor = new DroneActor(stage, SpriteRepository.getAttackDrone(), playerActor);
        droneActor.setMovementBehavior(new StayAroundActor(droneActor, playerActor, 25, 50, 3));
        droneActor.setCollisionBehavior(new DamageEnemyActor(droneActor, 1, 0));
        projectileSprite = new Sprite(projectileSprite);
        projectileSprite.setScale(0.075f * SettingsRepository.getInstance().getActorScale());
        droneActor.setAttackBehavior(new SnipeEnemiesAttack(droneActor, playerActor, 0.1f, projectileSprite));
        droneActor.setScale(0.3f * SettingsRepository.getInstance().getActorScale());
        playerActor.setDrone(droneActor);

        /*
        Texture text = new Texture("Desktop/Assets/bck_rectangle.png");
        Sprite sprite  = new Sprite(text);
        //actor.setPosition(stage.getWidth() / 2 - center.getWidth() / 2, stage.getHeight() / 2 - center.getHeight() / 2);
        final int xAmount = 50;
        final int yAmount = 25;
        final float scaleX = stage.getWidth() / (sprite.getWidth() * xAmount);
        final float scaleY = stage.getHeight() / (sprite.getHeight() * yAmount);
        sprite.setScale(scaleX, scaleY);
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 25; j++) {
                SpriteActor actor = new SpriteActor(stage, new Sprite(sprite));
                actor.setPosition(i * sprite.getWidth() * sprite.getScaleX(), j * sprite.getHeight() * sprite.getScaleY());
                stage.addActor(actor);
            }
        }
        */

        createUI();
    }

    // Create UI elements
    private void createUI() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        score1Label = new Label("TEST", skin);
        score1Label.setPosition(10, screenHeight - 10, Align.topLeft);
        stage.addActor(score1Label);

        score2Label = new Label("PRESS INSERT TO JOIN", skin);
        score2Label.setPosition(screenWidth - 10, screenHeight - 10, Align.topRight);
        score2Label.setAlignment(Align.right, Align.right);
        stage.addActor(score2Label);

        centerLabel = new Label("", skin);
        centerLabel.setVisible(false);
        centerLabel.setPosition(screenWidth / 2, screenHeight / 2, Align.center);
        stage.addActor(centerLabel);

        centerTopLabel = new Label("", skin);
        centerTopLabel.setVisible(false);
        centerTopLabel.setPosition(screenWidth / 2, screenHeight - 10, Align.top);
        stage.addActor(centerTopLabel);

        debugLabel = new Label("", skin);
        debugLabel.setVisible(false);
        debugLabel.setPosition(25, 25, Align.bottomLeft);
        stage.addActor(debugLabel);
        initDebugLabel();

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
                main.newGame(stage.getPlayer().getPlayer());
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

    public Label getCenterTopLabel() {
        return centerTopLabel;
    }

    public void setCenterTopLabel(String message, float duration) {
        centerTopLabel.setText(message);
        centerTopLabel.setVisible(true);
        this.messageDuration = duration;
    }

    private void updateCenterTopLabel(float delta) {
        if (messageDelta >= messageDuration) {
            centerTopLabel.setText("");
            centerTopLabel.setVisible(false);
            messageDelta = 0;
        } else {
            messageDelta += delta;
        }
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
        try {
            GameMapper mapper = GameMapper.getInstance();
            for (PlayerActor playerActor : stage.getPlayers()) {
                try {
                    if (PlayerRepository.getInstance().getActivePlayer() != null && playerActor.getPlayer() != null) {
                        int id = mapper.addGame(1, 1, "empty");
                        mapper.addHighscore(id, playerActor.getPlayer().getShips().get(0).getID(), playerActor.getScore());
                    }
                } catch (SQLException ex) {
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean isPlayerHealthEnabled() {
        return playerHealthEnabled;
    }

    private void setPlayerHealthEnabled(boolean playerHealthEnabled) {
        this.playerHealthEnabled = playerHealthEnabled;
        if (playerHealthEnabled) {
            for (PlayerActor player : stage.getPlayers()) {
                if (player.getHealthBehavior() instanceof Shield) {
                    ((Shield) player.getHealthBehavior()).remove();
                }
                player.setHealthBehavior(new StandardHealth(player, 3));
            }
        } else {
            for (PlayerActor player : stage.getPlayers()) {
                player.setHealthBehavior(new Shield(player, SpriteRepository.getShield()));
            }
        }
        initDebugLabel();
    }

    private void initDebugLabel() {
        if (!playerHealthEnabled) {
            debugLabel.setText("HEALTH ENABLED (C to disable)");
            debugLabel.setVisible(true);
        } else {
            debugLabel.setText("HEALTH DISABLED (C to enable)");
        }
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
            if (lastDelta > 0.1f) {
                setPlayerHealthEnabled(!playerHealthEnabled);
                lastDelta = 0;
            } else {
                lastDelta += delta;
            }
        }
        if (Gdx.input.isKeyPressed(Input.Keys.INSERT) && !secondPlayer) {
            spawnSecondPlayer();
        }

        if (!paused) {
            updateCenterTopLabel(delta);
            score1Label.setText(stage.getPlayers().get(0).getScore() + " POINTS\n" +
                    stage.getPlayers().get(0).getHealthBehavior().toString());
            if (secondPlayer) {
                score2Label.setText(stage.getPlayers().get(1).getScore() + " POINTS\n" +
                    stage.getPlayers().get(0).getHealthBehavior().toString());
            }
            //score1Label.setText(Float.toString(stage.getPlayers().get(0).getRotation()));
            /*
            String string = "";
            for (EnemyActor enemy : stage.getEnemies()) {
                string += enemy.getPosition().toString() + "\n";
            }
            score1Label.setText(string);
            */
            stage.act(delta);
        }
        stage.draw();
    }

    private void spawnSecondPlayer() {
        Sprite projectileSprite = SpriteRepository.getRedProjectile();
        projectileSprite.setScale(0.15f * SettingsRepository.getInstance().getActorScale());
        PlayerActor playerActor = new PlayerActor(stage, SpriteRepository.getArrow(), null, projectileSprite);
        playerActor.setScale(0.3f * SettingsRepository.getInstance().getActorScale());
        playerActor.setPosition(CustomUtils.getCenterCoordinates(playerActor, stage));

        PlayerActor.ControlScheme scheme = playerActor.getControlScheme();
        scheme.FOLLOW_MOUSE = false;
        scheme.ROTATE_RIGHT = Input.Keys.NUMPAD_9;
        scheme.ROTATE_LEFT = Input.Keys.NUMPAD_7;
        scheme.UP = Input.Keys.NUMPAD_8;
        scheme.DOWN = Input.Keys.NUMPAD_5;
        scheme.LEFT = Input.Keys.NUMPAD_4;
        scheme.RIGHT = Input.Keys.NUMPAD_6;
        scheme.FIRE = Input.Keys.ENTER;

        if (!playerHealthEnabled) {
            playerActor.setHealthBehavior(new Shield(playerActor, null));
        }

        stage.addPlayer(playerActor);

        DroneActor droneActor = new DroneActor(stage, SpriteRepository.getAttackDrone(), playerActor);
        droneActor.setMovementBehavior(new StayAroundActor(droneActor, playerActor, 25, 50, 3));
        droneActor.setCollisionBehavior(new DamageEnemyActor(droneActor, 1, 0));
        projectileSprite = new Sprite(projectileSprite);
        projectileSprite.setScale(0.075f * SettingsRepository.getInstance().getActorScale());
        droneActor.setAttackBehavior(new SnipeEnemiesAttack(droneActor, playerActor, 0.1f, projectileSprite));
        droneActor.setScale(0.3f * SettingsRepository.getInstance().getActorScale());
        playerActor.setDrone(droneActor);
        secondPlayer = true;
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
        centerLabel.setVisible(false);
    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
