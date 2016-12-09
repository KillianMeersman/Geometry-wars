package howest.groep14.game.actor.projectile;

import howest.groep14.game.CustomUtils;
import howest.groep14.game.GameStage;
import howest.groep14.game.actor.SpriteActor;
import howest.groep14.game.actor.enemy.EnemyActor;

public class DestroyEnemyCollisionBehavior implements IProjectileCollisionBehavior {
    private final ProjectileActor owner;

    public DestroyEnemyCollisionBehavior(ProjectileActor owner) {
        this.owner = owner;
    }

    @Override
    public boolean checkCollisions(float delta) {
        GameStage stage = owner.getStage();
        try {
            for (SpriteActor actor : stage.getCubeEnemies()) {
                if (CustomUtils.isColliding(owner, actor)) {
                    stage.removeCubeEnemy(actor);
                    stage.removeProjectile(owner);
                    return true;
                }
            }
            for (SpriteActor actor : stage.getCircleEnemies()) {
                if (CustomUtils.isColliding(owner, actor)) {
                    stage.removeCircleEnemy(actor);
                    stage.removeProjectile(owner);
                    return true;
                }
            }
        } catch (NullPointerException ignored) {

        }
        return false;
    }
}
