package howest.groep14.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import howest.groep14.game.actor.*;
import howest.groep14.game.actor.health.DeathObserver;
import howest.groep14.game.actor.health.IDeathObserver;
import howest.groep14.game.actor.health.Invulnerable;
import howest.groep14.game.actor.health.StandardHealthHide;
import howest.groep14.game.actor.movement.Bounce;
import howest.groep14.game.actor.movement.Kamikaze;
import howest.groep14.game.actor.movement.MovementBehavior;
import howest.groep14.game.actor.movement.Snake;
import howest.groep14.game.powers.*;

class SpawnManager extends Actor implements IDeathObserver {
    private final int SPAWN_PLAYER_MARGIN = 100;
    private final float GEOME_LIFETIME = 5f;

    // Change this to change difficulty
    private int CUBE_AMOUNT = 15;
    private int CIRCLE_AMOUNT = 5;

    private final int MAX_CIRCLE_AMOUNT = 6;
    private int SNAKE_AMOUNT = 0;

    private float CUBE_SPEED = 2f;
    private float CIRCLE_SPEED = 6f;

    private int destoyed_cubes, cube_upgrades, circle_upgrades, destroyed_circles;
    private boolean armored_enemies, changeTime, dualFire, extraLife, shield;


    private final GameStage stage;

    private int cube_amount = 0, circle_amount = 0, snake_amount = 0, geome_amount = 0;

    public SpawnManager(GameStage stage) {
        this.stage = stage;
    }

    @Override
    public void act(float delta) {
        if (cube_amount < CUBE_AMOUNT) {
            spawnCube();
            cube_amount++;
        }
        if (circle_amount < CIRCLE_AMOUNT) {
            spawnCircle();
            circle_amount++;
        }
        if (snake_amount < SNAKE_AMOUNT) {
            spawnSnake();
            snake_amount++;
        }
    }

    private void spawnCube() {
        EnemyActor enemyActor = new EnemyActor(stage, SpriteRepository.getCube(), EnemyActor.ENEMY_TYPE.CUBE);
        MovementBehavior kamikaze = new Kamikaze(enemyActor, stage.getPlayer(), CUBE_SPEED, false);
        enemyActor.setMovementBehavior(kamikaze);
        enemyActor.setScale(0.2f * SettingsRepository.getActorScale());
        Vector2 position = getEdgeSpawnCoordinates(-80);

        enemyActor.setPosition(position);
        enemyActor.setVisible(true);
        stage.addEnemy(enemyActor);
    }

    private void spawnCircle() {
        EnemyActor enemyActor = new EnemyActor(stage, SpriteRepository.getCircle(), EnemyActor.ENEMY_TYPE.CIRCLE);
        Vector2 position = getRandomSpawnCoordinates(SPAWN_PLAYER_MARGIN);
        enemyActor.setPosition(position);
        enemyActor.setVisible(true);
        enemyActor.setScale(0.2f * SettingsRepository.getActorScale());

        MovementBehavior mov = new Bounce(enemyActor, CIRCLE_SPEED, CustomUtils.intRandom(360));
        enemyActor.setMovementBehavior(mov);

        stage.addEnemy(enemyActor);
    }

    private void spawnSnake() {
        Sprite geomeSprite = new Sprite(SpriteRepository.getGeome());
        geomeSprite.setScale(0.2f);
        EnemyActor enemyActor = new EnemyActor(stage, geomeSprite, EnemyActor.ENEMY_TYPE.SNAKE);
        enemyActor.setPosition(150, 150);
        Snake mov = new Snake(enemyActor, 5, geomeSprite, 5);
        enemyActor.setMovementBehavior(mov);
        Invulnerable inv = new Invulnerable(enemyActor);
        enemyActor.setHealthBehavior(inv);
        stage.addEnemy(enemyActor);
    }

    private void spawnGeome(float x, float y) {
        GeomeActor geome = new GeomeActor(stage, SpriteRepository.getGeome(), 1, GEOME_LIFETIME);
        geome.setScale(0.1f);
        geome.setPosition(x, y);
        stage.addGeome(geome);
    }

