package howest.groep14.game.actor.health;

import howest.groep14.game.actor.SpriteActor;

// Decorator
public class DeathObserver extends HealthBehavior {
    private StandardHealth wrapper;
    private IDeathObserver observer;

    public DeathObserver(StandardHealth wrapper, IDeathObserver observer) {
        super(wrapper.owner);
        this.wrapper = wrapper;
        this.observer = observer;
    }

    @Override
    public void damage(int damage) {
        wrapper.damage(damage);
        if (wrapper.health < 1 || !owner.isVisible()) {
            observer.actorDeath(wrapper.owner);
        }
    }

    @Override
    public HealthBehavior copy(SpriteActor newOwner) {
        return wrapper.copy(newOwner);
    }

    @Override
    public String toString() {
        return wrapper.toString();
    }


}
