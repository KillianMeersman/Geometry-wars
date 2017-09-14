package main.engine.render.lighting;

import com.sun.org.apache.bcel.internal.generic.DREM;
import org.joml.Vector3f;

/** Represents a light that is emitted from a certain angle and has no pint of origin, nor any attenuation (like the sun) */
public class DirectionalLight {
    private Vector3f colour, direction;
    private float intensity;

    public DirectionalLight(Vector3f colour, Vector3f direction, float intensity) {
        this.colour = colour;
        this.direction = direction;
        this.intensity = intensity;
    }

    public DirectionalLight(DirectionalLight light) {
        this(new Vector3f(light.getColour()), new Vector3f(light.getDirection()), light.getIntensity());
    }

    public Vector3f getColour() {
        return colour;
    }

    public void setColour(Vector3f colour) {
        this.colour = colour;
    }

    public Vector3f getDirection() {
        return direction;
    }

    public void setDirection(Vector3f direction) {
        this.direction = direction;
    }

    public float getIntensity() {
        return intensity;
    }

    public void setIntensity(float intensity) {
        this.intensity = intensity;
    }
}
