package com.example.mygame;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class PlayerActor extends Actor {
    Sprite sprite;
    final float max_speed = 10;
    final float acceleration = 1;
    float current_speed = 0;
    final float rotation_speed = 5;
    public int projectilesFired = 0;

    public PlayerActor(){
        Texture texture = new Texture("Desktop/Assets/player.png");
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sprite = new Sprite(texture);

        setBounds(sprite.getX(),sprite.getY(),sprite.getWidth(),sprite.getHeight());
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        sprite.draw(batch);
    }

    @Override
    protected void positionChanged() {
        sprite.setPosition(getX(),getY());
        super.positionChanged();
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            updateRotation(rotation_speed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            updateRotation(-rotation_speed);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            updatePosition(Math.max(max_speed, current_speed + acceleration));
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            current_speed -= Math.min(0, current_speed - acceleration);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            fireProjectile();
        }
        faceMouse();
    }

    private void fireProjectile() {
        ProjectileActor projectile = new ProjectileActor(sprite.getX(), sprite.getY(), getRotation(), this);
        getStage().addActor(projectile);
        projectilesFired++;
    }

    private void updateRotation(float angle) {
        this.setRotation(this.getRotation() + angle);
        this.sprite.setRotation(this.getRotation());
    }

    private void updatePosition(float distance) {
        float rotation = (float) Math.toRadians(getRotation());
        float x = (float) Math.cos(rotation) * distance;
        if (outOfBoundsX(x)) { x = 0; }
        float y = (float) Math.sin(rotation) * distance;
        if (outOfBoundsY(y)) { y = 0; }
        setPosition(getX() + x, getY() + y);
    }

    private void faceMouse() {
        float xInput = Gdx.input.getX();
        float yInput = (Gdx.graphics.getHeight() - Gdx.input.getY());

        float angle = MathUtils.radiansToDegrees * MathUtils.atan2(yInput - getY(), xInput - getX());

        if(angle < 0){
            angle += 360;
        }
        setRotation(angle);
        sprite.setRotation(angle);
    }

    private boolean outOfBoundsX(float update) {
        return (getX() + update > GeometryWars.WIDTH - sprite.getWidth()) || (getX() + update < 0);
    }

    private boolean outOfBoundsY(float update) {
        return (getY() + update > GeometryWars.HEIGHT - sprite.getHeight()) || (getY() + update < 0);
    }
}
