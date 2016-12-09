package howest.groep14.game.actor.movement;

import howest.groep14.game.actor.SpriteActor;

public class StayAroundActor extends MovementBehavior {
    private SpriteActor target;
    private int MIN_DISTANCE, MAX_DISTANCE, MAX_SPEED;
    private final float ACCEL = 0.5f;

    private float speed_x = 0, speed_y = 0;

    public StayAroundActor(SpriteActor owner, SpriteActor target, int min_distance, int max_distance, int speed) {
        super(owner);
        this.target = target;
        this.MIN_DISTANCE = min_distance;
        this.MAX_DISTANCE = max_distance;
        this.MAX_SPEED = speed;
    }

    @Override
    public void move(float delta) {
        float distanceX = owner.getX() - target.getX();
        float distanceY = owner.getY() - target.getY();

        if (distanceX >= MAX_DISTANCE) {
            speed_x -= ACCEL;
        } else if (distanceX <= -MAX_DISTANCE) {
            speed_x += ACCEL;
        } else if (Math.abs(distanceX) <= MIN_DISTANCE) {
            speed_x -= ACCEL;
        }

        if (distanceY >= MAX_DISTANCE) {
            speed_y -= ACCEL;
        } else if (distanceY <= -MAX_DISTANCE) {
            speed_y += ACCEL;
        } else if (Math.abs(distanceY) <= MIN_DISTANCE) {
            speed_y -= ACCEL;
        }


        owner.updatePositionAbsolute(speed_x, speed_y, true);

        speed_x *= 0.98f;
        speed_y *= 0.98f;
    }

    public SpriteActor getTarget() {
        return target;
    }

    public void setTarget(SpriteActor target) {
        this.target = target;
    }

    public int getMAX_DISTANCE() {
        return MAX_DISTANCE;
    }

    public void setMAX_DISTANCE(int MAX_DISTANCE) {
        this.MAX_DISTANCE = MAX_DISTANCE;
    }

    public int getMAX_SPEED() {
        return MAX_SPEED;
    }

    public void setMAX_SPEED(int MAX_SPEED) {
        this.MAX_SPEED = MAX_SPEED;
    }
}
