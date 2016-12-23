package howest.groep14.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import howest.groep14.game.actor.SpriteActor;

import java.util.Random;

public class CustomUtils {
    private static final Random random = new Random();

    public static int intRandom(int bound) {
        return random.nextInt(bound);
    }

    public static float floatRandom() {
        return random.nextFloat();
    }

    public static boolean booleanRandom(int chance) {
        return random.nextInt(chance) == 9;
    }

    public static boolean booleanRandom() {
        return random.nextBoolean();
    }

    public static float getAngleToFace(SpriteActor actorA, SpriteActor actorB) {
        return getAngleToFace(actorA.getX(), actorA.getY(), actorB.getX(), actorB.getY());
    }

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

    // Is the object at the edge of the screens? (left & right borders)
    public static boolean outOfBoundsX(float x, float sizeX, float update) {
        return (x + update > GeometryWars.getInstance().getGameScreen().getStage().getWidth() - sizeX / 2) || (x + update < 0 - sizeX / 2);
    }

    // Is the object at the edge of the screens? (upper & lower borders)
    public static boolean outOfBoundsY(float y, float sizeY, float update) {
        return (y + update > GeometryWars.getInstance().getGameScreen().getStage().getHeight() - sizeY / 2) || (y + update  < 0 - sizeY / 2);
    }



    public static TextButton generateTextButton(Skin skin, String text, float x, float y, float width, float height) {
        TextButton button = new TextButton(text, skin);
        button.setWidth(width);
        button.setHeight(height);
        button.setPosition(x, y);
        return button;
    }

    public static boolean booleanArrayTrue(boolean[] booleanArray) {
        for (boolean bool : booleanArray) {
            if (!bool) {
                return false;
            }
        }
        return true;
    }

    public static Vector2 getCenterCoordinates(SpriteActor actor, Stage stage) {
        return new Vector2((stage.getWidth() / 2) + ((actor.getWidth() * actor.getScaleX()) / 2), (stage.getHeight() / 2) + ((actor.getHeight() * actor.getScaleY()) / 2));
    }

}
