package howest.groep14.game.actor.projectile;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import howest.groep14.game.GameStage;

public class ExplodeDestroyEnemies implements IProjectileCollisionBehavior {
    private ProjectileActor owner;
    private INotifyProjectileEvents observer;
    private final float LIFE_TIME;
    private float totalDelta = 0;

    public ExplodeDestroyEnemies(ProjectileActor owner, float fuse) {
        this.owner = owner;
        this.LIFE_TIME = fuse;
    }

    public ExplodeDestroyEnemies(ProjectileActor owner, float fuse, INotifyProjectileEvents observer) {
        this(owner, fuse);
        this.observer = observer;
    }

    @Override
    public boolean checkCollisions(float delta) {
        GameStage stage = owner.getStage();
        if (totalDelta >= LIFE_TIME) {
            explode(stage);
            return true;
        } else {
            totalDelta += delta;
            return false;
        }
    }

    private void explode(GameStage stage) {
        Texture texture = new Texture("Desktop/Assets/greyProjectile.png");
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Sprite projectileSprite = new Sprite(texture);
        projectileSprite.setScale(0.1f);
        for (int i = 0; i < 35; i += 1) {
            ProjectileActor actor = new ProjectileActor(owner.getStage(), projectileSprite, owner.getX(), owner.getY(), i * 10, owner, observer);
            stage.addProjectile(actor);
        }
        owner.remove();
    }
}
