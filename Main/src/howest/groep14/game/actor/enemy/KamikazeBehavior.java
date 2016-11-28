package howest.groep14.game.actor.enemy;

import howest.groep14.game.CustomUtils;
import howest.groep14.game.actor.PlayerActor;
import howest.groep14.game.actor.SpriteActor;

public class KamikazeBehavior implements IEnemyBehavior {   // The enemy goes towards the player
    final float SPEED;

    SpriteActor target;
    final SpriteActor owner;

    public KamikazeBehavior(SpriteActor actor ,SpriteActor target, float speed) {
        this.owner = actor;
        this.target = target;
        this.SPEED = speed;
    }

    public SpriteActor getTarget() {
        return target;
    }

    public void setTarget(PlayerActor target) {
        this.target = target;
    }

    @Override
    public void act() {
        float angleToFace = CustomUtils.getAngleToFace(owner.getX(), owner.getY(), target.getX(), target.getY());
        owner.setRotation(angleToFace); // Face target
        owner.updatePositionForward(SPEED); // Go forward
    }
}
