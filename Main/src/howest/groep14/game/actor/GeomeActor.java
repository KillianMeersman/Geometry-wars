package howest.groep14.game.actor;

import com.badlogic.gdx.graphics.g2d.Sprite;
import howest.groep14.game.CustomUtils;
import howest.groep14.game.GameStage;

public class GeomeActor extends SpriteActor {
    private final int scoreAmount;

    public GeomeActor(GameStage stage, Sprite sprite, int scoreAmount) {
        super(stage, sprite);
        this.scoreAmount = scoreAmount;
    }

    public void act(float delta) {
        for (PlayerActor player : stage.getPlayers()) {
            if (CustomUtils.isColliding(player, this)) {
                player.updateScore(scoreAmount);
                this.remove();
            }
        }
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
