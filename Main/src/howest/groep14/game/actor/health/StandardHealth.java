package howest.groep14.game.actor.health;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import howest.groep14.game.actor.SpriteActor;

public class StandardHealth extends HealthBehavior {
    protected int health;

    public StandardHealth(SpriteActor owner, int health) {
        super(owner);
        this.health = health;
    }

    @Override
    public void damage(int damage) {
        health -= damage;
        if (health < 1) {
            owner.addAction(Actions.removeActor());
        }
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void updateHealth(int update, boolean canDie) {
        if (!canDie && health + update <= 0) {
            update -= ((health + update) + 1);
        }
        this.health = health + update;
    }

    @Override
    public HealthBehavior copy(SpriteActor newOwner) {
        return new StandardHealth(newOwner, health);
    }

    @Override
    public String toString() {
        return health + " LIVES";
    }
}
