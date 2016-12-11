package howest.groep14.game.actor.collision;

import howest.groep14.game.actor.SpriteActor;

public abstract class CollisionBehavior {
    protected SpriteActor owner;

    public CollisionBehavior(SpriteActor owner) {
        this.owner = owner;
    }

    public abstract CollisionBehavior copy(SpriteActor newOwner);

    public abstract void checkCollisions(float delta);
}
