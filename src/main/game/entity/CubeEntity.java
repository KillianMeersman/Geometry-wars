package main.game.entity;

import main.engine.render.Mesh;
import main.engine.utils.Utils;
import main.game.DummyGame;
import org.joml.Vector3f;

import java.util.Random;

public class CubeEntity extends Entity {
    private float randX, randZ;

    public CubeEntity(Mesh mesh) {
        super(mesh);
        generateRandoms();
    }
    public CubeEntity(Mesh mesh, Vector3f position, Vector3f rotation, float scale) {
        super(mesh, position, rotation, scale);
        generateRandoms();
    }

    private void generateRandoms() {
        Random rand = new Random();
        randX = rand.nextFloat() / 5;
        randX -= rand.nextFloat() / 5;
        randZ = rand.nextFloat() / 5;
        randZ -= rand.nextFloat() / 5;
    }

    public void update() {
        this.updateRotation(0, 2, 0);
        moveTowardsPlayer(DummyGame.getInstance().getPlayer(0));
    }

    private void moveTowardsPlayer(PlayerEntity player) {
        Random rand = new Random();
        Vector3f toMove = Utils.vectorSubtraction(player.getPosition(), this.getPosition());
            if (Math.abs(toMove.x) > 2.5f) {
                this.getPosition().add(toMove.x * 0.02f, 0, 0 + randX);
            }
            if (Math.abs(toMove.z) > 2.5f) {
                this.getPosition().add(0, 0, toMove.z * 0.02f + randX);
            }
    }
}
