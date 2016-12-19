package howest.groep14.game.actor.movement;

import howest.groep14.game.actor.GeomeActor;
import howest.groep14.game.actor.SpriteActor;

public class StayAroundActorCollectGeomes extends StayAroundActor {
    private GeomeActor collectionTarget;

    public StayAroundActorCollectGeomes(SpriteActor owner, SpriteActor target, int min_distance, int max_distance, int speed) {
        super(owner, target, min_distance, max_distance, speed);
    }

    @Override
    public void move(float delta) {
        float maxX = 250 - Math.abs(target.getX() - owner.getX());
        float maxY = 250 - Math.abs(target.getY() - owner.getY());
        if (collectionTarget != null) {
            float distanceX = owner.getX() - collectionTarget.getX();
            float distanceY = owner.getY() - collectionTarget.getY();
            if (Math.abs(distanceX) < maxX && Math.abs(distanceY) < maxY) {
                owner.updatePositionAbsolute(distanceX / 5, distanceY / 5, false);
            }
        } else {
            for (GeomeActor geome : owner.getStage().getGeomes()) {
                float distanceX = owner.getX() - geome.getX();
                float distanceY = owner.getY() - geome.getY();
                if (Math.abs(distanceX) < maxX && Math.abs(distanceY) < maxY) {
                    target = geome;
                }
            }
        }
        super.move(delta);
    }
}
