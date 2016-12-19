package howest.groep14.game.actor.movement;

import howest.groep14.game.CustomUtils;
import howest.groep14.game.actor.SpriteActor;

public class StraightLine extends MovementBehavior {
    private float xMovement, yMovement, speed, rotation;
    private int bounces;

    public StraightLine(SpriteActor owner, float rotation, float speed, int bounces) {
        super(owner);
        this.rotation = rotation;
        this.speed = speed;
        this.bounces = bounces;
        calcMovement(rotation);
    }

    @Override
    public MovementBehavior copy(SpriteActor newOwner) {
        return new StraightLine(owner, rotation, speed, bounces);
    }

    @Override
    public void move(float delta) {
        if (CustomUtils.outOfBoundsX(owner.getX(), owner.getWidth(), speed)) {
            bounce();
        }
        else if (CustomUtils.outOfBoundsY(owner.getY(), owner.getHeight(), speed)) {
            bounce();
        }
        owner.updatePositionAbsolute(xMovement, yMovement, checkBounds);
    }

    @Override
    public void setPosition(float x, float y) {
        calcMovement(rotation);
    }

    @Override
    public void setRotation(float rotation) {
        this.rotation = rotation;
        calcMovement(rotation);
    }

    public int getBounces() {
        return bounces;
    }

    public void setBounces(int bounces) {
        this.bounces = bounces;
    }

    public float getSpeed() {
        return speed;
    }

    public float getRotation() {
        return rotation;
    }

    private void bounce() {
        if (bounces > 0) {
            if (CustomUtils.booleanRandom()) {
                this.rotation = owner.getRotation() + 130;
                owner.setRotation(rotation);
            } else {
                this.rotation = owner.getRotation() - 130;
                owner.setRotation(rotation);
            }
            bounces--;
            calcMovement(rotation); // we have rotated, so we need to recalculate x and y movement per turn
        } else {
            owner.outOfBounds();
            owner.remove();
        }
    }

    private void calcMovement(float rotation) {
        float radians = (float) Math.toRadians(rotation);
        this.xMovement = (float) Math.cos(radians) * speed;
        this.yMovement = (float) Math.sin(radians) * speed;
    }
}
