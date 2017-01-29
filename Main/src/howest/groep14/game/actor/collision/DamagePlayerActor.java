package howest.groep14.game.actor.collision;

import howest.groep14.game.actor.SpriteActor;

public class DamagePlayerActor extends DamageOnContact {

    public DamagePlayerActor(SpriteActor owner, int damage, int selfDamage) {
        super(owner, damage, selfDamage);
    }

    public void checkCollisions(float delta) {
        checkPlayerCollisions(delta);
        checkCollisionCache();
    }

    @Override
    public CollisionBehavior copy(SpriteActor newOwner) {
        return new DamagePlayerActor(newOwner, damage, selfDamage);
    }
}
