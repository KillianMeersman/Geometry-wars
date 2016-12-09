package howest.groep14.game.actor.actions;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import howest.groep14.game.CustomUtils;
import howest.groep14.game.GameStage;
import howest.groep14.game.actor.SpriteActor;
import howest.groep14.game.actor.projectile.DestroyPlayersBehavior;
import howest.groep14.game.actor.projectile.INotifyProjectileEvents;
import howest.groep14.game.actor.projectile.ProjectileActor;

public class SnipeAction extends ActorDecorator {
    private SpriteActor target;
    private INotifyProjectileEvents projectileObserver;
    private final int ROUNDS_PER_SECOND;
    private float lastDelta = 0;

    public SnipeAction(SpriteActor owner, SpriteActor target, int roundsPerSecond) {
        super(owner);
        this.target = target;
        this.ROUNDS_PER_SECOND = roundsPerSecond;
    }

    public SnipeAction(SpriteActor owner, SpriteActor target, int roundsPerSecond, INotifyProjectileEvents projectileObserver) {
        this(owner, target, roundsPerSecond);
        this.projectileObserver = projectileObserver;
    }

    @Override
    public void act(float delta) {
        if (lastDelta > 1f / ROUNDS_PER_SECOND) {
            fireProjectile();
            lastDelta = 0;
        } else {
            lastDelta += Gdx.graphics.getDeltaTime();
        }
        owner.act(delta);
    }

    private void fireProjectile() {
        GameStage stage = owner.getStage();
        Texture texture = new Texture("Desktop/Assets/greyProjectile.png");
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Sprite projectileSprite = new Sprite(texture);
        ProjectileActor projectileActor = new ProjectileActor(stage, projectileSprite, owner.getX(), owner.getY(), CustomUtils.getAngleToFace(owner, target), owner);
        projectileActor.setCollisionBehavior(new DestroyPlayersBehavior(projectileActor));
        projectileActor.setSpeed(5);
        projectileActor.setScale(0.1f);
        stage.addProjectile(projectileActor);
    }
}
