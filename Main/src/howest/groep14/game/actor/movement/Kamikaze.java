package howest.groep14.game.actor.movement;

import howest.groep14.game.CustomUtils;
import howest.groep14.game.actor.SpriteActor;

public class Kamikaze extends MovementBehavior {
    private SpriteActor target;
    private final float SPEED;
    private final boolean CHECK_BOUNDS;

    public Kamikaze(SpriteActor owner, SpriteActor target, float speed, boolean checkBounds) {
        super(owner);
        this.target = target;
        this.SPEED = speed;
        this.CHECK_BOUNDS = checkBounds;
    }

    @Override
    public void move(float delta) {
        float angleToFace = CustomUtils.getAngleToFace(owner.getX(), owner.getY(), target.getX(), target.getY());
        owner.setRotation(angleToFace); // Face target
        owner.updatePositionForward(SPEED, CHECK_BOUNDS); // Go forward
    }

    public SpriteActor getTarget() {
        return target;
    }

    public void setTarget(SpriteActor target) {
        this.target = target;
    }
}
