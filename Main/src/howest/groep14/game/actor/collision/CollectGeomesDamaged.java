package howest.groep14.game.actor.collision;

import howest.groep14.game.actor.IGeomeCollector;
import howest.groep14.game.actor.GeomeActor;
import howest.groep14.game.actor.SpriteActor;

public class CollectGeomesDamaged extends DamageOnContact {
    IGeomeCollector geomeCollector;

    public CollectGeomesDamaged(SpriteActor owner, int damage, int selfDamage, IGeomeCollector geomeCollector) {
        super(owner, damage, selfDamage);
        this.geomeCollector = geomeCollector;
    }

    @Override
    public void checkCollisions(float delta) {
        checkEnemyCollisions(delta);
        checkGeomeCollisions(delta);
        checkCollisionCache();
    }

    @Override
    protected void damageGeome(GeomeActor geome) {
        geome.damage(999);
        geomeCollector.updateScore(geome.getScoreAmount());
    }
}
