package com.example.mygame.enemy;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.utils.Cullable;
import com.example.mygame.CustomUtils;
import com.example.mygame.PlayerActor;
import com.example.mygame.SpriteActor;

public class KamikazeBehavior implements IEnemyBehavior {
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
        actor.setRotation(angleToFace);
        Vector2 newPos = CustomUtils.getForwardPosition(angleToFace, 2);
        actor.updatePosition(SPEED);
    }
}
