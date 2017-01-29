package howest.groep14.game.actor.collision;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import howest.groep14.game.SettingsRepository;
import howest.groep14.game.actor.EnemyActor;
import howest.groep14.game.actor.GeomeActor;
import howest.groep14.game.actor.PlayerActor;
import howest.groep14.game.actor.SpriteActor;

import java.util.ArrayList;
import java.util.List;

public abstract class CollisionBehavior {
    protected static byte typeCode = 0;
    protected SpriteActor owner;
    protected List<SpriteActor> collisionCache = new ArrayList<SpriteActor>();  // Tracks ongoing collisions to prevent one collision from triggering multiple collision events

    public CollisionBehavior(SpriteActor owner) {
        this.owner = owner;
    }

    public abstract CollisionBehavior copy(SpriteActor newOwner);

    public abstract void checkCollisions(float delta);

    protected void checkCollisionCache() {
        for (int i = 0; i < collisionCache.size(); i++) {   // Check all cache items until one can be removed (spread some load over frames)
            if (!isColliding(owner, collisionCache.get(i))) {
                collisionCache.remove(i);
                break;
            }
        }
    }

    public void collide(SpriteActor collider) {
        collisionCache.add(collider);
    }

    protected void checkPlayerCollisions(float delta) {
        for (PlayerActor player : owner.getStage().getPlayers()) {
            if (!collisionCache.contains(player) && isColliding(owner, player)) {
                collisionCache.add(player);
                contactPlayer(player);
            }
        }
    }

    protected abstract void contactPlayer(PlayerActor player);

    protected void checkEnemyCollisions(float delta) {
        for (EnemyActor enemy : owner.getStage().getEnemies()) {
            if (!collisionCache.contains(enemy) && isColliding(owner, enemy)) {
                collisionCache.add(enemy);
                contactEnemy(enemy);
            }
        }
    }

    protected abstract void contactEnemy(EnemyActor enemy);

    protected void checkGeomeCollisions(float delta) {
        for (GeomeActor geome : owner.getStage().getGeomes()) {
            if (!collisionCache.contains(geome) && isColliding(owner, geome)) {
                collisionCache.add(geome);
                owner.collide(geome);
                geome.collide(owner);
                contactGeome(geome);
            }
        }
    }

    protected abstract void contactGeome(GeomeActor geome);

    public static boolean isColliding(Actor actorA, Actor actorB) {
        Vector2 positionA = new Vector2(actorA.getX(), actorA.getY());
        Vector2 positionB = new Vector2(actorB.getX(), actorB.getY());
        boolean x = false;
        boolean y = false;
        if (Math.abs(positionA.x - positionB.x) < 30 * SettingsRepository.getInstance().getActorScale()) {
            x = true;
        }
        if (Math.abs(positionA.y - positionB.y) < 30 * SettingsRepository.getInstance().getActorScale()) {
            y = true;
        }
        return x && y;
    }
}
