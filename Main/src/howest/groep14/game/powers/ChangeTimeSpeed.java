package howest.groep14.game.powers;

import howest.groep14.game.actor.EnemyActor;
import howest.groep14.game.actor.ProjectileActor;
import howest.groep14.game.actor.SpriteActor;
import howest.groep14.game.actor.movement.MovementBehavior;

public class ChangeTimeSpeed extends PowerBehavior {
    protected final float factor;

    public ChangeTimeSpeed(float duration, float factor) {
        this.duration = duration;
        this.factor = factor;
    }

    @Override
    public void startPower(SpriteActor target) {
        updateSpeeds(factor, target);
    }

    @Override
    public void doPower(SpriteActor target) {

    }

    @Override
    public void endPower(SpriteActor target) {
        updateSpeeds(1f / factor, target);
    }

    private void updateSpeeds(float factor, SpriteActor target) {
        for (EnemyActor enemy : target.getStage().getEnemies()) {
            MovementBehavior mov = enemy.getMovementBehavior();
            mov.setSpeed(mov.getSpeed() * factor);
        }
        for (ProjectileActor projectile : target.getStage().getProjectiles()) {
            MovementBehavior mov = projectile.getMovementBehavior();
            mov.setSpeed(mov.getSpeed() * factor);
        }
    }
}
