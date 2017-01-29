package howest.groep14.game.actor.collision;

import howest.groep14.game.actor.EnemyActor;
import howest.groep14.game.actor.GeomeActor;
import howest.groep14.game.actor.PlayerActor;
import howest.groep14.game.actor.SpriteActor;

public class DamageOnContact extends CollisionBehavior {
    protected static byte typeCode = 1;
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
        checkEnemyCollisions(delta);
        checkPlayerCollisions(delta);
        checkCollisionCache();
    }

    @Override
    protected void contactEnemy(EnemyActor enemy) {
        enemy.collide(owner);
        enemy.damage(damage);
        owner.damage(selfDamage);
    }

    @Override
    protected void contactPlayer(PlayerActor player) {
        player.collide(owner);
        player.damage(damage);
        owner.damage(selfDamage);
    }

    @Override
    protected void contactGeome(GeomeActor geome) {
        geome.collide(owner);
        geome.damage(damage);
        owner.damage(selfDamage);
    }
}
