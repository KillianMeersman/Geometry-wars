package howest.groep14.game.actor.collision;

import howest.groep14.game.actor.IGeomeCollector;
import howest.groep14.game.actor.SpriteActor;

public class CollectGeomes extends DamageEnemyActor {
    private IGeomeCollector IGeomeCollector;

    public CollectGeomes(SpriteActor owner, IGeomeCollector IGeomeCollector) {
        super(owner, 1, 1);
        this.IGeomeCollector = IGeomeCollector;
    }

    public CollectGeomes(SpriteActor owner, int damage, int selfDamage, IGeomeCollector IGeomeCollector) {
        super(owner, damage, selfDamage);
        this.IGeomeCollector = IGeomeCollector;
    }

    @Override
    public CollisionBehavior copy(SpriteActor newOwner) {
        return new CollectGeomes(newOwner, this.IGeomeCollector);
    }

    @Override
    public void checkCollisions(float delta) {
        checkGeomeCollisions(delta);
        checkCollisionCache();
    }
}
