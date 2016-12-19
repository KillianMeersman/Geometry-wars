package howest.groep14.game.actor.movement;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import howest.groep14.game.CustomUtils;
import howest.groep14.game.actor.SpriteActor;

public class Bounce extends MovementBehavior {
    private float xMovement, yMovement, speed, rotation;
    private float lastBounce = 0;

    public Bounce(SpriteActor owner, float speed, float rotation) {
        super(owner);
        this.speed = speed;
        this.rotation = rotation;
        calcMovement(rotation);
    }

    @Override
    public MovementBehavior copy(SpriteActor newOwner) {
        return new Bounce(newOwner, speed, rotation);
    }

    @Override
    public void move(float delta) {
        if (CustomUtils.outOfBoundsX(owner.getX(), owner.getWidth(), xMovement) || CustomUtils.outOfBoundsY(owner.getY(), owner.getHeight(), yMovement)) {
            if (CustomUtils.booleanRandom()) {
                this.rotation = owner.getRotation() + 130;
                owner.setRotation(rotation);
            } else {
                this.rotation = owner.getRotation() - 130;
                owner.setRotation(rotation);
            }
            calcMovement(rotation); // we have rotated, so we need to recalculate x and y movement per turn
        }
        owner.updatePositionAbsolute(xMovement, yMovement, false);
    }

    private void bounce() {
        if (CustomUtils.booleanRandom()) {
            this.rotation = owner.getRotation() + 130;
            owner.setRotation(rotation);
        } else {
            this.rotation = owner.getRotation() - 130;
            owner.setRotation(rotation);
            calcMovement(rotation); // we have rotated, so we need to recalculate x and y movement per turn
        }
    }

    private void calcMovement(float rotation) {
        float radians = (float) Math.toRadians(rotation);
        this.xMovement = (float) Math.cos(radians) * speed;
        this.yMovement = (float) Math.sin(radians) * speed;
    }
}
