package howest.groep14.game.actor.attack;

import howest.groep14.game.actor.SpriteActor;

public abstract class AttackBehavior {
    protected SpriteActor owner, target;

    public AttackBehavior(SpriteActor owner) {
        this.owner = owner;
    }

    public abstract AttackBehavior copy(SpriteActor newOwner);

    public abstract void engage(float delta);
}
