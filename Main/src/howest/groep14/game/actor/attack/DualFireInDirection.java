package howest.groep14.game.actor.attack;

import howest.groep14.game.CustomUtils;
import howest.groep14.game.actor.IProjectileObserver;
import howest.groep14.game.actor.SpriteActor;
import howest.groep14.game.actor.movement.MovementBehavior;

public class DualFireInDirection extends FireInDirection {

    public DualFireInDirection(IProjectileObserver owner) {
        super(owner);
    }

    public DualFireInDirection(FireInDirection copy) {
        super(copy.observer);
        this.ROUNDS_PER_SECOND = copy.ROUNDS_PER_SECOND;
    }

    @Override
    public void engage(float delta) {
        if (isCooldownOver()) {
            fireProjectile(owner.getRotation(), true);  // Fire forwards
            fireProjectile(MovementBehavior.getUpdatedRotation(owner.getRotation(), 180), false);    // Fire backwards
            lastDelta = 0;
        } else {
            lastDelta += delta;
        }
    }

    @Override
    public AttackBehavior copy(SpriteActor newOwner) {
        return super.copy(newOwner);
    }
}
