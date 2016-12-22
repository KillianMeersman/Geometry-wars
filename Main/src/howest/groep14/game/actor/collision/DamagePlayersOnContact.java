package howest.groep14.game.actor.collision;

import howest.groep14.game.actor.EnemyActor;
import howest.groep14.game.actor.SpriteActor;

public class DamagePlayersOnContact extends DamageOnContact {

    public DamagePlayersOnContact(SpriteActor owner, int damage, int selfDamage) {
        super(owner, damage, selfDamage);
    }

    public void checkCollisions(float delta) {
        checkPlayerCollisions(delta);
        checkEnemyCollisions(delta);
        checkCollisionCache();
    }

    @Override
    public CollisionBehavior copy(SpriteActor newOwner) {
        return new DamagePlayersOnContact(newOwner, damage, selfDamage);
    }



    @Override
    protected void damageEnemy(EnemyActor enemy) {
        enemy.collide(owner);
    }
}
