package howest.groep14.game.powers;

import howest.groep14.game.actor.SpriteActor;
import howest.groep14.game.actor.health.StandardHealth;
import howest.groep14.game.powers.PowerBehavior;

public class ExtraLife extends PowerBehavior {
    @Override
    public void startPower(SpriteActor target) {
        if (target.getHealthBehavior() instanceof StandardHealth) {
            ((StandardHealth) target.getHealthBehavior()).updateHealth(1, false);
        }
    }

    @Override
    public void doPower(SpriteActor target) {

    }

    @Override
    public void endPower(SpriteActor target) {

    }
}
