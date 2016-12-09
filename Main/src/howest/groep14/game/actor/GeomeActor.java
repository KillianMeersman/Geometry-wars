package howest.groep14.game.actor;

import com.badlogic.gdx.graphics.g2d.Sprite;
import howest.groep14.game.CustomUtils;
import howest.groep14.game.GameStage;

public class GeomeActor extends SpriteActor {
    public GeomeActor(GameStage stage, Sprite sprite) {
        super(stage, sprite);
    }

    public void act(float delta) {
        for (PlayerActor player : stage.getPlayers()) {
            if (CustomUtils.isColliding(player, this)) {
                player.updateScore(1);
                this.remove();
            }
        }
    }
}
