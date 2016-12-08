package howest.groep14.game.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import howest.groep14.game.CustomUtils;
import howest.groep14.game.GameStage;
import howest.groep14.game.actor.movement.MovementBehavior;
import howest.groep14.game.actor.movement.NoMovement;

public class SpriteActor extends Actor { // An actor that holds a sprite, and updates it
    protected final GameStage gameStage;
    protected Sprite sprite;
    protected MovementBehavior movementBehavior;


    public SpriteActor(GameStage stage, Sprite sprite) {
        this.gameStage = stage;
        this.sprite = sprite;
        setPosition(1, 1);
        this.movementBehavior = new NoMovement(this);
        setHeight(sprite.getHeight());
        setWidth(sprite.getWidth());
    }

    public SpriteActor(GameStage stage, Sprite sprite, MovementBehavior movementBehavior) {
        this(stage, sprite);
        this.movementBehavior = movementBehavior;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public void updateRotation(float angle) {
        this.setRotation(this.getRotation() + angle);
        sprite.setRotation(sprite.getRotation() + angle);
    }

    // Update the position along the X- and Y-axis directly (don't move in the direction you are facing)
    public void updatePositionAbsolute(float distanceX, float distanceY, boolean checkBounds) {
        float x = getX() + distanceX;
        float y = getY() + distanceY;
        if (checkBounds && CustomUtils.outOfBoundsX(getX(), sprite.getWidth(), distanceX)) {
            x = getX();
        }
        if (checkBounds && CustomUtils.outOfBoundsY(getY(), sprite.getWidth(), distanceY)) {
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

    @Override
    public void setRotation(float degrees) {
        super.setRotation(degrees);
        sprite.setRotation(degrees);
    }

    public Vector2 getPosition() {
        return new Vector2(getX(), getY());
    }

    public void setPosition(Vector2 position) {
        setPosition(position.x, position.y);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        sprite.setPosition(x, y);
    }

    public MovementBehavior getMovementBehavior() {
        return movementBehavior;
    }

    public void setMovementBehavior(MovementBehavior movementBehavior) {
        this.movementBehavior = movementBehavior;
    }

    @Override
    protected void positionChanged() {
        super.positionChanged();
        sprite.setPosition(getX(), getY());
    }

    @Override
    public void setScale(float scaleXY) {
        super.setScale(scaleXY);
        this.sprite.setScale(scaleXY);
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
        movementBehavior.move(delta);
    }

    @Override
    public boolean remove() {
        return super.remove();
    }


    public float getCenterX() {
        return getX() + getWidth();
    }

    public float getCenterY() {
        return getY() + getHeight();
    }
}
