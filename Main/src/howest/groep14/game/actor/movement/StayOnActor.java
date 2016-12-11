package howest.groep14.game.actor.movement;

import howest.groep14.game.actor.SpriteActor;

public class StayOnActor extends MovementBehavior {
    private SpriteActor target;

    public StayOnActor(SpriteActor owner, SpriteActor target) {
        super(owner);
        this.target = target;
    }

    @Override
    public MovementBehavior copy(SpriteActor newOwner) {
        return null;
    }

    @Override
    public void move(float delta) {
        owner.setPosition(target.getX(), target.getY());
    }
}
