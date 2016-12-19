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

    public void collide(SpriteActor victim) {}

    public void setPosition(float x, float y) {}

    public void setRotation(float degrees) {}
}