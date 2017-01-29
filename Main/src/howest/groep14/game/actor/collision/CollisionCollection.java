package howest.groep14.game.actor.collision;

import java.util.ArrayList;

public class CollisionCollection {
    private ArrayList<CollisionBehavior> behaviors;

    public void addBehavior(CollisionBehavior behavior) {

    }

    private boolean hasBehavior(CollisionBehavior newBehavior) {
        for (CollisionBehavior behavior : behaviors) {
        }
        return false;
    }

    public ArrayList<CollisionBehavior> getBehaviors() {
        return behaviors;
    }
}
