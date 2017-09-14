package main.engine.utils;

import main.game.entity.Entity;
import org.joml.Vector3f;

public class AIUtils {

    private AIUtils() {

    }

    /** Returns the y-rotation in radians to rotate an entity towards another entity */
    public static float getFaceYRotation(Entity a, Entity b) {
        float addition = 0, multiplication = 1;
        Vector3f aPos = a.getPosition();
        Vector3f bPos = b.getPosition();

        float distanceX = aPos.x - bPos.x;
        float distanceZ = aPos.z - bPos.z;
        float hyph = (float) Math.sqrt(distanceX*distanceX + distanceZ*distanceZ);

        if (a.getPosition().z > b.getPosition().z) {
            addition = 3.14159265f;
            multiplication = -1;
        }

        return (float) (Math.asin(distanceX / hyph) * multiplication) + addition;
    }

    public static void rotateTowardsEntity(Entity entity, Entity target) {
        entity.setRotation(0, (float) Math.toDegrees(getFaceYRotation(entity, target)), 0);
    }
}
