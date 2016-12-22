package howest.groep14.game.actor.health;

import howest.groep14.game.actor.SpriteActor;

public class StandardHealthHide extends StandardHealth {
    public StandardHealthHide(SpriteActor owner, int health) {
        super(owner, health);
    }

    @Override
    public void damage(int damage) {
        health -= damage;
        if (health < 1) {
            owner.setVisible(false);
        }
    }
}
