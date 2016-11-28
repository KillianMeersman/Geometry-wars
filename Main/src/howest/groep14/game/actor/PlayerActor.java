package howest.groep14.game.actor;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import howest.groep14.game.CustomUtils;
import howest.groep14.game.GameStage;
import howest.groep14.game.GeometryWars;
import howest.groep14.game.actor.enemy.EnemyActor;
import howest.groep14.game.actor.projectile.INotifyProjectileEvents;
import howest.groep14.game.actor.projectile.ProjectileActor;

public class PlayerActor extends SpriteActor implements INotifyProjectileEvents {
    // Constants
    private int ROUNDS_PER_SECOND = 15;
    private final float MAX_SPEED = 10;
    private final float ACCEL = 1f;
    private final float ROT_SPEED = 5;
    private final float FRICTION = 0.92f;

    // Class vars
    private float speed_x = 0;
    private float speed_y = 0;
    private float lastDelta = 0;
    private int projectilesFired = 0;
    private int score = 0;
    private ControlScheme controlScheme;

    public PlayerActor(GameStage stage){
        super(stage);
        Texture texture = new Texture("Desktop/Assets/player.png");
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sprite = new Sprite(texture);

        setBounds(new Rectangle(sprite.getX(), sprite.getY(), sprite.getWidth(), sprite.getHeight()));

        controlScheme = new ControlScheme();
        controlScheme.UP = Input.Keys.UP;
        controlScheme.DOWN = Input.Keys.DOWN;
        controlScheme.LEFT = Input.Keys.LEFT;
        controlScheme.RIGHT = Input.Keys.RIGHT;
        controlScheme.FIRE = Input.Keys.SPACE;
    }

    public PlayerActor(GameStage stage, ControlScheme controlScheme) {
        this(stage);
        this.controlScheme = controlScheme;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        updatePositionAbsolute(speed_x *= FRICTION, speed_y *= FRICTION);
        checkInput();
        if (gameStage.isCollisionsEnabled()) { checkCollisions(); }
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

        if (Gdx.input.isKeyPressed(controlScheme.FIRE)) {
            fireProjectile();
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            fireProjectile();
        }
        faceMouse();
    }

    private void checkCollisions() {
        for (EnemyActor enemy : ((GameStage) getStage()).getEnemyActors()) {
            if (enemy.getBounds().overlaps(getBounds())) {
                GeometryWars.getInstance().getGameScreen().gameOver();
            }
        }
    }

    private void fireProjectile() {
        if (lastDelta > 1f / ROUNDS_PER_SECOND) { // check if not over rate of fire (delta is the time since last frame)
            ProjectileActor projectile = new ProjectileActor(gameStage, sprite.getX(), sprite.getY(), CustomUtils.getAngleToMouse(getX(), getY()), this, this);
            gameStage.addProjectile(projectile);
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

    public int getProjectilesFired() {
        return projectilesFired;
    }

    public ControlScheme getControlScheme() {
        return controlScheme;
    }

    @Override
    public void projectileHit(ProjectileActor projectileActor) {
        updateScore(1);
    }

    @Override
    public void projectileOutOfBounds(ProjectileActor projectileActor) {

    }

    class ControlScheme {
        int UP, DOWN, LEFT, RIGHT, FIRE;

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