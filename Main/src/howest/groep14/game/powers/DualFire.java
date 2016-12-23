package howest.groep14.game.powers;

import howest.groep14.game.actor.IProjectileObserver;
import howest.groep14.game.actor.SpriteActor;
import howest.groep14.game.actor.attack.MultipleFireInDirection;
import howest.groep14.game.actor.attack.FireInDirection;

public class DualFire extends PowerBehavior {
    private FireInDirection originalBehavior;

    public DualFire(float duration) {
        super(duration);
    }

    @Override
    public void startPower(SpriteActor target) {
        if (target instanceof IProjectileObserver) {
            this.originalBehavior = (FireInDirection) target.getAttackBehavior();
            target.setAttackBehavior(new MultipleFireInDirection(originalBehavior, 2));
        }
    }

    @Override
    public void doPower(SpriteActor target) {

    }

    @Override
    public void endPower(SpriteActor target) {
        if (target instanceof IProjectileObserver) {
            target.setAttackBehavior(originalBehavior);
        }
    }
}
