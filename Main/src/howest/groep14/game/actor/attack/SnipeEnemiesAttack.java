package howest.groep14.game.actor.attack;

import com.badlogic.gdx.graphics.g2d.Sprite;
import howest.groep14.game.CustomUtils;
import howest.groep14.game.SpriteRepository;
import howest.groep14.game.actor.*;
import howest.groep14.game.actor.movement.StraightLine;

public class SnipeEnemiesAttack extends AttackBehavior implements IProjectileObserver {
    protected PlayerActor player;
    protected SpriteActor target;
    protected ProjectileActor projectileTemplate;
    protected final float TIME_BETWEEN_SHOTS;
    protected float totalDelta = 0;
    protected int shots = 0;


    public SnipeEnemiesAttack(SpriteActor owner, PlayerActor player, float timeBetweenShots) {
        super(owner);
        this.player = player;
        this.TIME_BETWEEN_SHOTS = timeBetweenShots;

    }

    public AttackBehavior copy(SpriteActor newOwner) {
        return new SnipeAttack(newOwner, null, projectileTemplate, TIME_BETWEEN_SHOTS);
    }

    public void engage(float delta) {
        if (totalDelta >= TIME_BETWEEN_SHOTS) {
            fireProjectile();
            totalDelta = 0;
            shots++;
        } else {
            totalDelta += delta;
        }
    }

    private void fireProjectile() {
        if (target == null || shots > 5) {
            target = owner.getStage().getEnemy();
            shots = 0;
        }
        Sprite projectile = SpriteRepository.getBlueProjectile();
        projectile.setScale(0.04f);
        ProjectileActor pro = new ProjectileActor(owner.getStage(), projectile, this);
        pro.setPosition(owner.getPosition());
        pro.setRotation(CustomUtils.getAngleToFace(owner, target));
        owner.getStage().addProjectile(pro);
    }

    @Override
    public void projectileHit(ProjectileActor projectile, SpriteActor victim) {
        player.updateScore(1);
        target = null;
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
