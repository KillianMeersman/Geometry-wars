package howest.groep14.game.powers;

import howest.groep14.game.actor.IProjectileObserver;
import howest.groep14.game.actor.SpriteActor;
import howest.groep14.game.actor.attack.MultipleFireInDirection;
import howest.groep14.game.actor.attack.FireInDirection;

public class Quadfire extends PowerBehavior {
    private FireInDirection originalBehavior;
    private boolean duplicate = true;

    public Quadfire(float duration) {
        super(duration);
    }

    @Override
    public void startPower(SpriteActor target) {
        if (target instanceof IProjectileObserver && !(target.getAttackBehavior() instanceof MultipleFireInDirection)) {
            this.originalBehavior = (FireInDirection) target.getAttackBehavior();
            target.setAttackBehavior(new MultipleFireInDirection(originalBehavior, 4, originalBehavior.getProjectileSprite()));
            duplicate = false;
        }
    }

    @Override
    public void doPower(SpriteActor target) {

    }

    @Override
    public void endPower(SpriteActor target ) {
        if (!duplicate) {
            target.setAttackBehavior(originalBehavior);
        }
    }

    @Override
    public String toString() {
        return "Quadfire!";
    }
}
