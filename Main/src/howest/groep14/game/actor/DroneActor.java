package howest.groep14.game.actor;

import com.badlogic.gdx.graphics.g2d.Sprite;
import howest.groep14.game.GameStage;
import howest.groep14.game.actor.collision.CollectGeomes;
import howest.groep14.game.actor.health.Invulnerable;
import howest.groep14.game.actor.movement.CircleActor;

public class DroneActor extends SpriteActor {
    private PlayerActor player;

    public DroneActor(GameStage stage, Sprite sprite, PlayerActor player) {
        super(stage, sprite);
        this.player = player;
        this.movementBehavior = new CircleActor(this, player, 1f);
        this.healthBehavior = new Invulnerable(this);
        this.collisionBehavior = new CollectGeomes(this, player);
    }

    public PlayerActor getPlayer() {
        return player;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        this.movementBehavior.move(delta);
        this.attackBehavior.engage(delta);
        this.collisionBehavior.checkCollisions(delta);
    }
}