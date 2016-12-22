package howest.groep14.game.actor.movement;

import howest.groep14.game.actor.SpriteActor;

public abstract class MovementBehavior {
    protected SpriteActor owner;
    protected boolean checkBounds = false;

    public MovementBehavior(SpriteActor owner) {
        this.owner = owner;
    }

    public abstract MovementBehavior copy(SpriteActor newOwner);

    public void setBoundsChecking(boolean set) {
        this.checkBounds = set;
    }

    public SpriteActor getOwner() {
        return owner;
    }

    public abstract void move(float delta);

    public void setSpeed(float speed) {}

    public void multiplySpeed(float factor) {
        setSpeed(getSpeed() * factor);
    }

    public float getSpeed() {
        return 0f;
    }

    public void collide(SpriteActor victim) {}

    public void setPosition(float x, float y) {}

    public void setRotation(float degrees) {}

    public static float getUpdatedRotation(float rotation, float update) {
        rotation += update;
        if (rotation > 360) {
            rotation -= 360;
        } else if (rotation < 0) {
            rotation += 360;
        }
        return rotation;
    }
}