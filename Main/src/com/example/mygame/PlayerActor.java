package com.example.mygame;


import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.actions.MoveByAction;

public class PlayerActor extends Actor {
    Sprite sprite = new Sprite(new Texture("Desktop/Assets/player.png"));
    float speed = 100;

    public PlayerActor(){
        setBounds(sprite.getX(),sprite.getY(),sprite.getWidth(),sprite.getHeight());

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
                    MoveByAction mba = new MoveByAction();
                    mba.setAmount(-100f,0f); //x-100 y+0
                    mba.setDuration(1f); //1 sec

                    PlayerActor.this.addAction(mba);
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
    }
}
