package howest.groep14.game.powers.up;

import howest.groep14.game.actor.IProjectileObserver;
import howest.groep14.game.actor.SpriteActor;
import howest.groep14.game.actor.attack.MultipleFireInDirection;
import howest.groep14.game.actor.attack.FireInDirection;
import howest.groep14.game.powers.PowerBehavior;

public class DualFire extends PowerBehavior {
    private IProjectileObserver owner;
    private FireInDirection originalBehavior;

    public DualFire(float duration, IProjectileObserver owner) {
        super(duration);
        this.owner = owner;
    }

    @Override
    public void startPower(SpriteActor target) {
        this.originalBehavior = (FireInDirection) target.getAttackBehavior();
        target.setAttackBehavior(new MultipleFireInDirection(originalBehavior, 2));
    }

    @Override
    public void doPower(SpriteActor target) {

    }

    @Override
    public void endPower(SpriteActor target) {
        target.setAttackBehavior(originalBehavior);
    }
}
