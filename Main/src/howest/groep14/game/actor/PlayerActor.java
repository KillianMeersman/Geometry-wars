package howest.groep14.game.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import howest.groep14.game.CustomUtils;
import howest.groep14.game.GameStage;
import howest.groep14.game.GeometryWars;
import howest.groep14.game.actor.drone.DroneActor;
import howest.groep14.game.actor.enemy.EnemyActor;
import howest.groep14.game.actor.projectile.ExplodeDestroyEnemies;
import howest.groep14.game.actor.projectile.INotifyProjectileEvents;
import howest.groep14.game.actor.projectile.ProjectileActor;

public class PlayerActor extends SpriteActor implements INotifyProjectileEvents {
    // Constants
    private final float MAX_SPEED = 10;
    private final float ACCEL = 1f;
    private final float ROT_SPEED = 5;
    private final float FRICTION = 0.92f;
    private int ROUNDS_PER_SECOND = 15;

    // Class vars
    private float speed_x = 0;
    private float speed_y = 0;
    private float lastDelta = 0;
    private int score, kills, projectilesFired;
    private int lives;
    private ControlScheme controlScheme;
    private DroneActor drone;

    public PlayerActor(GameStage stage, Sprite sprite, int lives) {
        super(stage, sprite);
        this.lives = lives;
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight());

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

    public PlayerActor(GameStage stage, Sprite sprite, int lives, ControlScheme controlScheme) {
        this(stage, sprite, lives);
        this.controlScheme = controlScheme;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        updatePositionAbsolute(speed_x *= FRICTION, speed_y *= FRICTION, true);
        checkInput();
        if (stage.isCollisionsEnabled()) {
            checkCollisions();
        }
    }

    private void checkInput() { // check for pressed keys
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

        if (Gdx.input.isButtonPressed(controlScheme.FIRE) || Gdx.input.isButtonPressed(controlScheme.FIRE)) {
            fireProjectile();
        }
        if (Gdx.input.isButtonPressed(controlScheme.POWERUP) || Gdx.input.isButtonPressed(controlScheme.POWERUP)) {
            fireExplodingProjectile();
        }
    }

    private void checkCollisions() {
        for (SpriteActor enemy : ((GameStage) getStage()).getCubeEnemies()) {
            if (CustomUtils.isColliding(this, enemy)) {
                GeometryWars.getInstance().getGameScreen().gameOver();
            }
        }
    }

    private void fireProjectile() {
        if (lastDelta > 1f / ROUNDS_PER_SECOND) { // check if not over rate of fire (delta is the time since last frame)
            Texture texture = new Texture("Desktop/Assets/greyProjectile.png");
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            Sprite projectileSprite = new Sprite(texture);

            ProjectileActor projectile = new ProjectileActor(stage, projectileSprite, getX(), getY(), getRotation(), this, this);
            projectile.setScale(0.1f);
            stage.addProjectile(projectile);

            projectilesFired++;
            lastDelta = 0; // reset lastDelta

            Sound shootSound = Gdx.audio.newSound(Gdx.files.internal("Desktop/Assets/laser.mp3"));
            shootSound.play(0.1f);
        } else {
            lastDelta += Gdx.graphics.getDeltaTime(); // add time since last frame to lastDelta)
        }
    }

    private void fireExplodingProjectile() {
        if (lastDelta > 1f / ROUNDS_PER_SECOND) { // check if not over rate of fire (delta is the time since last frame)
            Texture texture = new Texture("Desktop/Assets/greyProjectile.png");
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            Sprite projectileSprite = new Sprite(texture);

            ProjectileActor projectile = new ProjectileActor(stage, projectileSprite, getX(), getY(), getRotation(), this, this);
            projectile.setCollisionBehavior(new ExplodeDestroyEnemies(projectile, 1));
            projectile.setScale(0.3f);
            stage.addProjectile(projectile);

            projectilesFired++;
            lastDelta = 0; // reset lastDelta

            Sound shootSound = Gdx.audio.newSound(Gdx.files.internal("Desktop/Assets/laser.mp3"));
            shootSound.play(0.1f);
        } else {
            lastDelta += Gdx.graphics.getDeltaTime(); // add time since last frame to lastDelta)
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

    private void updateKills(int update) {
        kills += update;
    }

    public int getProjectilesFired() {
        return projectilesFired;
    }

    public ControlScheme getControlScheme() {
        return controlScheme;
    }

    public void damage(int damage) {
        this.lives -= damage;
        if (lives < 1) {
            stage.removePlayer(this);
        }
    }

    @Override
    public void projectileHit(ProjectileActor projectileActor) {
        updateKills(1);
    }

    @Override
    public void projectileOutOfBounds(ProjectileActor projectileActor) {

    }

    public DroneActor getDrone() {
        return drone;
    }

    public void setDrone(DroneActor drone) {
        this.drone = drone;
        if (drone != null) {
            drone.remove();
        }
        stage.addActor(drone);
    }

    class ControlScheme {
        int UP, DOWN, LEFT, RIGHT, ROTATE_LEFT, ROTATE_RIGHT, FIRE, POWERUP;
        boolean FOLLOW_MOUSE = true;

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
