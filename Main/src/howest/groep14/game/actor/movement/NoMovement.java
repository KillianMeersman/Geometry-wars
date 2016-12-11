package howest.groep14.game.actor.movement;

import howest.groep14.game.actor.SpriteActor;

public class NoMovement extends MovementBehavior {

    public NoMovement(SpriteActor owner) {
        super(owner);
    }

    @Override
    public MovementBehavior copy(SpriteActor newOwner) {
        return null;
    }

    @Override
    public void move(float delta) {

    }
}
