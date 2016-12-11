package howest.groep14.game.actor.collision;

import howest.groep14.game.CustomUtils;
import howest.groep14.game.actor.PlayerActor;
import howest.groep14.game.actor.SpriteActor;

public class DamagePlayersOnContact extends DamageOnContact {

    public DamagePlayersOnContact(SpriteActor owner, int damage, int selfDamage) {
        super(owner, damage, selfDamage);
    }

    public void checkCollisions(float delta) {
        for (PlayerActor actor : owner.getStage().getPlayers()) {
            if (CustomUtils.isColliding(actor, owner)) {
                actor.damage(damage);
                owner.collide(actor);
                owner.damage(selfDamage);
            }
        }
    }

    @Override
    public CollisionBehavior copy(SpriteActor newOwner) {
        return new DamagePlayersOnContact(newOwner, damage, selfDamage);
    }
}
