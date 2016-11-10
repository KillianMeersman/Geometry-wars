package com.example.mygame;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class PlayerActor extends SpriteActor {
    int ROUNDS_PER_SECOND = 20;
    final float MAX_SPEED = 10;
    final float ACCEL = 1f;
    final float ROT_SPEED = 5;
    final float FRICTION = 0.92f;

    float speed_x = 0;
    float speed_y = 0;
    float lastDelta = 0;
    public int projectilesFired = 0;
    ControlScheme controlScheme;

    public PlayerActor(){
        Texture texture = new Texture("Desktop/Assets/spaceship.png");
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sprite = new Sprite(texture);

        setBounds(sprite.getX(),sprite.getY(),sprite.getWidth(),sprite.getHeight());

        controlScheme = new ControlScheme();
        controlScheme.UP = Input.Keys.UP;
        controlScheme.DOWN = Input.Keys.DOWN;
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
        updatePositionAbsolute(speed_x *= FRICTION, speed_y *= FRICTION);
        checkInput();
    }

    private void checkInput() { // check for pressed keys
        if (Gdx.input.isKeyPressed(controlScheme.UP)) {
            speed_y = Math.min(speed_y + ACCEL, MAX_SPEED);
        }
        if (Gdx.input.isKeyPressed(controlScheme.DOWN)) {
            speed_y = Math.max(-MAX_SPEED, speed_y - ACCEL);
        }
        if (Gdx.input.isKeyPressed(controlScheme.LEFT)) {
            speed_x = Math.max(-MAX_SPEED, speed_x - ACCEL);
        }
        if (Gdx.input.isKeyPressed(controlScheme.RIGHT)) {
            speed_x = Math.min(speed_x + ACCEL, MAX_SPEED);
        }

        if (Gdx.input.isKeyPressed(controlScheme.FIRE)) {
            fireProjectile();
        }
        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            fireProjectile();
        }
        faceMouse();
    }

    private void fireProjectile() {
        if (lastDelta > 1f / ROUNDS_PER_SECOND) { // check if not over rate of fire (delta is the time since last frame)
            ProjectileActor projectile = new ProjectileActor(sprite.getX(), sprite.getY(), CustomUtils.getAngleToMouse(getX(), getY()), this);
            getStage().addActor(projectile);
            projectilesFired++;
            lastDelta = 0; // reset lastDelta
        } else {
            lastDelta += Gdx.graphics.getDeltaTime(); // add time since last frame to lastDelta)
        }
    }

    private void faceMouse() {
        setRotation(CustomUtils.getAngleToMouse(getX(), getY()));
    }

    class ControlScheme {
        int UP, DOWN, LEFT, RIGHT, FIRE;

        /*
        public void setFORWARD(int key) {
            UP = key;
        }

        public int getForward() {
            return UP;
        }

        public void setBACKWARDS(int key) {
            DOWN = key;
        }

        public int getBACKWARDS() {
            return DOWN;
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
