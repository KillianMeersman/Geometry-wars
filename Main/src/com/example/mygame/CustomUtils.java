package com.example.mygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class CustomUtils {
    // Get the angle towards another object (useful for facing an enemy or following)
    public static float getAngleToFace(float posX, float posY, float tarX, float tarY) {
        float angle = MathUtils.radiansToDegrees * MathUtils.atan2(tarY - posY, tarX - posX);

        if(angle < 0){
            angle += 360;
        }
        return angle;
    }

    // Get the new position when moving forward in the direction you are facing
    public static Vector2 getForwardPosition(float angle, float distance) {    // Calculates new position when going forward
        float rotation = (float) Math.toRadians(angle);
        Vector2 newPos = new Vector2(); // Holds 2 floats
        newPos.x = (float) Math.cos(rotation) * distance; // The more you are rotated to the left or right, the bigger cos(rotation) is (between 0 and 1)
        newPos.y = (float) Math.sin(rotation) * distance;   // The more you are rotated up or down, the bigger sin(rotation) is (between 0 and 1)
        return newPos;
    }

    // Get the angle towards the mouse pointer
    public static float getAngleToMouse(float xPos, float yPos) {
        float xInput = Gdx.input.getX();
        float yInput = (Gdx.graphics.getHeight() - Gdx.input.getY());

        float angle = MathUtils.radiansToDegrees * MathUtils.atan2(yInput - yPos, xInput - xPos);

        if(angle < 0){
            angle += 360;
        }
        return angle;
    }

    // Is the object at the edge of the screen? (left & right borders)
    public static boolean outOfBoundsX(float x, float sizeX, float update) {
        return (x + update > GeometryWars.WIDTH - sizeX) || (x + update < 0);
    }

    // Is the object at the edge of the screen? (upper & lower borders)
    public static boolean outOfBoundsY(float y, float sizeY, float update) {
        return (y + update > GeometryWars.HEIGHT - sizeY) || (y + update  < 0);
    }

    public static boolean isColliding(SpriteActor actorA, SpriteActor actorB) {
        return false;
    }
}
