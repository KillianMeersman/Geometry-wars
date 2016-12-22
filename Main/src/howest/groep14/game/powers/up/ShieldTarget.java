package howest.groep14.game.powers.up;

import howest.groep14.game.SpriteRepository;
import howest.groep14.game.actor.SpriteActor;
import howest.groep14.game.actor.health.HealthBehavior;
import howest.groep14.game.actor.health.Shield;
import howest.groep14.game.powers.PowerBehavior;

public class ShieldTarget extends PowerBehavior {
    HealthBehavior originalBehavior;

    @Override
    public void startPower(SpriteActor target) {
        originalBehavior = target.getHealthBehavior();
        target.setHealthBehavior(new Shield(target, SpriteRepository.getShield()));
    }

    @Override
    public void doPower(SpriteActor target) {

    }

    @Override
    public void endPower(SpriteActor target) {
        target.setHealthBehavior(originalBehavior);
    }
}
