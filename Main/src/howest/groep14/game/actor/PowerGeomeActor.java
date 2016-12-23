package howest.groep14.game.actor;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import howest.groep14.game.GameStage;
import howest.groep14.game.actor.health.StandardHealthHide;
import howest.groep14.game.powers.PowerBehavior;

public class PowerGeomeActor extends GeomeActor {
    private PowerBehavior powerBehavior;
    private float totalPowerDelta = 0f;
    private boolean powerActive;
    private SpriteActor powerTarget;
    private POWER powerFlag;

    public PowerGeomeActor(GameStage stage, Sprite sprite, int scoreAmount, float lifeTime, PowerBehavior powerBehavior, POWER powerFlag) {
        super(stage, sprite, scoreAmount, lifeTime);
        this.healthBehavior = new StandardHealthHide(this, 1);
        this.powerBehavior = powerBehavior;
        this.powerFlag = powerFlag;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (powerActive && totalPowerDelta < powerBehavior.getDuration()) {
            powerBehavior.doPower(powerTarget);
            totalPowerDelta += delta;
        } else if (powerActive || powerBehavior.getDuration() == 0) {
            powerBehavior.endPower(powerTarget);
            powerActive = false;
            this.addAction(Actions.removeActor());
        }
    }

    @Override
    public void collide(SpriteActor victim) {
        if (this.isVisible()) {
            powerBehavior.startPower(victim);
            powerTarget = victim;
            powerActive = true;
        }
    }

    public POWER getPowerFlag() {
        return powerFlag;
    }

    public PowerBehavior getPowerBehavior() {
        return powerBehavior;
    }

    public enum POWER {
        ARMORED_ENEMIES,
        CHANGE_TIME,
        DUAL_FIRE,
        EXTRA_LIFE,
        SHIELD
    }
}
