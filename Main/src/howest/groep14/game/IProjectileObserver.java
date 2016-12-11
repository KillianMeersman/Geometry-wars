package howest.groep14.game;

import howest.groep14.game.actor.ProjectileActor;
import howest.groep14.game.actor.SpriteActor;

public interface IProjectileObserver {
    public void projectileHit(ProjectileActor projectile, SpriteActor victim);
    public void projectileOutOfBounds(ProjectileActor projectile);
    public SpriteActor getOwner();
}
