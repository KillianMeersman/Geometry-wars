package howest.groep14.game.actor.projectile;

import howest.groep14.game.CustomUtils;
import howest.groep14.game.GameStage;
import howest.groep14.game.actor.PlayerActor;

public class DestroyPlayersBehavior implements IProjectileCollisionBehavior {
    private final ProjectileActor owner;

    public DestroyPlayersBehavior(ProjectileActor owner) {
        this.owner = owner;
    }

    @Override
    public boolean checkCollisions(float delta) {
        GameStage stage = (GameStage) owner.getStage();
        try {
            for (PlayerActor player : stage.getPlayers()) {
                if (CustomUtils.isColliding(owner, player) && stage.isCollisionsEnabled()) {
                    stage.removePlayer(player);
                    stage.removeProjectile(owner);
                    return true;
                }
            }
        } catch (NullPointerException ignored) {

        }
        return false;
    }
}
