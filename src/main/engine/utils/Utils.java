package main.engine.utils;

import main.game.entity.Entity;
import org.joml.Vector3f;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Utils {

    private Utils() {

    }

    public static String loadResource(String fileName) throws IOException {
        String result = "";
        try (InputStream in = Utils.class.getClass().getResourceAsStream(fileName)) {
            if (in == null) {
                throw new IOException("Resource could not be found");
            }
            result = new Scanner(in, "UTF-8").useDelimiter("\\A").next();
        }
        return result;
    }

    public static List<String> readAllLines(String fileName) throws Exception {
        List<String> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(Utils.class.getClass().getResourceAsStream(fileName)))) {
            String line;
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        }
        return list;
    }

    public static float normalizeValue(float min, float max, float x) {
        return (x - (min)) / (max - (min));
    }

    public static Vector3f vectorSubtraction(Vector3f vectorA, Vector3f vectorB) {
        Vector3f difference = new Vector3f();
        difference.x = vectorA.x - vectorB.x;
        difference.y = vectorA.y - vectorB.y;
        difference.z = vectorA.z - vectorB.z;
        return difference;
    }

    public static Vector3f vectorAddition(Vector3f vectorA, Vector3f vectorB) {
        Vector3f sum = new Vector3f();
        sum.x = vectorA.x + vectorB.x;
        sum.y = vectorA.y + vectorB.y;
        sum.z = vectorA.z + vectorB.z;
        return sum;
    }

    public static double getVectorLength(Vector3f vector) {
        return Math.sqrt(vector.x*vector.x + vector.y*vector.y + vector.z*vector.z);
    }

    public static Vector3f normalizeVector(Vector3f vector) {
        float length = vector.length();
        if (length != 0) {
            return new Vector3f(vector.x / length, vector.y / length, vector.z / length);
        } else {
            return new Vector3f(vector);
        }
    }
    /*
    public static float getAngleToEntity(Entity entity, Entity toFace) {
        Vector3f normalized_rotation = normalizeVector(entity.getRotation());
        Vector3f entity_to_entity_direction = normalizeVector(vectorSubtraction(toFace.getPosition(), entity.getPosition()));
        return (float) Math.acos(normalized_rotation.dot(entity_to_entity_direction));
    }
    */

    public static float checkRotUpdate180(float coordinate, float update) { // 355 7 0 360
        if (coordinate + update < -180) {
            return 180 - (180 + coordinate);
        }
        else if (coordinate + update >= 180) {
            return -180 - (180 - coordinate);
        }
        else {
            return coordinate + update;
        }
    }

    public static Vector3f getPositionFromLocal(Vector3f position, Vector3f rotation) {
        float x, y, z;
        x = (float) Math.sin(Math.toRadians(rotation.y)) - 1;
        y = (float) Math.toRadians(rotation.x);
        z = (float) Math.cos(Math.toRadians(rotation.y)) - 1;
        return new Vector3f(x, y, z);
    }

    public static Vector3f rotateToFace(Vector3f toFacePos, Vector3f entityPos) {
        Vector3f newRot = new Vector3f();
        newRot.x = toFacePos.x - entityPos.x;
        newRot.y = toFacePos.y - entityPos.y;
        newRot.z = toFacePos.z - entityPos.z;

        float hyp = (float) Math.sqrt(newRot.x * newRot.x + newRot.y * newRot.y + newRot.z * newRot.z);
        newRot.x /= hyp;
        newRot.y /= hyp;
        newRot.z /= hyp;

        return newRot;
    }
}