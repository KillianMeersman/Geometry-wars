package howest.groep14.game.actor.actions;

import howest.groep14.game.actor.SpriteActor;

public abstract class ActorDecorator extends SpriteActor {
    protected SpriteActor owner;

    public ActorDecorator(SpriteActor owner) {
        super(owner.getStage(), owner.getSprite(), owner.getMovementBehavior());
        this.owner = owner;
    }

    public abstract void act(float delta);

    @Override
    public void setPosition(float x, float y) {
        if (owner != null) {
            owner.setPosition(x, y);
        }
    }

    @Override
    public float getX() {
        if (owner != null) {
            return owner.getX();
        }
        return 1;
    }

    @Override
    public float getY() {
        if (owner != null) {
            return owner.getY();
        } return 1;
    }
}
