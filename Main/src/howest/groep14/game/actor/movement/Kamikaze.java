package howest.groep14.game.actor.movement;

import howest.groep14.game.CustomUtils;
import howest.groep14.game.actor.SpriteActor;

public class Kamikaze extends MovementBehavior {
    private SpriteActor target;
    private float speed;
    private final boolean CHECK_BOUNDS;

    public Kamikaze(SpriteActor owner, SpriteActor target, float speed, boolean checkBounds) {
        super(owner);
        this.target = target;
        this.speed = speed;
        this.CHECK_BOUNDS = checkBounds;
    }

    @Override
    public MovementBehavior copy(SpriteActor newOwner) {
        return null;
    }

    @Override
    public void move(float delta) {
        float angleToFace = CustomUtils.getAngleToFace(owner.getX(), owner.getY(), target.getX(), target.getY());
        owner.setRotation(angleToFace); // Face target
        owner.updatePositionForward(speed, CHECK_BOUNDS); // Go forward
    }

    public SpriteActor getTarget() {
        return target;
    }

    public void setTarget(SpriteActor target) {
        this.target = target;
    }

    @Override
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public float getSpeed() {
        return speed;
    }
}
