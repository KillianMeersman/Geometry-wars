package howest.groep14.game.powers;

import howest.groep14.game.actor.EnemyActor;
import howest.groep14.game.actor.SpriteActor;
import howest.groep14.game.actor.health.StandardHealth;

public class ArmoredEnemies extends PowerBehavior {

    public ArmoredEnemies(float duration) {
        super(duration);
    }

    @Override
    public void startPower(SpriteActor target) {
        updateEnemyHealth(1, target);
    }

    @Override
    public void doPower(SpriteActor target) {

    }

    @Override
    public void endPower(SpriteActor target) {
        updateEnemyHealth(-1, target);
    }

    private void updateEnemyHealth(int update, SpriteActor target) {
        for (EnemyActor enemy : target.getStage().getEnemies()) {
            if (enemy.getHealthBehavior() instanceof StandardHealth) {
                ((StandardHealth) enemy.getHealthBehavior()).updateHealth(update, false);
            }
        }
    }
}
