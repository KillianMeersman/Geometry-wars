package howest.groep14.game.actor.drone;

import com.badlogic.gdx.graphics.g2d.Sprite;
import howest.groep14.game.GameStage;
import howest.groep14.game.actor.PlayerActor;
import howest.groep14.game.actor.SpriteActor;
import howest.groep14.game.actor.movement.CircleActor;

public class DroneActor extends SpriteActor {
    private PlayerActor player;

    public DroneActor(GameStage stage, Sprite sprite, PlayerActor player) {
        super(stage, sprite);
        this.player = player;
        this.movementBehavior = new CircleActor(this, player, 1f);
    }

    public void act(float delta) {
        movementBehavior.move(delta);
    }
}