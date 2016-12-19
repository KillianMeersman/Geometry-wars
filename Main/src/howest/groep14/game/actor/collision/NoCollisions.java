package howest.groep14.game.actor.collision;

import howest.groep14.game.actor.EnemyActor;
import howest.groep14.game.actor.GeomeActor;
import howest.groep14.game.actor.PlayerActor;
import howest.groep14.game.actor.SpriteActor;

public class NoCollisions extends CollisionBehavior {

    public NoCollisions(SpriteActor owner) {
        super(owner);
    }

    @Override
    public CollisionBehavior copy(SpriteActor newOwner) {
        return new NoCollisions(newOwner);
    }

    @Override
    public void checkCollisions(float delta) {
        // Do nothing
    }

    @Override
    protected void damagePlayer(PlayerActor player) {

    }

    @Override
    protected void damageEnemy(EnemyActor enemy) {

    }

    @Override
    protected void damageGeome(GeomeActor geome) {

    }
}
