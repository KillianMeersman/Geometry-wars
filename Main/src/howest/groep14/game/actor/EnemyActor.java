package howest.groep14.game.actor;

import com.badlogic.gdx.graphics.g2d.Sprite;
import howest.groep14.game.GameStage;
import howest.groep14.game.actor.attack.AttackBehavior;
import howest.groep14.game.actor.collision.CollisionBehavior;
import howest.groep14.game.actor.collision.DamagePlayersOnContact;
import howest.groep14.game.actor.health.HealthBehavior;
import howest.groep14.game.actor.movement.MovementBehavior;

public class EnemyActor extends SpriteActor {
    private ENEMY_TYPE type;

    public EnemyActor(GameStage stage, Sprite sprite, ENEMY_TYPE type) {
        super(stage, sprite);
        this.type = type;
        this.collisionBehavior = new DamagePlayersOnContact(this, 1, 0);
    }

    public EnemyActor(GameStage stage, Sprite sprite, ENEMY_TYPE type, MovementBehavior movementBehavior, AttackBehavior attackBehavior,
                      HealthBehavior healthBehavior, CollisionBehavior collisionBehavior) {
        super(stage, sprite, movementBehavior, attackBehavior, healthBehavior, collisionBehavior);
        this.type = type;
    }

    @Override
    public boolean remove() {
        if (!removed) {
            removed = true;
            stage.removeEnemy(this);
            return super.remove();
        }
        return false;
    }

    public ENEMY_TYPE getTypeCode() {
        return type;
    }

    public enum ENEMY_TYPE {
        CUBE,
        CIRCLE,
        SNAKE
    }
}