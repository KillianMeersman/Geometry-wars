package howest.groep14.game.actor;

public interface IProjectileObserver {
    void projectileHit(ProjectileActor projectile, SpriteActor victim);
    void projectileOutOfBounds(ProjectileActor projectile);
    void projectileDestroyed(ProjectileActor projectile);
    SpriteActor getOwner();
}
