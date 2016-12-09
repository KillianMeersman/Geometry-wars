package howest.groep14.game.actor.enemy;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import howest.groep14.game.GameStage;
import howest.groep14.game.actor.SpriteActor;

public class EnemyActor extends SpriteActor {
    private IEnemyBehavior behavior; // Stategy pattern

    public EnemyActor(GameStage stage, Sprite sprite) {
        super(stage, sprite);
        this.setSprite(sprite);
        this.behavior = new NoBehavior();
        setBounds(sprite.getX(), sprite.getY(), sprite.getWidth() * sprite.getScaleX(), sprite.getHeight() * sprite.getScaleY());
    }

    public EnemyActor(GameStage stage, Sprite sprite, float scale) {
        this(stage, sprite);
        this.setScale(scale);
        float width = sprite.getWidth() * sprite.getScaleX();
        float height = sprite.getWidth() * sprite.getScaleX();
        setBounds(sprite.getX(), sprite.getY(), width, height);
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
    }
}