    private void spawnPowerUp(float x, float y) {
        PowerBehavior pow;
        switch (CustomUtils.intRandom(6)) {
            case 1: pow = new ArmoredEnemies(10f);
                armored_enemies = true;
                break;
            case 2: pow = new ChangeTimeSpeed(5f, 1.2f);
                changeTime = true;
                break;
            case 3: pow = new Quadfire(5f);
                dualFire = true;
                break;
            case 4: pow = new ExtraLife();
                extraLife = true;
                break;
            case 5: //pow = new ShieldTarget(10f);
                //shield = true;    // broken
                //break;
            default: pow = new ChangeTimeSpeed(10f, 0.5f);
                changeTime = true;
        }
            GeomeActor geome = new PowerGeomeActor(stage, SpriteRepository.getGeome(), 1, 15, pow, PowerGeomeActor.POWER.DUAL_FIRE);
            geome.setHealthBehavior(new DeathObserver((StandardHealthHide)geome.getHealthBehavior(), this));
            geome.setScale(0.2f);
            geome.setPosition(x, y);
            stage.addGeome(geome);
    }

    public void removeEnemy(EnemyActor actor) {
        switch (actor.getTypeCode()) {
            case CUBE:
                cube_amount--;
                destoyed_cubes++;
                if (destoyed_cubes % (25 * (cube_upgrades + 1)) == 0) {
                    CUBE_AMOUNT++;
                    cube_upgrades++;
                    increaseRateOfFire(1);
                }
                break;
            case CIRCLE:
                circle_amount--;
                destroyed_circles++;
                if (destroyed_circles % (25 * (circle_upgrades + 1)) == 0) {
                    CIRCLE_AMOUNT = Math.min(CIRCLE_AMOUNT + 1, MAX_CIRCLE_AMOUNT);
                    circle_upgrades++;
                }
                break;
            case SNAKE:
                snake_amount--;
                break;
        }
        if (CustomUtils.booleanRandom(15)) {
            spawnPowerUp(actor.getX(), actor.getY());
        } else {
            spawnGeome(actor.getX(), actor.getY());
        }
    }

    public void removeGeome(GeomeActor actor) {
        if (actor instanceof PowerGeomeActor) {
            switch (((PowerGeomeActor) actor).getPowerFlag()) {
                case ARMORED_ENEMIES:
                    if (armored_enemies) {
                        armored_enemies = false;
                    }
                    break;
                case CHANGE_TIME:
                    if (changeTime) {
                        changeTime = false;
                    }
                    break;
                case DUAL_FIRE:
                    if (dualFire) {
                        dualFire = false;
                    }
                    break;
                case EXTRA_LIFE:
                    if (extraLife) {
                        extraLife = false;
                    }
                    break;
                case SHIELD:
                    if (shield) {
                        shield = false;
                    }
                    break;
            }
        }
    }

    private Vector2 getEdgeSpawnCoordinates(float edge_margin) {
        float x, y;
        if (CustomUtils.booleanRandom()) {
            y = CustomUtils.booleanRandom() ? stage.getHeight() - edge_margin : 0 + edge_margin;
            x = CustomUtils.floatRandom() * stage.getWidth() - edge_margin;
        } else {
            y = CustomUtils.floatRandom() * stage.getHeight() - edge_margin;
            x = CustomUtils.booleanRandom() ? 0 + edge_margin : stage.getWidth() - edge_margin;
        }

        return new Vector2(x, y);
    }

    private void increaseRateOfFire(int increase) {
        for (PlayerActor player : stage.getPlayers()) {
            player.setRoundsPerSecond(player.getRoundsPerSecond() + increase);
        }
    }

    private Vector2 getRandomSpawnCoordinates(float margin) {
        float x = 0;
        float y = 0;
        boolean xClear = false;
        boolean yClear = false;

        while (!xClear || !yClear) {
            xClear = true;
            yClear = true;
            x = CustomUtils.intRandom(Math.round(stage.getWidth() - 100));
            y = CustomUtils.intRandom(Math.round(stage.getHeight() - 100));

            for (PlayerActor player : stage.getPlayers()) {
                if (!(Math.abs(player.getX() - x) >= margin)) {
                    xClear = false;
                    break;
                }
                if (!(Math.abs(player.getY() - y) >= margin)) {
                    yClear = false;
                    break;
                }
            }
        }
        return new Vector2(x, y);
    }

    @Override
    public void actorDeath(SpriteActor actor) {
        GeometryWars.getInstance().getGameScreen().getCenterLabel().setVisible(true);
        GeometryWars.getInstance().getGameScreen().setCenterTopLabel(((PowerGeomeActor)actor).getPowerBehavior().toString(), 3);
    }
}
