package howest.groep14.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Sprite;
import howest.groep14.game.*;
import howest.groep14.game.actor.attack.FireInDirection;
import howest.groep14.game.actor.attack.MultipleFireInDirection;
import howest.groep14.game.actor.collision.CollectGeomesDamaged;
import howest.groep14.game.actor.health.StandardHealth;
import howest.groep14.game.player.Player;
import howest.groep14.game.powers.PowerBehavior;

import java.util.HashSet;
import java.util.Set;

public class PlayerActor extends SpriteActor implements IProjectileObserver, IGeomeCollector {
    // Constants
    private final float MAX_SPEED = 10;
    private final float ACCEL = 1f;
    private final float ROT_SPEED = 5;
    private final float FRICTION = 0.92f;

    // Class vars
    private float speed_x = 0;
    private float speed_y = 0;
    private int score, hits, projectilesFired;
    private ControlScheme controlScheme;
    private DroneActor drone;
    private Player player;
    private boolean enabled = true;
    private Set<PowerBehavior> powers = new HashSet<PowerBehavior>();

    public Set<PowerBehavior> getPowers() {
        return powers;
    }

    public PlayerActor(GameStage stage, Sprite sprite, Player player) {
        super(stage, sprite);
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());

        this.collisionBehavior = new CollectGeomesDamaged(this, 0, 1, this);
        this.healthBehavior = new StandardHealth(this, 3);
        this.attackBehavior = new FireInDirection(this);

        this.player = player;

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

    public PlayerActor(GameStage stage, Sprite sprite, Player player, ControlScheme controlScheme) {
        this(stage, sprite, player);
        this.controlScheme = controlScheme;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.movementBehavior.move(delta);
        this.collisionBehavior.checkCollisions(delta);

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
        if (Gdx.input.isKeyPressed(controlScheme.POWERUP) || Gdx.input.isButtonPressed(controlScheme.POWERUP)) {
            //fireProjectileBomb(delta);
        }
    }

    private void fireProjectile(float delta) {
        attackBehavior.engage(delta);
        projectilesFired++;
    }

    private void fireProjectileBomb(float delta) {
        if (this.attackBehavior instanceof FireInDirection) {
            FireInDirection original = (FireInDirection) this.attackBehavior;
            this.attackBehavior = new MultipleFireInDirection(this, 60, 50);
            this.attackBehavior.engage(delta);
            this.attackBehavior = original;
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

        Sound blipSound = Gdx.audio.newSound(Gdx.files.internal("blip.mp3"));
        blipSound.play(0.5f);

    }

    public int getHits() {
        return hits;
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
        return ((FireInDirection)attackBehavior).getROUNDS_PER_SECOND();
    }

    public void setRoundsPerSecond(int roundsPerSecond) {
        ((FireInDirection)attackBehavior).setROUNDS_PER_SECOND(roundsPerSecond);
    }

    @Override
    public void projectileHit(ProjectileActor projectile, SpriteActor victim) {
        hits++;
    }

    @Override
    public void projectileOutOfBounds(ProjectileActor projectile) {

    }

    @Override
    public void projectileDestroyed(ProjectileActor projectile) {

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

    public Player getPlayer() {
        return player;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public class ControlScheme {
        public int UP, DOWN, LEFT, RIGHT, ROTATE_LEFT, ROTATE_RIGHT, FIRE, POWERUP;
        public boolean FOLLOW_MOUSE = true;
    }
}
