package com.example.mygame;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class SpriteActor extends Actor { // An actor that holds a sprite, and updates it
    Sprite sprite;

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void updateRotation(float angle) {
        this.setRotation(this.getRotation() + angle);
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

    @Override
    public void setRotation(float degrees) {
        super.setRotation(degrees);
        sprite.setRotation(degrees);
    }

    @Override
    public void setPosition(float x, float y) {
        super.setPosition(x, y);
        sprite.setPosition(x, y);
    }

    @Override
    protected void positionChanged() {
        super.positionChanged();
        sprite.setPosition(getX(),getY());
    }

    @Override
    public void setScale(float scaleXY) {
        super.setScale(scaleXY);
        this.sprite.setScale(scaleXY);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

}
