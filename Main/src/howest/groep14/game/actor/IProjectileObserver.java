package howest.groep14.game.actor;

import java.util.List;

public interface IProjectileObserver {
    public void projectileHit(ProjectileActor projectile, SpriteActor victim);
    public void projectileOutOfBounds(ProjectileActor projectile);
    public void projectileDestroyed(ProjectileActor projectile);
    public SpriteActor getOwner();
}
