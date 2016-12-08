package howest.groep14.game.actor.movement;

import howest.groep14.game.actor.SpriteActor;

public abstract class MovementBehavior {
    protected SpriteActor owner;

    MovementBehavior(SpriteActor owner) {
        this.owner = owner;
    }

    public abstract void move(float delta);
}