package howest.groep14.game.actor.movement;

import howest.groep14.game.actor.SpriteActor;
import howest.groep14.game.actor.health.HealthBehavior;

class SnakeSegment extends MovementBehavior implements ISnake {
    private ISnake previousSegment;

    SnakeSegment(SpriteActor owner, final ISnake previousSegment) {
        super(owner);
        this.previousSegment = previousSegment;
        owner.setHealthBehavior(new HealthBehavior(owner) {
            @Override
            public void damage(int damage) {
                previousSegment.damage(1);
                owner.remove();
            }

            @Override
            public HealthBehavior copy(SpriteActor newOwner) {
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
        owner.updatePositionAbsolute(previousSegment.getLastX() / 2, previousSegment.getLastY() / 2, false);
    }

    @Override
    public float getLastX() {
        return previousSegment.getLastX();
    }

    @Override
    public float getLastY() {
        return previousSegment.getLastY();
    }

    @Override
    public void damage(int damage) {
        previousSegment.damage(damage);
    }
}
