package howest.groep14.game.actor.health;

import com.badlogic.gdx.graphics.g2d.Sprite;
import howest.groep14.game.actor.SpriteActor;
import howest.groep14.game.actor.decorators.RemoveOwnerOnNoTarget;
import howest.groep14.game.actor.movement.MovementBehavior;
import howest.groep14.game.actor.movement.StayOnActor;

public class Shield extends HealthBehavior{
    private SpriteActor shieldActor;

    public Shield(SpriteActor owner, Sprite sprite) {
        super(owner);
        this.shieldActor = new SpriteActor(owner.getStage(), sprite);
        HealthBehavior inv = new Invulnerable(shieldActor);
        shieldActor.setHealthBehavior(inv);
        MovementBehavior stay = new StayOnActor(shieldActor, owner);
        shieldActor.setMovementBehavior(stay);
        shieldActor.setScale(owner.getScaleX(), owner.getScaleY());
        owner.getStage().addActor(shieldActor);
    }

    @Override
    public void damage(int damage) {

    }

    public void remove() {
        shieldActor.remove();
    }

    @Override
    public HealthBehavior copy(SpriteActor newOwner) {
        return new Shield(newOwner, shieldActor.getSprite());
    }
}
