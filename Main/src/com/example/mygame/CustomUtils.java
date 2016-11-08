package com.example.mygame;

public class CustomUtils {
    public static float to180(float angle) {
        if (angle > 180) {
            angle = -180 + (angle - 180);
        }
        return angle;
    }

    public static boolean outOfBoundsX(float x, float sizeX, float update) {
        return (x + update > GeometryWars.WIDTH - sizeX) || (x + update < 0);
    }

    public static boolean outOfBoundsY(float y, float sizeY, float update) {
        return (y + update > GeometryWars.HEIGHT - sizeY) || (y + update < 0);
    }
}
