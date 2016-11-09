package com.example.mygame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class ProjectileActor extends SpriteActor {
    float speed;
    float xAmount, yAmount;
    Actor owner;

    public ProjectileActor(float x, float y, float rotation, Actor owner) {
        speed = 15;
        this.owner = owner;
        Texture texture = new Texture("Desktop/Assets/blueProjectile.png");
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sprite = new Sprite(texture);
        sprite.setRotation(rotation);
        sprite.setScale(0.1f);
        this.setPosition(x, y);
        this.setRotation(rotation);
        float radians = (float) Math.toRadians(rotation);
        this.xAmount = (float) Math.cos(radians) * speed;
        this.yAmount = (float) Math.sin(radians) * speed;
    }

    public void act(float delta) {
        super.act(delta);
        setPosition(getX() + xAmount, getY() + yAmount);
        if (CustomUtils.outOfBoundsX(getX(), owner.getWidth(), speed)) { this.remove(); }
        if (CustomUtils.outOfBoundsY(getY(), owner.getHeight(), speed)) { this.remove(); }
    }
}
