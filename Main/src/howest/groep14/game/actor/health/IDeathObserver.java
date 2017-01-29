package howest.groep14.game.actor.health;

import howest.groep14.game.actor.SpriteActor;

public interface IDeathObserver {
    void actorDeath(SpriteActor actor);
}
