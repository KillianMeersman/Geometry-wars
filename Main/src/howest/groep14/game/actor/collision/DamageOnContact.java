package howest.groep14.game.actor.collision;

import howest.groep14.game.CustomUtils;
import howest.groep14.game.actor.EnemyActor;
import howest.groep14.game.actor.PlayerActor;
import howest.groep14.game.actor.SpriteActor;

public class DamageOnContact extends CollisionBehavior {
    protected int damage, selfDamage;

    public DamageOnContact(SpriteActor owner, int damage, int selfDamage) {
        super(owner);
        this.damage = damage;
        this.selfDamage = selfDamage;
    }

    @Override
    public CollisionBehavior copy(SpriteActor newOwner) {
        return new DamageOnContact(newOwner, damage, selfDamage);
    }

    @Override
    public void checkCollisions(float delta) {
        for (EnemyActor actor : owner.getStage().getEnemies()) {
            if (CustomUtils.isColliding(actor, owner)) {
                actor.damage(damage);
                owner.collide(actor);
                owner.damage(selfDamage);
            }
        }
        for (PlayerActor actor : owner.getStage().getPlayers()) {
            if (CustomUtils.isColliding(actor, owner)) {
                actor.damage(damage);
                owner.collide(actor);
                owner.damage(selfDamage);
            }
        }
    }
}
