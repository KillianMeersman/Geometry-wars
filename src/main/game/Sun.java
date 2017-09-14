package main.game;

import main.engine.render.lighting.DirectionalLight;
import main.engine.utils.Utils;
import org.joml.Vector3f;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static java.lang.Math.round;


public class Sun {
    private final DirectionalLight light;
    private final Vector3f colour, direction, ambientLight;
    private float lightAngle;
    private final float update, dawnLimit, duskLimit, nightUpper, nightLower, maxAmbient;

    public Sun(float update) {
        dawnLimit = -75;
        duskLimit = 65;
        nightUpper = 95;
        nightLower = -95;
        maxAmbient = 0.5f;

        this.update = update;

        lightAngle = 0;
        colour = new Vector3f(1f, 0.8f, 0.8f);
        direction = new Vector3f(-1f, 0, 0);
        ambientLight = new Vector3f(0, 0, 0);
        light = new DirectionalLight(colour, direction, 1.0f);
    }

    public void update(Vector3f ambientLight) {
        lightAngle += update;
        if(lightAngle > nightUpper - 1) { // night
            light.setIntensity(0.0f);
            light.getColour().x = 0;
            light.getColour().y = 0;
            light.getColour().z = 0;
            updateAmbientIntensity(0);
            if(lightAngle >= 360) {
                lightAngle = nightLower;
            }

        } else if (lightAngle > nightLower && lightAngle < dawnLimit) { // dawn
            float normal = Utils.normalizeValue(nightLower, dawnLimit, lightAngle);
            light.setIntensity(normal);
            updateDirectionalColour(colour.x * normal, colour.y * normal, colour.z * normal);
            updateAmbientIntensity(min(normal, maxAmbient));

        } else if (lightAngle < nightUpper && lightAngle > duskLimit) { // dusk
            float normal = Utils.normalizeValue(nightUpper, duskLimit, lightAngle);
            light.setIntensity(normal);
            updateDirectionalColour(colour.x * normal, colour.y * normal, colour.z * normal);
            updateAmbientIntensity(min(normal, maxAmbient));

        } else {
            light.setIntensity(1);
            updateDirectionalColour(colour.x, colour.y, colour.z);
            updateAmbientIntensity(maxAmbient);
        }

        double angRad = java.lang.Math.toRadians(lightAngle);
        light.getDirection().x = (float) Math.sin(angRad);
        light.getDirection().y = (float) Math.cos(angRad);
    }

    private void updateDirectionalColour(float red, float green, float blue) {
        light.setColour(new Vector3f(red, green, blue));
    }

    private void updateAmbientIntensity(float value) {
        ambientLight.x = value;
        ambientLight.y = value;
        ambientLight.z = value;
    }

    public DirectionalLight getDirectionalLight() {
        return light;
    }

    public Vector3f getAmbientLight() {
        return ambientLight;
    }
}