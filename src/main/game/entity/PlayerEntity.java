package main.game.entity;

import main.engine.render.Material;
import main.engine.render.Mesh;
import main.engine.render.OBJLoader;
import main.engine.utils.Timer;
import main.game.DummyGame;
import org.joml.Vector3f;

public class PlayerEntity extends Entity {

    private final float MAX_SPEED = 0.5f;
    private final float MAX_ACCEL = 0.1f;
    private final float NORMAL_ACCEL = 0.01f;
    private float ACCEL = 0.01f;
    private final float BOOST_ACCEL = 0.06f;
    private final float FRICTION_FACTOR = 0.98f;
    private final float TIME_BETWEEN_SHOTS = 0.1f;
    private final Timer projectileTimer;

    private Mesh projectileMesh;
    private Material projectileMaterial;

    private float velX = 0, velZ = 0, velY = 0;

    public PlayerEntity(Mesh mesh) {
        super(mesh);
        try {
            this.projectileMesh = OBJLoader.loadMesh("/models/bolt.obj");
            this.projectileMaterial = new Material(new Vector3f(0.3f, 0.3f, 1f), new Vector3f(0.3f, 0.3f, 0.3f), new Vector3f(0, 0, 0), 10f);
            projectileMesh.setMaterial(projectileMaterial);
        } catch (Exception e) {
            e.printStackTrace();
        }
        projectileTimer = new Timer();
        projectileTimer.init();
    }

    public void update() {
        // Slowly return to normal acceleration
        if (ACCEL > NORMAL_ACCEL) {
            ACCEL -= NORMAL_ACCEL / 2;
        }
        else if (ACCEL + NORMAL_ACCEL /10 > NORMAL_ACCEL) { // Fix floating point shit
            ACCEL = NORMAL_ACCEL;
        }
        this.getPosition().z += (velZ *= FRICTION_FACTOR); // Update position and reduce velocity to simulate friction
        this.getPosition().x -= (velX *= FRICTION_FACTOR);
        this.getPosition().y += (velY *= FRICTION_FACTOR);
        this.getRotation().z *= 0.98f;
    }

    /** Increase velocity */
    public void increaseSpeed() {
        float yRadians = (float) Math.toRadians(getRotation().y);
        float xRadians = (float) Math.toRadians(getRotation().x);
        float xFactor = (float) Math.cos(xRadians);
        if (velX < MAX_SPEED) {
            velX += Math.sin(yRadians) * xFactor * ACCEL;
        }
        if (velZ < MAX_SPEED) {
            velZ += Math.cos(yRadians) * xFactor * ACCEL;
        }
        if (velY < MAX_SPEED) {
            velY += xRadians * ACCEL;
        }
    }

    /** Decrease velocity */
    public void decreaseSpeed() {

    }

    /** Create and return a projectile */
    public Projectile fireProjectile() {
        if (projectileTimer.getElapsedTime() >= TIME_BETWEEN_SHOTS) {
            projectileTimer.reset();
            return new Projectile(projectileMesh, this.getPosition(), this.getRotation(), 1f, 1.5f, 3f);
        }
        return null;
    }

    /** Increase acceleration */
    public void boost() {
        ACCEL = Math.min(ACCEL + BOOST_ACCEL, MAX_ACCEL);
    }
}
