package com.example.mygame.enemy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.example.mygame.SpriteActor;

public class EnemyActor extends SpriteActor {
    IEnemyBehavior behavior;    // Stategy pattern

    public EnemyActor(Sprite sprite) {
        this.setSprite(sprite);
        this.behavior = new NoBehavior();
    }

    public EnemyActor(Sprite sprite, float scale) {
        this(sprite);
        this.setScale(scale);
    }

    public IEnemyBehavior getBehavior() {
        return behavior;
    }

    public void setBehavior(IEnemyBehavior behavior) {
        this.behavior = behavior;
    }

    public void act(float delta) {
        behavior.act();
    }
}
