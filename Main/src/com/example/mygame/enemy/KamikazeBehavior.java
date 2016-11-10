package com.example.mygame.enemy;

import com.badlogic.gdx.math.Vector2;
import com.example.mygame.CustomUtils;
import com.example.mygame.PlayerActor;
import com.example.mygame.SpriteActor;

public class KamikazeBehavior implements IEnemyBehavior {   // The enemy goes towards the player
    final float SPEED;

    SpriteActor target;
    SpriteActor actor;

    public KamikazeBehavior(SpriteActor actor ,SpriteActor target, float speed) {
        this.actor = actor;
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
        float angleToFace = CustomUtils.getAngleToFace(actor.getX(), actor.getY(), target.getX(), target.getY());
        actor.setRotation(angleToFace); // Face target
        Vector2 newPos = CustomUtils.getForwardPosition(angleToFace, 2);
        actor.updatePositionForward(SPEED); // Go forward
    }
}
