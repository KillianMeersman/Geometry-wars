package howest.groep14.game.actor.movement;

import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import howest.groep14.game.actor.SpriteActor;
import howest.groep14.game.actor.health.HealthBehavior;

class SnakeSegment extends MovementBehavior implements ISnake {
    private ISnake previousSegment;
    private float lastX = 0, lastY = 0;

    SnakeSegment(SpriteActor owner, final ISnake previousSegment) {
        super(owner);
        this.previousSegment = previousSegment;
        owner.setHealthBehavior(new HealthBehavior(owner) {
            @Override
            public void damage(int damage) {
                previousSegment.damage(1);
                owner.addAction(Actions.removeActor());
            }

            @Override
            public HealthBehavior copy(SpriteActor newOwner) {
                return null;
            }

            @Override
            public String toString() {
                return null;
            }
        });
    }

    @Override
    public MovementBehavior copy(SpriteActor newOwner) {
        return null;
    }

    @Override
    public void move(float delta) {
        owner.updatePositionAbsolute(previousSegment.getLastX(), previousSegment.getLastY(), false);
        this.lastX = previousSegment.getLastX();
        this.lastY = previousSegment.getLastY();
    }

    @Override
    public float getLastX() {
        return lastX;
    }

    @Override
    public float getLastY() {
        return lastY;
    }

    @Override
    public void damage(int damage) {
        previousSegment.damage(damage);
    }
}
