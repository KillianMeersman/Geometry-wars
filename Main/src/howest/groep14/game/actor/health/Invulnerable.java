package howest.groep14.game.actor.health;

import howest.groep14.game.actor.SpriteActor;

public class Invulnerable extends HealthBehavior {
    public Invulnerable(SpriteActor owner) {
        super(owner);
    }

    @Override
    public void damage(int damage) {

    }

    @Override
    public HealthBehavior copy(SpriteActor newOwner) {
        return new Invulnerable(newOwner);
    }
}
