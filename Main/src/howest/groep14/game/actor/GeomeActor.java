package howest.groep14.game.actor;

import com.badlogic.gdx.graphics.g2d.Sprite;
import howest.groep14.game.GameStage;
import howest.groep14.game.actor.health.StandardHealth;

public class GeomeActor extends SpriteActor {
    private final int scoreAmount;
    private final float lifeTime;
    private float time;

    public GeomeActor(GameStage stage, Sprite sprite, int scoreAmount, float lifeTime) {
        super(stage, sprite);
        this.healthBehavior = new StandardHealth(this, 1);
        this.scoreAmount = scoreAmount;
        this.lifeTime = lifeTime;
    }

    public void act(float delta) {
        super.act(delta);
        if (time >= lifeTime) {
            this.remove();
        } else {
            time += delta;
        }
        /*
        for (PlayerActor player : stage.getPlayers()) {
            if (player.getDrone() instanceof IGeomeCollector) {
                if (CustomUtils.isColliding(player.getDrone(), this)) {
                    player.updateScore(scoreAmount);
                    this.remove();
                }
            }
            if (CustomUtils.isColliding(player, this)) {
                player.updateScore(scoreAmount);
                this.remove();
            }
        }
        */
    }

    @Override
    public boolean remove() {
        if (!removed) {
            removed = true;
            stage.removeGeome(this);
            return super.remove();
        }
        return false;
    }

    public int getScoreAmount() {
        return scoreAmount;
    }
}
