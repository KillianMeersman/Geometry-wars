package howest.groep14.game.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import howest.groep14.game.CustomUtils;
import howest.groep14.game.GameStage;
import howest.groep14.game.actor.attack.AttackBehavior;
import howest.groep14.game.actor.attack.NoAttack;
import howest.groep14.game.actor.collision.CollisionBehavior;
import howest.groep14.game.actor.collision.NoCollisions;
import howest.groep14.game.actor.health.HealthBehavior;
import howest.groep14.game.actor.health.StandardHealth;
import howest.groep14.game.actor.movement.MovementBehavior;
import howest.groep14.game.actor.movement.NoMovement;

public abstract class SpriteActor extends Actor { // An actor that holds a sprite, and updates it
    protected final GameStage stage;
    protected Sprite sprite;
    protected MovementBehavior movementBehavior;
    protected AttackBehavior attackBehavior;
    protected HealthBehavior healthBehavior;
    protected CollisionBehavior collisionBehavior;

    protected boolean removed = false;

    public SpriteActor(GameStage stage, Sprite sprite) {
        this.stage = stage;
        this.sprite = sprite;

        this.movementBehavior = new NoMovement(this);
        this.attackBehavior = new NoAttack(this);
        this.healthBehavior = new StandardHealth(this, 1);
        this.collisionBehavior = new NoCollisions(this);

        setPosition(1, 1);
        setHeight(sprite.getHeight());
        setWidth(sprite.getWidth());
    }

    public SpriteActor(GameStage stage, Sprite sprite, MovementBehavior movementBehavior, AttackBehavior attackBehavior,
                       HealthBehavior healthBehavior, CollisionBehavior collisionBehavior) {
        this.stage = stage;
        this.sprite = sprite;
        this.movementBehavior = movementBehavior;
        this.attackBehavior = attackBehavior;
        this.healthBehavior = healthBehavior;
        this.collisionBehavior = collisionBehavior;

        setPosition(1, 1);
        setHeight(sprite.getHeight());
        setWidth(sprite.getWidth());
    }

    public SpriteActor(SpriteActor copy) {
        this(copy.stage, copy.getSprite());
        this.movementBehavior = copy.getMovementBehavior().copy(this);
        this.attackBehavior = copy.getAttackBehavior().copy(this);
        this.healthBehavior = copy.getHealthBehavior().copy(this);
        this.collisionBehavior = copy.getCollisionBehavior().copy(this);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        if (isVisible()) {
            sprite.draw(batch);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.movementBehavior.move(delta);
        this.attackBehavior.engage(delta);
        this.collisionBehavior.checkCollisions(delta);
    }

    // Position & rotation
    public void updateRotation(float angle) {
        this.setRotation(this.getRotation() + angle);
        sprite.setRotation(sprite.getRotation() + angle);
    }

    // Update the position along the X- and Y-axis directly (don't move in the direction you are facing)
    public void updatePositionAbsolute(float distanceX, float distanceY, boolean checkBounds) {
        float x = getX() + distanceX;
        float y = getY() + distanceY;
        if (checkBounds && CustomUtils.outOfBoundsX(getX(), getWidth(), distanceX)) {
            x = getX();
        }
        if (checkBounds && CustomUtils.outOfBoundsY(getY(), getWidth(), distanceY)) {
            y = getY();
        }
        setPosition(x, y);
    }

    // Move forward in the direction you are facing
    public void updatePositionForward(float distanceX, float distanceY, boolean checkBounds) {
        float rotation = (float) Math.toRadians(getRotation());
        float x = (float) Math.cos(rotation) * distanceX;
        if (checkBounds && CustomUtils.outOfBoundsX(getX(), sprite.getWidth(), x)) {
            x = 0;
        }
        float y = (float) Math.sin(rotation) * distanceY;
        if (checkBounds && CustomUtils.outOfBoundsY(getY(), sprite.getWidth(), y)) {
            y = 0;
        }
        setPosition(getX() + x, getY() + y);
    }

    public void updatePositionForward(float distance, boolean checkBounds) {
        updatePositionForward(distance, distance, checkBounds);
    }

    // Getters & setters
    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public MovementBehavior getMovementBehavior() {
        return movementBehavior;
    }

    public void setMovementBehavior(MovementBehavior movementBehavior) {
        this.movementBehavior = movementBehavior;
    }

    public AttackBehavior getAttackBehavior() {
        return attackBehavior;
    }

    public void setAttackBehavior(AttackBehavior attackBehavior) {
        this.attackBehavior = attackBehavior;
    }

    public HealthBehavior getHealthBehavior() {
        return healthBehavior;
    }

    public void setHealthBehavior(HealthBehavior healthBehavior) {
        this.healthBehavior = healthBehavior;
    }

    public CollisionBehavior getCollisionBehavior() {
        return collisionBehavior;
    }

    public void setCollisionBehavior(CollisionBehavior collisionBehavior) {
        this.collisionBehavior = collisionBehavior;
    }

    @Override
    public void setRotation(float degrees) {
        super.setRotation(degrees);
        sprite.setRotation(degrees);
        movementBehavior.setRotation(degrees);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        sprite.setPosition(x, y);
        movementBehavior.setPosition(x, y);
    }

    public Vector2 getPosition() {
        return new Vector2(getX(), getY());
    }

    public void setPosition(Vector2 position) {
        this.setPosition(position.x, position.y);
    }

    @Override
    public void setScale(float scaleXY) {
        super.setScale(scaleXY);
        this.sprite.setScale(scaleXY);
    }

    @Override
    protected void positionChanged() {
        super.positionChanged();
        sprite.setPosition(getX(), getY());
    }

    @Override
    public GameStage getStage() {
        return stage;
    }

    public void damage(int damage) {
        healthBehavior.damage(damage);
    }

    public void outOfBounds() {

    }

    public void collide(SpriteActor victim) {

    }

}
