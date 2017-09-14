package main.engine.render;

import main.engine.utils.Utils;
import org.joml.Vector3f;

public class Camera {
    private final Vector3f position, rotation;

    public Camera() {
        this.position = new Vector3f(0, 0, 0);
        this.rotation = new Vector3f(0, 0, 0);
    }

    public Camera(Vector3f position, Vector3f rotation) {
        this.position = position;
        this.rotation = rotation;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(float x, float y, float z) {
        this.position.x = x;
        this.position.y = y;
        this.position.z = z;
    }

    public void setPosition(Vector3f position) {
        this.position.x = position.x;
        this.position.y = position.y;
        this.position.z = position.z;
    }

    public void movePosition(float offsetX, float offsetY, float offsetZ) {
        float xRot = rotation.x;
            if (rotation.x > 180) {
                xRot = -(xRot);
            }
            //System.out.println(xRot);
        if (offsetZ != 0) {
            position.x += (float)Math.sin(Math.toRadians(rotation.y)) * -1.0f * offsetZ;
            position.z += (float)Math.cos(Math.toRadians(rotation.y)) * offsetZ;
            position.y += (float)Math.toRadians(xRot) * offsetZ;
        }
        if (offsetX != 0) {
            position.x += (float)Math.sin(Math.toRadians(rotation.y - 90)) * -1.0f * offsetX;
            position.z += (float)Math.cos(Math.toRadians(rotation.y - 90)) * offsetX;
        }
        position.y += offsetY;
    }

    public Vector3f getRotation()	{
        return	rotation;
    }

    public void setRotation(float x, float y, float z)	{
        rotation.x	=	x;
        rotation.y	=	y;
        rotation.z	=	z;
    }

    public void setRotation(Vector3f rotation) {
        setRotation(rotation.x, rotation.y, rotation.z);
    }

    public void moveRotation(float offsetX, float offsetY, float offsetZ) {
        rotation.x = checkRotUpdate(rotation.x, offsetX);
        rotation.y = checkRotUpdate(rotation.y, offsetY);
        rotation.z = checkRotUpdate(rotation.z, offsetZ);
    }

    private float checkRotUpdate(float coordinate, float update) {
        return Utils.checkRotUpdate180(coordinate, update);
    }
}
