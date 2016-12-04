package howest.groep14.game.actor.enemy;

import com.badlogic.gdx.Gdx;
import howest.groep14.game.CustomUtils;
import howest.groep14.game.GameStage;
import howest.groep14.game.actor.projectile.DestroyPlayersBehavior;
import howest.groep14.game.actor.projectile.ProjectileActor;
import howest.groep14.game.actor.SpriteActor;

public class SniperBehavior implements IEnemyBehavior {
    private final float ROUNDS_PER_SECOND = 1f;
    private final int ANGLE_LIMIT = 20;
    private final int SPEED = 5;
    private final SpriteActor owner;
    private final SpriteActor target;
    private float lastDelta = 0;

    public SniperBehavior(SpriteActor owner, SpriteActor target) {
        this.owner = owner;
        this.target = target;
    }

    @Override
    public void act() {
        if (CustomUtils.booleanRandom(500)) {
            int angle = CustomUtils.intRandom(360);
            float angleToFace = CustomUtils.getAngleToFace(owner, target);
            while (Math.abs(angleToFace - angle) < ANGLE_LIMIT) {
                angle = CustomUtils.intRandom(360);
            }
            owner.setRotation(angle);
            owner.updatePositionForward(SPEED);
        }
        if (lastDelta > 1f / ROUNDS_PER_SECOND) {
            fireProjectile();
            lastDelta = 0;
        } else {
            lastDelta += Gdx.graphics.getDeltaTime();
        }
    }

    private void fireProjectile() {
        GameStage stage = (GameStage) owner.getStage();
        ProjectileActor projectileActor = new ProjectileActor(stage, owner.getX(), owner.getY(), CustomUtils.getAngleToFace(owner, target), owner);
        projectileActor.setCollisionBehavior(new DestroyPlayersBehavior(projectileActor));
        projectileActor.setSpeed(5);
        stage.addProjectile(projectileActor);
    }
}
