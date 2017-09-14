package main.engine.render;

import main.game.entity.Entity;
import	org.joml.Matrix4f;
import	org.joml.Vector3f;

public class Transformation {
    private final Matrix4f projectionMatrix, viewMatrix, modelViewMatrix;

    public Transformation() {
        projectionMatrix = new Matrix4f();
        viewMatrix = new Matrix4f();
        modelViewMatrix = new Matrix4f();
    }

    public final Matrix4f getProjectionMatrix(float fov, float width, float height, float zNear, float zFar) {
        float aspectRation = width / height;
        projectionMatrix.identity();
        projectionMatrix.perspective(fov, aspectRation, zNear, zFar);
        return projectionMatrix;
    }

    /*
    public Matrix4f getWorldMatrix(Vector3f offset, Vector3f rotation, float scale) {
        worldMatrix.identity().translate(offset).
                rotateX((float)Math.toRadians(rotation.x)).
                rotateY((float)Math.toRadians(rotation.y)).
                rotateZ((float)Math.toRadians(rotation.z)).
                scale(scale);
        return worldMatrix;
    } */

    public Matrix4f getViewMatrix(Camera camera) {
        Vector3f cameraPos = camera.getPosition();
        Vector3f cameraRot = camera.getRotation();

        viewMatrix.identity();

        // Do camera rotation
        viewMatrix.rotate((float)Math.toRadians(cameraRot.x), new Vector3f(1, 0, 0))
                .rotate((float)Math.toRadians(cameraRot.y), new Vector3f(0, 1, 0)).
        rotate((float)Math.toRadians(cameraRot.z), new Vector3f(0, 0, 1));

        // Do camera translation
        viewMatrix.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        return viewMatrix;
    }

    public Matrix4f getModelViewMatrix(Entity entity, Matrix4f viewMatrix) {
        Vector3f rotation = entity.getRotation();
        modelViewMatrix.identity().translate(entity.getPosition())
                .rotateY((float)Math.toRadians(-rotation.y))
                .rotateX((float)Math.toRadians(-rotation.x))
                .rotateZ((float)Math.toRadians(-rotation.z))
                .scale(entity.getScale());

        Matrix4f viewCurr = new Matrix4f(viewMatrix);
        return viewCurr.mul(modelViewMatrix);
    }
}
