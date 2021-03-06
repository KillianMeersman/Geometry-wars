package howest.groep14.game.actor.collision;

import howest.groep14.game.actor.IGeomeCollector;
import howest.groep14.game.actor.GeomeActor;
import howest.groep14.game.actor.SpriteActor;

public class DamageEnemiesCollectGeomes extends DamageOnContact {
    IGeomeCollector geomeCollector;

    public DamageEnemiesCollectGeomes(SpriteActor owner, int damage, int selfDamage, IGeomeCollector geomeCollector) {
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
    protected void contactGeome(GeomeActor geome) {
        super.contactGeome(geome);
        geomeCollector.updateScore(geome.getScoreAmount());
    }
}
