package howest.groep14.game.actor.attack;

import howest.groep14.game.actor.SpriteActor;

public class NoAttack extends AttackBehavior {

    public NoAttack(SpriteActor owner) {
        super(owner);
    }

    @Override
    public AttackBehavior copy(SpriteActor newOwner) {
        return new NoAttack(newOwner);
    }

    @Override
    public void engage(float delta) {

    }
}
