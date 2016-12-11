package howest.groep14.game.actor.movement;

import howest.groep14.game.actor.SpriteActor;

public class CircleActor extends MovementBehavior {
    private SpriteActor target;
    private float prevX, prevY, speed;

    public CircleActor(SpriteActor owner, SpriteActor target, float speed) {
        super(owner);
        this.target = target;
        this.speed = speed;
    }

    @Override
    public MovementBehavior copy(SpriteActor newOwner) {
        return null;
    }

    @Override
    public void move(float delta) {
        /*
        float x = ((target.getX() - target.getSprite().getWidth() / 2) + (float) Math.cos(CustomUtils.getAngleToFace(owner, target)) * 100);
        float y = ((target.getY() - target.getSprite().getHeight() / 2) + (float) Math.sin(CustomUtils.getAngleToFace(owner, target)) * 100);
        owner.setPosition(x, y);
        prevX = x;
        prevY = y;
        */
        double y = (100) * Math.sin(0.5 * 0.5 * Math.PI);
        owner.setY((float)y);
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
