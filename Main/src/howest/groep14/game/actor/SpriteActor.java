package howest.groep14.game.actor;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import howest.groep14.game.CustomUtils;
import howest.groep14.game.GameStage;

public class SpriteActor extends Actor { // An actor that holds a sprite, and updates it
    protected Rectangle bounds;
    protected Sprite sprite;
    protected GameStage gameStage;
    Polygon polygon;
    float SPRITE_WIDTH;
    float SPRITE_HEIGHT;

    public SpriteActor(GameStage stage) {
        this.gameStage = stage;
        bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        SPRITE_WIDTH = sprite.getWidth() * sprite.getScaleX();
        SPRITE_HEIGHT = sprite.getHeight() * sprite.getScaleY();
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void updateRotation(float angle) {
        this.setRotation(this.getRotation() + angle);
        sprite.setRotation(sprite.getRotation() + angle);
    }

    // Update the position along the X- and Y-axis directly (don't move in the direction you are facing)
    public void updatePositionAbsolute(float distanceX, float distanceY) {
        float x = getX() + distanceX;
        float y = getY() + distanceY;
        if (CustomUtils.outOfBoundsX(getX(), sprite.getWidth(), distanceX)) { x = getX(); }
        if (CustomUtils.outOfBoundsY(getY(), sprite.getWidth(), distanceY)) { y = getY(); }
        setPosition(x, y);
    }

    // Move forward in the direction you are facing
    public void updatePositionForward(float distanceX, float distanceY) {
        float rotation = (float) Math.toRadians(getRotation());
        float x = (float) Math.cos(rotation) * distanceX;
        if (CustomUtils.outOfBoundsX(getX(), sprite.getWidth(), x)) { x = 0; }
        float y = (float) Math.sin(rotation) * distanceY;
        if (CustomUtils.outOfBoundsY(getY(), sprite.getWidth(), y)) { y = 0; }
        setPosition(getX() + x, getY() + y);
    }

    public void updatePositionForward(float distance) {
        updatePositionForward(distance, distance);
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public void setBounds(Rectangle bounds) {
        this.bounds = bounds;
    }

    @Override
    public void setRotation(float degrees) {
        super.setRotation(degrees);
        sprite.setRotation(degrees);
    }

    public Vector2 getPosition() {
        return new Vector2(getX(), getY());
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        sprite.setPosition(x, y);
    }

    public void setPosition(Vector2 position) {
        setPosition(position.x, position.y);
    }

    @Override
    protected void positionChanged() {
        super.positionChanged();
        sprite.setPosition(getX(), getY());
        bounds.setPosition(getX() + SPRITE_WIDTH, getY() + SPRITE_HEIGHT);
    }

    @Override
    public void setScale(float scaleXY) {
        super.setScale(scaleXY);
        this.sprite.setScale(scaleXY);
        SPRITE_WIDTH = sprite.getWidth() * scaleXY;
        SPRITE_HEIGHT = sprite.getHeight() * scaleXY;
        bounds.setSize(SPRITE_WIDTH, SPRITE_HEIGHT);
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

    }

    @Override
    public boolean remove() {
        return super.remove();
    }
}
