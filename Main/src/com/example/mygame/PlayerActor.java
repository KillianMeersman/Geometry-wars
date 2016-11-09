package com.example.mygame;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.sun.media.sound.ModelAbstractChannelMixer;

public class PlayerActor extends SpriteActor {
    final int ROUNDS_PER_SECOND = 15;
    final float MAX_SPEED = 10;
    final float ACCEL = 1f;
    final float ROT_SPEED = 5;
    final float FRICTION = 0.92f;

    float current_speed = 0;
    float lastDelta = 0;
    public int projectilesFired = 0;
    ControlScheme controlScheme;

    public PlayerActor(){
        Texture texture = new Texture("Desktop/Assets/spaceship.png");
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sprite = new Sprite(texture);

        setBounds(sprite.getX(),sprite.getY(),sprite.getWidth(),sprite.getHeight());

        controlScheme = new ControlScheme();
        controlScheme.FORWARD = Input.Keys.UP;
        controlScheme.BACKWARDS = Input.Keys.BACK;
        controlScheme.LEFT = Input.Keys.LEFT;
        controlScheme.RIGHT = Input.Keys.RIGHT;
        controlScheme.FIRE = Input.Keys.SPACE;
    }

    public PlayerActor(ControlScheme controlScheme) {
        this();
        this.controlScheme = controlScheme;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        updatePosition(current_speed *= FRICTION);
        checkInput();
    }

    private void checkInput() {

        if (Gdx.input.isKeyPressed(controlScheme.FORWARD)) {
            current_speed = Math.min(current_speed + ACCEL, MAX_SPEED);
        }
        if (Gdx.input.isKeyPressed(controlScheme.BACKWARDS)) {
            current_speed = Math.max(0, current_speed - ACCEL);
        }
        if (Gdx.input.isKeyPressed(controlScheme.FIRE)) {
            fireProjectile();
        }

        if (controlScheme.USEMOUSE) {
            faceMouse();
        } else {
            if (Gdx.input.isKeyPressed(controlScheme.LEFT)) {
                updateRotation(ROT_SPEED);
            }
            if (Gdx.input.isKeyPressed(controlScheme.RIGHT)) {
                updateRotation(-ROT_SPEED);
            }

        }
    }

    private void fireProjectile() {
        if (lastDelta > 1f / ROUNDS_PER_SECOND) {
            ProjectileActor projectile = new ProjectileActor(sprite.getX(), sprite.getY(), getRotation(), this);
            getStage().addActor(projectile);
            projectilesFired++;
            lastDelta = 0;
        } else {
            lastDelta += Gdx.graphics.getDeltaTime();
        }

    }

    private void faceMouse() {
        float xInput = Gdx.input.getX();
        float yInput = (Gdx.graphics.getHeight() - Gdx.input.getY());

        float angle = MathUtils.radiansToDegrees * MathUtils.atan2(yInput - getY(), xInput - getX());

        if(angle < 0){
            angle += 360;
        }
        setRotation(angle);
    }

    class ControlScheme {
        int FORWARD, BACKWARDS, LEFT, RIGHT, FIRE;
        boolean USEMOUSE = false;

        /*
        public void setFORWARD(int key) {
            FORWARD = key;
        }

        public int getForward() {
            return FORWARD;
        }

        public void setBACKWARDS(int key) {
            BACKWARDS = key;
        }

        public int getBACKWARDS() {
            return BACKWARDS;
        }

        public void setLEFT(int key) {
            LEFT = key;
        }

        public int getLEFT() {
            return LEFT;
        }

        public void setRIGHT(int key) {
            RIGHT = key;
        }

        public int getRIGHT() {
            return RIGHT;
        }
        */
    }
}
