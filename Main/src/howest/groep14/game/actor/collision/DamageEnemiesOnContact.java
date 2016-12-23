package howest.groep14.game.actor.collision;

import howest.groep14.game.actor.SpriteActor;

public class DamageEnemiesOnContact extends DamageOnContact {

    public DamageEnemiesOnContact(SpriteActor owner, int damage, int selfDamage) {
        super(owner, damage, selfDamage);
    }

    public void checkCollisions(float delta) {
        checkEnemyCollisions(delta);
        checkCollisionCache();
    }

    @Override
    public CollisionBehavior copy(SpriteActor newOwner) {
        return new DamageEnemiesOnContact(newOwner, damage, selfDamage);
    }
}
