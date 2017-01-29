package howest.groep14.game.actor.attack;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import howest.groep14.game.SettingsRepository;
import howest.groep14.game.SpriteRepository;
import howest.groep14.game.actor.IProjectileObserver;
import howest.groep14.game.actor.ProjectileActor;
import howest.groep14.game.actor.SpriteActor;

public class FireInDirection extends AttackBehavior {
    protected IProjectileObserver observer;
    protected int ROUNDS_PER_SECOND = 15;
    protected float lastDelta = 0f;
    protected Sound shootSound = Gdx.audio.newSound(Gdx.files.internal("laser.mp3"));

    public FireInDirection(IProjectileObserver owner) {
        super(owner.getOwner());
        this.observer = owner;
        this.lastDelta = 1f / ROUNDS_PER_SECOND;
    }

    @Override
    public AttackBehavior copy(SpriteActor newOwner) {
        FireInDirection copy = new FireInDirection(observer);
        copy.target = target;
        copy.ROUNDS_PER_SECOND = ROUNDS_PER_SECOND;
        copy.lastDelta = lastDelta;
        return copy;
    }

    protected boolean isCooldownOver() {
        return lastDelta > 1f / ROUNDS_PER_SECOND;
    }

    @Override
    public void engage(float delta) {
        if (isCooldownOver()) { // check if not over rate of fire (delta is the time since last frame)
            fireProjectile(owner.getRotation(), true);
            lastDelta = 0;
        } else {
            lastDelta += delta; // add time since last frame to lastDelta)
        }
    }

    protected void fireProjectile(float direction, boolean playSound) {
        ProjectileActor projectile = new ProjectileActor(owner.getStage(), SpriteRepository.getProjectile(), observer, direction);
        projectile.setScale(0.1f * SettingsRepository.getInstance().getActorScale());
        owner.getStage().addProjectile(projectile);
        if (playSound) {
            shootSound.play(0.06f);
        }
    }

    public int getROUNDS_PER_SECOND() {
        return ROUNDS_PER_SECOND;
    }

    public void setROUNDS_PER_SECOND(int ROUNDS_PER_SECOND) {
        this.ROUNDS_PER_SECOND = ROUNDS_PER_SECOND;
    }
}
