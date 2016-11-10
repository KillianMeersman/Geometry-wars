package com.example.mygame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g3d.particles.influencers.ColorInfluencer;
import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Random;

public class ProjectileActor extends SpriteActor {
    Random random = new Random();
    float speed;
    float xAmount, yAmount;
    int bounces;
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
        calcMovement(); // Because it moves in a straight line, calculate movement on x and y-axis per turn only once (saves cpu)
        if (random.nextInt(10) == 9) { // 1 in 10 change to bounce (once)
            bounces = 1;
        }
}

    public void act(float delta) {
        super.act(delta);
        setPosition(getX() + xAmount, getY() + yAmount); // Go forward
        if (CustomUtils.outOfBoundsX(getX(), owner.getWidth(), speed)) { bounce(); } // Check if at border of screen, of so: bounce or be removed
        if (CustomUtils.outOfBoundsY(getY(), owner.getHeight(), speed)) { bounce(); }
    }

    public void bounce() {
        if (bounces > 0) {
            if (random.nextBoolean()) {
                setRotation(getRotation() + 130); // Add or subtract 130 degrees from rotation
            } else {
                setRotation(getRotation() - 130);
            }
            bounces--;
            calcMovement(); // we have rotated, so we need to recalculate x and y movement per turn
        } else {
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
