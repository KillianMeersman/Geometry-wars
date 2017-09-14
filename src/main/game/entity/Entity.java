package main.game.entity;

import main.engine.utils.Utils;
import org.joml.Vector3f;
import main.engine.render.Mesh;

public class Entity {
    private final Mesh mesh;
    private final Vector3f position;
    private final Vector3f rotation;
    private float scale;

    public Entity(Mesh mesh) {
        this.mesh = mesh;
        position = new Vector3f(0, 0, 0);
        scale = 1;
        rotation = new Vector3f(0, 0, 0);
    }

    public Entity(Mesh mesh, Vector3f position, Vector3f rotation, float scale) {
        this.mesh = mesh;
        this.position = position;
        this.rotation = rotation;
        this.scale = scale;
    }

    public void update() {

    }

    /** Check whether or not this object is to be deleted */
    public boolean lifeOver() {
        return false;
    }

    // Position
    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        this.position.set(x, y, z);
    }

    public void setPosition(Vector3f position) {
        this.position.set(position);
    }

    public void updatePosition(Direction direction, float speed) {
        float x, y, z;
        Vector3f rotation = this.getRotation();
        float xFactor = (float) Math.cos(Math.toRadians(rotation.x));
        switch (direction) {
            case FORWARD:
                x = (float) Math.sin(Math.toRadians(rotation.y)) * xFactor * speed;
                y = (float) Math.toRadians(rotation.x) * speed;
                z = (float) Math.cos(Math.toRadians(rotation.y)) * xFactor * speed;
                break;
            default:
                x = 0;
                y = 0;
                z = 0;
        }
        this.position.x -= x;
        this.position.y += y;
        this.position.z += z;
    }

    public void updatePosition(float x, float y, float z) {
        this.position.add(x, y , z);
    }

    // Rotation
    public final Vector3f getRotation() {
        return rotation;
    }

    public final void setRotation(float x, float y, float z) {
        this.rotation.set(x, y, z);
    }

    public final void setRotation(Vector3f rotation) {
        this.rotation.set(rotation);
    }

    public final void updateRotation(float x, float y, float z) {
        setRotation(checkRotUpdate(rotation.x, x), checkRotUpdate(rotation.y, y), checkRotUpdate(rotation.z, z));
    }

    // Scale
    public final float getScale() {
        return scale;
    }

    public final void setScale(float scale) {
        this.scale = scale;
    }

    public Mesh getMesh() {
        return mesh;
    }

    private float checkRotUpdate(float coordinate, float update) {
        return Utils.checkRotUpdate180(coordinate, update);
    }
}
