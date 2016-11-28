package howest.groep14.game.actor.projectile;

public interface INotifyProjectileEvents {
    void projectileHit(ProjectileActor projectileActor);
    void projectileOutOfBounds(ProjectileActor projectileActor);
}
