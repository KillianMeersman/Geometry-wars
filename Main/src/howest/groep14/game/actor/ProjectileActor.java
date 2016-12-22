package howest.groep14.game.actor;

import com.badlogic.gdx.graphics.g2d.Sprite;
import howest.groep14.game.CustomUtils;
import howest.groep14.game.GameStage;
import howest.groep14.game.actor.collision.DamageEnemiesOnContact;
import howest.groep14.game.actor.movement.StraightLine;

public class ProjectileActor extends SpriteActor {
    private final IProjectileObserver owner;
    private final float DEFAULT_SPEED = 12;
    private final int DEFAULT_BOUNCE_BOUND = 2;

    public ProjectileActor(GameStage stage, Sprite sprite, IProjectileObserver owner) {
        super(stage, sprite);
        this.owner = owner;
        this.setRotation(owner.getOwner().getRotation());
        this.setPosition(owner.getOwner().getPosition());
        int bounces = CustomUtils.booleanRandom(10) ? 1 : 0;
        this.movementBehavior = new StraightLine(this, this.getRotation(), DEFAULT_SPEED, bounces);
        this.collisionBehavior = new DamageEnemiesOnContact(this, 1, 1);
    }

    public ProjectileActor(GameStage stage, Sprite sprite, IProjectileObserver owner, float rotation) {
        super(stage, sprite);
        this.owner = owner;
        this.setRotation(rotation);
        this.setPosition(owner.getOwner().getPosition());
        int bounces = CustomUtils.booleanRandom(10) ? 1 : 0;
        this.movementBehavior = new StraightLine(this, rotation, DEFAULT_SPEED, bounces);
        this.collisionBehavior = new DamageEnemiesOnContact(this, 1, 1);
    }

    public ProjectileActor(ProjectileActor copy) {
        super(copy);
        this.owner = copy.getOwner();
    }

    public void act(float delta) {
        super.act(delta);
    }

    public IProjectileObserver getOwner() {
        return owner;
    }

    @Override
    public void outOfBounds() {
        owner.projectileOutOfBounds(this);
    }

    @Override
    public void collide(SpriteActor victim) {
        owner.projectileHit(this, victim);
    }

    @Override
    public boolean remove() {
        if (!removed) {
            removed = true;
            owner.projectileDestroyed(this);
            stage.removeProjectile(this);
            return super.remove();
        }
        return false;
    }
}
