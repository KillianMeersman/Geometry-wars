package howest.groep14.game.actor.attack;

import howest.groep14.game.actor.IProjectileObserver;
import howest.groep14.game.actor.SpriteActor;
import howest.groep14.game.actor.movement.MovementBehavior;

public class MultipleFireInDirection extends FireInDirection {
    private int streams = 1;
    private int angleIncrease;

    public MultipleFireInDirection(IProjectileObserver owner, int streams, int roundsPerSecond) {
        super(owner);
        this.streams = streams;
        this.ROUNDS_PER_SECOND = roundsPerSecond;
        calcAngleIncrease();
    }

    public MultipleFireInDirection(FireInDirection copy, int streams) {
        super(copy.observer);
        this.streams = streams;
        this.ROUNDS_PER_SECOND = copy.ROUNDS_PER_SECOND;
        calcAngleIncrease();
    }

    @Override
    public void engage(float delta) {
        if (isCooldownOver()) {
            fireProjectile(owner.getRotation(), true);  // Fire forwards
            for (int i = 0; i < streams; i++) {
                fireProjectile(MovementBehavior.getUpdatedRotation(owner.getRotation(), angleIncrease * i), false);    // Fire backwards
            }
            lastDelta = 0;
        } else {
            lastDelta += delta;
        }
    }

    @Override
    public AttackBehavior copy(SpriteActor newOwner) {
        return super.copy(newOwner);
    }

    private void calcAngleIncrease() {
        angleIncrease = 360 / streams;
    }
}
