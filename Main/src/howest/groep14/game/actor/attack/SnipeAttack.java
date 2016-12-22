package howest.groep14.game.actor.attack;

import howest.groep14.game.CustomUtils;
import howest.groep14.game.actor.IProjectileObserver;
import howest.groep14.game.actor.ProjectileActor;
import howest.groep14.game.actor.SpriteActor;

public class SnipeAttack extends AttackBehavior implements IProjectileObserver {
    private SpriteActor target;
    private ProjectileActor projectileTemplate;
    private final float TIME_BETWEEN_SHOTS;
    private float totalDelta = 0;

    public SnipeAttack(SpriteActor owner, SpriteActor target, ProjectileActor projectileTemplate, float timeBetweenShots) {
        super(owner);
        this.target = target;
        this.projectileTemplate = projectileTemplate;
        this.TIME_BETWEEN_SHOTS = timeBetweenShots;
    }

    @Override
    public AttackBehavior copy(SpriteActor newOwner) {
        return new SnipeAttack(newOwner, target, projectileTemplate, TIME_BETWEEN_SHOTS);
    }

    @Override
    public void engage(float delta) {
        if (totalDelta >= TIME_BETWEEN_SHOTS) {
            fireProjectile();
            totalDelta = 0;
        } else {
            totalDelta += delta;
        }
    }

    private void fireProjectile() {
        ProjectileActor projectile = new ProjectileActor(projectileTemplate);
        projectile.setPosition(owner.getPosition());
        projectile.setRotation(CustomUtils.getAngleToFace(owner, target));
        owner.getStage().addProjectile(projectile);
    }

    @Override
    public void projectileHit(ProjectileActor projectile, SpriteActor victim) {

    }

    @Override
    public void projectileOutOfBounds(ProjectileActor projectile) {

    }

    @Override
    public void projectileDestroyed(ProjectileActor projectile) {

    }

    @Override
    public SpriteActor getOwner() {
        return owner;
    }
}
