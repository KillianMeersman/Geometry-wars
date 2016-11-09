package com.example.mygame;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class CustomUtils {
    public static float to180(float angle) {
        if (angle > 180) {
            angle = -180 + (angle - 180);
        }
        return angle;
    }

    public static float getAngleToFace(float posX, float posY, float tarX, float tarY) {
        float angle = MathUtils.radiansToDegrees * MathUtils.atan2(tarY - posY, tarX - posX);

        if(angle < 0){
            angle += 360;
        }
        return angle;
    }

    public static Vector2 getForwardPosition(float angle, float distance) {    // Calculates new position when going forward
        float rotation = (float) Math.toRadians(angle);
        Vector2 newPos = new Vector2();
        newPos.x = (float) Math.cos(rotation) * distance;
        newPos.y = (float) Math.sin(rotation) * distance;
        return newPos;
    }

    public static boolean outOfBoundsX(float x, float sizeX, float update) {
        return (x + update > GeometryWars.WIDTH - sizeX) || (x + update < 0);
    }

    public static boolean outOfBoundsY(float y, float sizeY, float update) {
        return (y + update > GeometryWars.HEIGHT - sizeY) || (y + update < 0);
    }
}
