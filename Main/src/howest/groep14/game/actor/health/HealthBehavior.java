package howest.groep14.game.actor.health;

import howest.groep14.game.actor.SpriteActor;

public abstract class HealthBehavior {
    protected SpriteActor owner;

    public HealthBehavior(SpriteActor owner) {
        this.owner = owner;
    }

    public abstract void damage(int damage);

    public abstract HealthBehavior copy(SpriteActor newOwner);

    public abstract String toString();
}
