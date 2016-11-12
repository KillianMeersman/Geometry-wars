package com.example.mygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.example.mygame.enemy.EnemyActor;

import java.util.Random;

class ProjectileActor extends SpriteActor {
    private Random random = new Random();
    private float speed;
    private float xAmount, yAmount;
    private int bounces;
    private PlayerActor owner;

    ProjectileActor(GameStage stage, float x, float y, float rotation, PlayerActor owner) {
        super(stage);
        speed = 15;
        this.owner = owner;

        Texture texture = new Texture("Desktop/Assets/greyProjectile.png");
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sprite = new Sprite(texture);
        sprite.setRotation(rotation);
        sprite.setScale(0.1f);

        this.setPosition(x, y);
        this.setRotation(rotation);
        setBounds(new Rectangle(sprite.getX(), sprite.getY(), getWidth(), getHeight()));

        calcMovement(); // Because it moves in a straight line, calculate movement on x and y-axis per turn only once (saves cpu)
        if (random.nextInt(10) == 9) { // 1 in 10 change to bounce (once)
            bounces = 1;
        }

        Sound shootSound = Gdx.audio.newSound(Gdx.files.internal("Desktop/Assets/laser.mp3"));
        shootSound.play(0.1f);
}


    public void act(float delta) {
        super.act(delta);
        setPosition(getX() + xAmount, getY() + yAmount); // Go forward
        if (CustomUtils.outOfBoundsX(getX(), owner.getWidth(), speed)) { bounce(); } // Check if at border of screen, of so: bounce or be removed
        if (CustomUtils.outOfBoundsY(getY(), owner.getHeight(), speed)) { bounce(); }
        checkCollisions();
    }

    private void checkCollisions() {
        outerLoop:
            for (EnemyActor actor : gameStage.getEnemyActors()) {
                if (actor.getBounds().overlaps(getBounds())) {
                    ((GameStage) getStage()).removeEnemyActor(actor);
                    ((GameStage) getStage()).removeProjectile(this);
                    owner.updateScore(1);
                    Sound blipSound = Gdx.audio.newSound(Gdx.files.internal("Desktop/Assets/blip.mp3"));
                    blipSound.play();
                    break outerLoop;
                }
            }
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
