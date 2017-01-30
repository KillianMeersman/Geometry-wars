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
    private Sprite projectileSprite;


    public SnipeEnemiesAttack(SpriteActor owner, PlayerActor player, float timeBetweenShots, Sprite projectileSprite) {
        super(owner);
        this.player = player;
        this.TIME_BETWEEN_SHOTS = timeBetweenShots;
        this.projectileSprite = projectileSprite;

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
        float angle = CustomUtils.getAngleToFace(owner, target);
        ProjectileActor pro = new ProjectileActor(owner.getStage(), new Sprite(projectileSprite), this);
        pro.setPosition(owner.getPosition());
        pro.setRotation(angle);
        owner.setRotation(angle);
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
