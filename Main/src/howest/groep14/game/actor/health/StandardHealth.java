package howest.groep14.game.actor.health;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import howest.groep14.game.actor.SpriteActor;

public class StandardHealth extends HealthBehavior {
    private int health;

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

    @Override
    public HealthBehavior copy(SpriteActor newOwner) {
        return new StandardHealth(newOwner, health);
    }
}
