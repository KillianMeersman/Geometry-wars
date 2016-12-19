package howest.groep14.game.actor.collision;

import howest.groep14.game.CustomUtils;
import howest.groep14.game.actor.EnemyActor;
import howest.groep14.game.actor.GeomeActor;
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
        checkEnemyCollisions(delta);
        checkPlayerCollisions(delta);
    }

    @Override
    protected void damageEnemy(EnemyActor enemy) {
        enemy.collide(owner);
        enemy.damage(damage);
    }

    @Override
    protected void damagePlayer(PlayerActor player) {
        player.collide(owner);
        player.damage(damage);
    }

    @Override
    protected void damageGeome(GeomeActor geome) {
        geome.damage(damage);
    }
}
