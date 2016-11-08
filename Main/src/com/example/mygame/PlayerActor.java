package com.example.mygame;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;
import com.sun.javaws.Main;

public class PlayerActor extends Actor {
    Sprite sprite = new Sprite(new Texture("Desktop/Assets/player.png"));
    final float max_speed = 10;
    final float acceleration = 1;
    float current_speed = 0;
    final float rotation_speed = 5;
    float angle = 0;


    public PlayerActor(){
        setBounds(sprite.getX(),sprite.getY(),sprite.getWidth(),sprite.getHeight());
        angle = getRotation();

        /*
        addListener(new InputListener(){
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.RIGHT){

                    MoveByAction mba = new MoveByAction();
                    mba.setAmount(100f,0f); //x+100 y+0
                    mba.setDuration(1f); //1 sec

                    PlayerActor.this.addAction(mba);
                    //PlayerActor.this.setPosition(getX()+speed,getY());
                }
                if (keycode == Input.Keys.LEFT){
                    /*
                    MoveByAction mba = new MoveByAction();
                    mba.setAmount(-100f,0f); //x-100 y+0
                    mba.setDuration(1f); //1 sec

                    PlayerActor.this.addAction(mba);
                    */
                    PlayerActor.this.setRotation(angle += rotation_speed);
                    PlayerActor.this.sprite.setRotation(angle);
/*
                }
                if (keycode == Input.Keys.UP){
                    MoveByAction mba = new MoveByAction();
                    mba.setAmount(0f,100f); //x+0 y+100
                    mba.setDuration(1f); //1 sec

                    PlayerActor.this.addAction(mba);
                }
                if (keycode == Input.Keys.DOWN){
                    MoveByAction mba = new MoveByAction();
                    mba.setAmount(0f,-100f); //x+0 y-100
                    mba.setDuration(1f); //1 sec

                    PlayerActor.this.addAction(mba);
                }
                return true;
            }
        });
                */
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

    private boolean outOfBoundsX(float update) {
        return (getX() + update > myGame.WIDTH - sprite.getWidth()) || (getX() + update < 0);
    }

    private boolean outOfBoundsY(float update) {
        return (getY() + update > myGame.HEIGHT - sprite.getHeight()) || (getY() + update < 0);
    }
}
