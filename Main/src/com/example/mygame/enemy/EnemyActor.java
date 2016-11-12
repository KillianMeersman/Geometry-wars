package com.example.mygame.enemy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.example.mygame.GameStage;
import com.example.mygame.SpriteActor;

public class EnemyActor extends SpriteActor {
    private IEnemyBehavior behavior; // Stategy pattern

    public EnemyActor(GameStage stage, Sprite sprite) {
        super(stage);
        this.setSprite(sprite);
        this.behavior = new NoBehavior();
        setBounds(new Rectangle(sprite.getX(), sprite.getY(), sprite.getWidth() * sprite.getScaleX(), sprite.getHeight() * sprite.getScaleY()));
    }

    public EnemyActor(GameStage stage, Sprite sprite, float scale) {
        this(stage, sprite);
        this.sprite.setScale(scale);
        this.setScale(scale);
        setBounds(new Rectangle(sprite.getX(), sprite.getY(), sprite.getWidth() * sprite.getScaleX(), sprite.getHeight() * sprite.getScaleY()));
    }

    public IEnemyBehavior getBehavior() {
        return behavior;
    }

    public void setBehavior(IEnemyBehavior behavior) {
        this.behavior = behavior;
    }

    public void act(float delta) {
        super.act(delta);
        behavior.act();
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        /*
        ShapeRenderer ren = new ShapeRenderer();
        ren.begin(ShapeRenderer.ShapeType.Line);
        ren.rect(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight());
        ren.end();
        */
    }
}