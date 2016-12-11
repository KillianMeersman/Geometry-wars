package howest.groep14.game.actor.collision;

import howest.groep14.game.CustomUtils;
import howest.groep14.game.actor.EnemyActor;
import howest.groep14.game.actor.SpriteActor;

public class DamageEnemiesOnContact extends DamageOnContact {

    public DamageEnemiesOnContact(SpriteActor owner, int damage, int selfDamage) {
        super(owner, damage, selfDamage);
    }

    public void checkCollisions(float delta) {
        for (EnemyActor actor : owner.getStage().getEnemies()) {
            if (CustomUtils.isColliding(actor, owner)) {
                actor.damage(damage);
                owner.collide(actor);
                owner.damage(selfDamage);
            }
        }
    }

    @Override
    public CollisionBehavior copy(SpriteActor newOwner) {
        return new DamageEnemiesOnContact(newOwner, damage, selfDamage);
    }
}
