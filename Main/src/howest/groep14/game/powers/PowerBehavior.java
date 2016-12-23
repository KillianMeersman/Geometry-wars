package howest.groep14.game.powers;

import howest.groep14.game.actor.SpriteActor;

public abstract class PowerBehavior {
    protected float duration = 0;

    public PowerBehavior() {}
    public PowerBehavior(float duration) {
        this.duration = duration;
    }

    public float getDuration() {
        return duration;
    }

    public abstract void startPower(SpriteActor target); // Method for start of power (for timed powers)
    public abstract void doPower(SpriteActor target);    // Intermediate method
    public abstract void endPower(SpriteActor target);  // Method for end of power

    public abstract String toString();
}
