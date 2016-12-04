package howest.groep14.game.actor.projectile;

import howest.groep14.game.CustomUtils;
import howest.groep14.game.GameStage;
import howest.groep14.game.actor.enemy.EnemyActor;

public class DestroyEnemyCollisionBehavior implements IProjectileCollisionBehavior {
    private final ProjectileActor owner;

    public DestroyEnemyCollisionBehavior(ProjectileActor owner) {
        this.owner = owner;
    }

    @Override
    public boolean checkCollisions() {
                /*
        try {
            for (EnemyActor actor : gameStage.getCubeEnemies()) {
                if (actor.getBounds().overlaps(getBounds())) {
                    ((GameStage) getStage()).removeCubeEnemy(actor);
                    ((GameStage) getStage()).removeProjectile(this);
                    owner.updateScore(1);
                    break;
                }
            }
        } catch (NullPointerException npe) {
            System.err.println("nullpointer!");
        }
        */
        GameStage stage = (GameStage) owner.getStage();
        try {
            for (EnemyActor actor : stage.getCubeEnemies()) {
                if (CustomUtils.isColliding(owner, actor)) {
                    stage.removeCubeEnemy(actor);
                    stage.removeProjectile(owner);
                    return true;
                }
            }
            for (EnemyActor actor : stage.getCircleEnemies()) {
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
