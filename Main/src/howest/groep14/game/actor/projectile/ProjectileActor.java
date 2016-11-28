package howest.groep14.game.actor.projectile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import howest.groep14.game.CustomUtils;
import howest.groep14.game.GameStage;
import howest.groep14.game.actor.SpriteActor;

public class ProjectileActor extends SpriteActor {
    private float speed = 15;
    private float xAmount, yAmount;
    private int bounces;
    private final SpriteActor owner;
    private INotifyProjectileEvents observer;
    private IProjectileCollisionBehavior collisionBehavior;

    public ProjectileActor(GameStage stage, float x, float y, float rotation, SpriteActor owner) {
        super(stage);
        this.owner = owner;
        this.collisionBehavior = new DestroyEnemyCollisionBehavior(this);

        Texture texture = new Texture("Desktop/Assets/greyProjectile.png");
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sprite = new Sprite(texture);
        sprite.setRotation(rotation);
        sprite.setScale(0.1f);

        this.setPosition(x, y);
        this.setRotation(rotation);
        setBounds(new Rectangle(sprite.getX(), sprite.getY(), getWidth(), getHeight()));

        calcMovement(); // Because it moves in a straight line, calculate movement on x and y-axis per turn only once (saves cpu)
        if (CustomUtils.booleanRandom(10)) { // 1 in 10 change to bounce (once)
            bounces = 1;
        }
    }

    public ProjectileActor(GameStage stage, float x, float y, float rotation, SpriteActor owner, INotifyProjectileEvents observer) {
        this(stage, x, y, rotation, owner);
        this.observer = observer;
    }

    public void setCollisionBehavior(IProjectileCollisionBehavior collisionBehavior) {
        this.collisionBehavior = collisionBehavior;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public void act(float delta) {
        super.act(delta);
        setPosition(getX() + xAmount, getY() + yAmount); // Go forward
        if (CustomUtils.outOfBoundsX(getX(), sprite.getWidth(), speed)) { bounce(); } // Check if at border of screen, of so: bounce or be removed
        if (CustomUtils.outOfBoundsY(getY(), sprite.getHeight(), speed)) { bounce(); }
        checkCollisions();
    }

    private void checkCollisions() {
        if (collisionBehavior.checkCollisions() && observer != null) {
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
