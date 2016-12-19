package howest.groep14.game.actor.collision;

import howest.groep14.game.CustomUtils;
import howest.groep14.game.actor.EnemyActor;
import howest.groep14.game.actor.GeomeActor;
import howest.groep14.game.actor.PlayerActor;
import howest.groep14.game.actor.SpriteActor;

public abstract class CollisionBehavior {
    protected SpriteActor owner;

    public CollisionBehavior(SpriteActor owner) {
        this.owner = owner;
    }

    public abstract CollisionBehavior copy(SpriteActor newOwner);

    public abstract void checkCollisions(float delta);

    protected void checkPlayerCollisions(float delta) {
        for (PlayerActor player : owner.getStage().getPlayers()) {
            if (CustomUtils.isColliding(owner, player)) {
                damagePlayer(player);
            }
        }
    }

    protected abstract void damagePlayer(PlayerActor player);

    protected void checkEnemyCollisions(float delta) {
        for (EnemyActor enemy : owner.getStage().getEnemies()) {
            if (CustomUtils.isColliding(owner, enemy)) {
                damageEnemy(enemy);
            }
        }
    }

    protected abstract void damageEnemy(EnemyActor enemy);

    protected void checkGeomeCollisions(float delta) {
        for (GeomeActor geome : owner.getStage().getGeomes()) {
            if (CustomUtils.isColliding(owner, geome)) {
                damageGeome(geome);
            }
        }
    }

    protected abstract void damageGeome(GeomeActor geome);
}
