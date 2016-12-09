package howest.groep14.game.actor.projectile;

import com.badlogic.gdx.graphics.g2d.Sprite;
import howest.groep14.game.CustomUtils;
import howest.groep14.game.GameStage;
import howest.groep14.game.actor.SpriteActor;

public class ProjectileActor extends SpriteActor {
    private float speed = 12;
    private float xAmount, yAmount;
    private int bounces;
    private final SpriteActor owner;
    private INotifyProjectileEvents observer;
    private IProjectileCollisionBehavior collisionBehavior;

    public ProjectileActor(GameStage stage, Sprite sprite, float x, float y, float rotation, SpriteActor owner) {
        super(stage, sprite);
        this.owner = owner;
        this.collisionBehavior = new DestroyEnemyCollisionBehavior(this);

        this.setPosition(x, y);
        this.setRotation(rotation);

        calcMovement(); // Because it moves in a straight line, calculate movement on x and y-axis per turn only once (saves cpu)
        if (CustomUtils.booleanRandom(10)) { // 1 in 10 change to bounce (once)
            bounces = 1;
        }
    }

    public ProjectileActor(GameStage stage, Sprite sprite, float x, float y, float rotation, SpriteActor owner, INotifyProjectileEvents observer) {
        this(stage, sprite, x, y, rotation, owner);
        this.observer = observer;
    }

    public void setCollisionBehavior(IProjectileCollisionBehavior collisionBehavior) {
        this.collisionBehavior = collisionBehavior;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
        calcMovement();
    }

    public void act(float delta) {
        super.act(delta);
        setPosition(getX() + xAmount, getY() + yAmount); // Go forward
        if (CustomUtils.outOfBoundsX(getX(), sprite.getWidth(), speed)) { bounce(); } // Check if at border of screens, of so: bounce or be removed
        if (CustomUtils.outOfBoundsY(getY(), sprite.getHeight(), speed)) { bounce(); }
        checkCollisions(delta);
    }

    private void checkCollisions(float delta) {
        if (collisionBehavior.checkCollisions(delta) && observer != null) {
            observer.projectileHit(this);
        }
    }

    public void bounce() {
        if (bounces > 0) {
            if (CustomUtils.booleanRandom()) {
                setRotation(getRotation() + 130); // Add or subtract 130 degrees from rotation
            } else {
                setRotation(getRotation() - 130);
            }
            bounces--;
            calcMovement(); // we have rotated, so we need to recalculate x and y movement per turn
        } else {
            if (observer != null) {
                observer.projectileOutOfBounds(this);
            }
            this.remove();
        }
    }

    private void calcMovement() {
        float radians = (float) Math.toRadians(getRotation());
        this.xAmount = (float) Math.cos(radians) * speed;
        this.yAmount = (float) Math.sin(radians) * speed;
        this.speed *= 0.5f;
    }
}
