package howest.groep14.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import howest.groep14.game.*;
import howest.groep14.game.actor.collision.CollectGeomesDamaged;
import howest.groep14.game.actor.health.StandardHealth;

public class PlayerActor extends SpriteActor implements IProjectileObserver, IGeomeCollector {
    // Constants
    private final float MAX_SPEED = 10;
    private final float ACCEL = 1f;
    private final float ROT_SPEED = 5;
    private final float FRICTION = 0.92f;
    private int ROUNDS_PER_SECOND = 15;
    private int PROJECTILE_SPEED = 15;

    // Class vars
    private float speed_x = 0;
    private float speed_y = 0;
    private float lastDelta = 0;
    private int score, kills, projectilesFired;
    private ControlScheme controlScheme;
    private DroneActor drone;

    public PlayerActor(GameStage stage, Sprite sprite) {
        super(stage, sprite);
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());

        this.collisionBehavior = new CollectGeomesDamaged(this, 0, 1, this);
        this.healthBehavior = new StandardHealth(this, 1);

        controlScheme = new ControlScheme();
        controlScheme.UP = Input.Keys.UP;
        controlScheme.DOWN = Input.Keys.DOWN;
        controlScheme.LEFT = Input.Keys.LEFT;
        controlScheme.RIGHT = Input.Keys.RIGHT;
        controlScheme.ROTATE_LEFT = Input.Keys.L;
        controlScheme.ROTATE_RIGHT = Input.Keys.M;
        controlScheme.FIRE = Input.Buttons.LEFT;
        controlScheme.POWERUP = Input.Buttons.RIGHT;
    }

    public PlayerActor(GameStage stage, Sprite sprite, ControlScheme controlScheme) {
        this(stage, sprite);
        this.controlScheme = controlScheme;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        updatePositionAbsolute(speed_x *= FRICTION, speed_y *= FRICTION, true);
        checkInput(delta);
    }

    private void checkInput(float delta) { // check for pressed keys
        if (Gdx.input.isKeyPressed(controlScheme.UP)) {
            speed_y = Math.min(speed_y + ACCEL, MAX_SPEED);
        }
        if (Gdx.input.isKeyPressed(controlScheme.DOWN)) {
            speed_y = Math.max(-MAX_SPEED, speed_y - ACCEL);
        }
        if (Gdx.input.isKeyPressed(controlScheme.LEFT)) {
            speed_x = Math.max(-MAX_SPEED, speed_x - ACCEL);
        }
        if (Gdx.input.isKeyPressed(controlScheme.RIGHT)) {
            speed_x = Math.min(speed_x + ACCEL, MAX_SPEED);
        }
        if (controlScheme.FOLLOW_MOUSE) {
            faceMouse();
        } else {
            if (Gdx.input.isKeyPressed(controlScheme.ROTATE_LEFT)) {
                updateRotation(ROT_SPEED);
            }
            if (Gdx.input.isKeyPressed(controlScheme.ROTATE_LEFT)) {
                updateRotation(-ROT_SPEED);
            }
        }

        if (Gdx.input.isKeyPressed(controlScheme.FIRE) || Gdx.input.isButtonPressed(controlScheme.FIRE)) {
            fireProjectile(delta);
        }
    }

    private void fireProjectile(float delta) {
        if (lastDelta > 1f / ROUNDS_PER_SECOND) { // check if not over rate of fire (delta is the time since last frame)
            ProjectileActor projectile = new ProjectileActor(stage, SpriteRepository.getProjectile(), this, this.getRotation());
            projectile.setScale(0.1f * SettingsRepository.getActorScale());
            stage.addProjectile(projectile);

            projectilesFired++;
            lastDelta = 0; // reset lastDelta


            Sound shootSound = Gdx.audio.newSound(Gdx.files.internal("Desktop/Assets/laser.mp3"));
            shootSound.play(0.06f);

        } else {
            lastDelta += delta; // add time since last frame to lastDelta)
        }
    }

    private void faceMouse() {
        setRotation(CustomUtils.getAngleToMouse(getX(), getY()));
    }

    public int getScore() {
        return score;
    }

    public void updateScore(int scoreUpdate) {
        score += scoreUpdate;

        Sound blipSound = Gdx.audio.newSound(Gdx.files.internal("Desktop/Assets/blip.mp3"));
        blipSound.play(0.5f);

    }

    public ControlScheme getControlScheme() {
        return controlScheme;
    }

    public void setDrone(DroneActor drone) {
        this.drone = drone;
        if (drone != null) {
            drone.remove();
        }
        stage.addActor(drone);
    }

    public int getRoundsPerSecond() {
        return ROUNDS_PER_SECOND;
    }

    public void setRoundsPerSecond(int roundsPerSecond) {
        this.ROUNDS_PER_SECOND = roundsPerSecond;
    }

    @Override
    public void projectileHit(ProjectileActor projectile, SpriteActor victim) {

    }

    @Override
    public void projectileOutOfBounds(ProjectileActor projectile) {

    }

    @Override
    public SpriteActor getOwner() {
        return this;
    }

    @Override
    public boolean remove() {
        if (!removed) {
            removed = true;
            stage.removePlayer(this);
            return super.remove();
        }
        return false;
    }

    public DroneActor getDrone() {
        return drone;
    }

    public class ControlScheme {
        public int UP, DOWN, LEFT, RIGHT, ROTATE_LEFT, ROTATE_RIGHT, FIRE, POWERUP;
        public boolean FOLLOW_MOUSE = true;

        /*
        public void setFORWARD(int key) {
            UP = key;
        }

        public int getForward() {
            return UP;
        }

        public void setBACKWARDS(int key) {
            DOWN = key;
        }

        public int getBACKWARDS() {
            return DOWN;
        }

        public void setLEFT(int key) {
            LEFT = key;
        }

        public int getLEFT() {
            return LEFT;
        }

        public void setRIGHT(int key) {
            RIGHT = key;
        }

        public int getRIGHT() {
            return RIGHT;
        }
        */
    }
}
