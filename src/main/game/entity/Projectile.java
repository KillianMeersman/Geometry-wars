package main.game.entity;

import main.engine.render.Mesh;
import main.engine.utils.Timer;
import org.joml.Vector3f;

public class Projectile extends Entity {
    private Timer lifeTimer;
    private float speed;
    private final float lifeTime;

    public Projectile(Mesh mesh) {
        super(mesh);
        this.lifeTime = 3f;
    }

    public Projectile(Mesh mesh, Vector3f position, Vector3f rotation, float scale, float speed, float lifeTime) {
        super(mesh);
        this.setPosition(position);
        this.setRotation(rotation);
        this.setScale(scale);
        this.speed = speed;
        this.lifeTime = lifeTime;
        this.lifeTimer = new Timer();
        lifeTimer.init();
    }

    public void update() {
        this.updatePosition(Direction.FORWARD, speed);
    }

    public boolean lifeOver() {
        return lifeTimer.getElapsedTime() >= lifeTime;
    }
}
