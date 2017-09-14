package main.engine.render;

import org.joml.Vector3f;
import sun.invoke.util.ValueConversions;

public class Material {
    private static final Vector3f DEFAULT_COLOR = new Vector3f(1.0f, 1.0f, 1.0f);
    private Vector3f ambient, diffuse, specular;
    private float reflectance;
    private Texture texture;

    public Material(Vector3f colour, float reflectance) {
        this.ambient = colour;
        this.diffuse = colour;
        this.specular = colour;
        this.reflectance = reflectance;
    }

    public Material(Vector3f ambient, Vector3f diffuse, Vector3f specular, float reflectance) {
        this.ambient = ambient;
        this.diffuse = diffuse;
        this.specular = specular;
        this.reflectance = reflectance;
    }

    public Material(Texture texture, float reflectance) {
        this.texture = texture;
        this.reflectance = reflectance;
    }

    public float getReflectance() {
        return reflectance;
    }

    public void setReflectance(float reflectance) {
        this.reflectance = reflectance;
    }

    public boolean isTextured() {
        return this.texture != null;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Vector3f getAmbient() {
        return ambient;
    }

    public void setAmbient(Vector3f ambient) {
        this.ambient = ambient;
    }

    public Vector3f getDiffuse() {
        return diffuse;
    }

    public void setDiffuse(Vector3f diffuse) {
        this.diffuse = diffuse;
    }

    public Vector3f getSpecular() {
        return specular;
    }

    public void setSpecular(Vector3f specular) {
        this.specular = specular;
    }
}
