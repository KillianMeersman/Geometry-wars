package howest.groep14.game.actor.actions;

import howest.groep14.game.CustomUtils;
import howest.groep14.game.actor.PlayerActor;
import howest.groep14.game.actor.SpriteActor;

public class DamagePlayerOnContact extends ActorDecorator {
    private boolean destroySelfOnContact;
    private int damage;

    public DamagePlayerOnContact(SpriteActor owner, boolean destroySelfOnContact, int damage) {
        super(owner);
        this.destroySelfOnContact = destroySelfOnContact;
        this.damage = damage;
    }

    @Override
    public void act(float delta) {
        for (PlayerActor player : stage.getPlayers()) {
            if (CustomUtils.isColliding(owner, player)) {
                player.damage(damage);
                if (destroySelfOnContact) { owner.remove(); }
            }
        }
        owner.act(delta);
    }
}
