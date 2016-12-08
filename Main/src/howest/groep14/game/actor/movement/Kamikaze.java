package howest.groep14.game.actor.movement;

import howest.groep14.game.actor.SpriteActor;

public class Kamikaze extends MovementBehavior {
    private SpriteActor target;

    public Kamikaze(SpriteActor owner, SpriteActor target) {
        super(owner);
        this.target = target;
    }

    @Override
    public void move(float delta) {

    }
}
