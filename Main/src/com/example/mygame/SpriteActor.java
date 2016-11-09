package com.example.mygame;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class SpriteActor extends Actor {
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

    public void updatePosition(float distance) {
        float rotation = (float) Math.toRadians(getRotation());
        float x = (float) Math.cos(rotation) * distance;
        if (CustomUtils.outOfBoundsX(getX(), sprite.getWidth(), x)) { x = 0; }
        float y = (float) Math.sin(rotation) * distance;
        if (CustomUtils.outOfBoundsY(getY(), sprite.getWidth(), y)) { y = 0; }
        setPosition(getX() + x, getY() + y);
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
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }



}
