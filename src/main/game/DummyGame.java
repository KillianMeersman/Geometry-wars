package main.game;

import com.sun.org.apache.bcel.internal.generic.DUP;
import main.engine.core.IGameLogic;
import main.engine.core.Window;
import main.engine.input.MouseInput;
import main.engine.render.*;
import main.engine.render.lighting.DirectionalLight;
import main.engine.render.lighting.PointLight;
import main.engine.utils.AIUtils;
import main.game.entity.*;
import org.joml.Vector2f;
import org.joml.Vector3f;

import java.lang.reflect.Method;
import java.util.ArrayList;

import static org.lwjgl.glfw.GLFW.*;

public class DummyGame implements IGameLogic {
    private static final float CAMERA_POS_STEP = 0.05f;
    private static final float MOUSE_SENSITIVITY = 0.2f;
    private static float CAMERA_SPEED = 1f;

    private final Vector3f cameraInc;
    private final Renderer renderer;
    private final Camera camera;
    private ArrayList<Entity> entities;
    private ArrayList<PlayerEntity> players;
    private Vector3f ambientLight;
    private DirectionalLight directionalLight;
    private PointLight[] pointLights;

    private static DummyGame instance = new DummyGame();

    private DummyGame() {
        renderer = new Renderer();
        camera = new Camera();
        camera.setRotation(90, 180, 0);
        camera.setPosition(0, 30, 0);
        cameraInc = new Vector3f(0, 0, 0);
        pointLights = new PointLight[5];
    }

    public static DummyGame getInstance() {
        return instance;
    }

    @Override
    public void init(Window window) throws Exception {
        renderer.init(window);

        float reflectance = 1f;

        entities = new ArrayList<>();
        players = new ArrayList<>();
        /*
        Mesh block = OBJLoader.loadMesh("/models/cube.obj");
        Texture dirtTexture = new Texture("/textures/grassblock.png");
        Material dirt = new Material(dirtTexture, 1f);
        block.setMaterial(dirt);
        Entity dirtBlock = new Entity(block);
        dirtBlock.setPosition(0, 0, -3);
        entities.add(dirtBlock); */

        Mesh circleWall = OBJLoader.loadMesh("/models/wall.obj");
        Mesh bunnyMesh = OBJLoader.loadMesh("/models/bunny.obj");
        Mesh planeMesh = OBJLoader.loadMesh("/models/plane.obj");
        Mesh jetMesh = OBJLoader.loadMesh("/models/spaceship.obj");
        Mesh cubeMesh = OBJLoader.loadMesh("/models/cubeEnemy.obj");
        Material material = new Material(new Vector3f(1f, 1f, 1f), reflectance);
        Material chrome = new Material(new Vector3f(0.25f, 0.25f, 0.25f),
                new Vector3f(0.4f, 0.4f, 0.4f),
                new Vector3f(0.77f, 0.77f,0.77f), 0.6f);
        Material blue = new Material(new Vector3f(0, 0, 1f), new Vector3f(0, 0, 1f), new Vector3f(0, 0, 0), 0);
        circleWall.setMaterial(chrome);
        bunnyMesh.setMaterial(material);
        planeMesh.setMaterial(chrome);
        jetMesh.setMaterial(chrome);
        cubeMesh.setMaterial(blue);

        Entity bunny = new Entity(bunnyMesh);
        bunny.setScale(1f);
        bunny.setPosition(0, 0, -5);
        entities.add(bunny);

        Entity floor = new Entity(planeMesh);
        floor.setScale(10000f);
        floor.setPosition(0, -1f, 0);
        entities.add(floor);

        PlayerEntity playerEntity = new PlayerEntity(jetMesh);
        playerEntity.setScale(1f);
        playerEntity.setPosition(4, 1, 10);
        playerEntity.setRotation(0, 0, 0);
        entities.add(playerEntity);
        players.add(playerEntity);

        PlayerEntity enemy = new PlayerEntity(jetMesh);
        enemy.setScale(1f);
        enemy.setPosition(1, 1, -1);
        enemy.setRotation(0, -21.802f, 0);
        entities.add(enemy);

        CubeEntity enemyCube1 = new CubeEntity(cubeMesh);
        enemyCube1.setScale(1f);
        enemyCube1.setPosition(10, 1, 10);
        enemyCube1.setRotation(0, 0, 0);
        entities.add(enemyCube1);

        CubeEntity enemyCube2 = new CubeEntity(cubeMesh);
        enemyCube2.setScale(1f);
        enemyCube2.setPosition(11, 1, -11);
        enemyCube2.setRotation(0, 0, 0);
        entities.add(enemyCube2);


        CubeEntity enemyCube3 = new CubeEntity(cubeMesh);
        enemyCube3.setScale(1f);
        enemyCube3.setPosition(-12, 1, 12);
        enemyCube3.setRotation(0, 0, 0);
        entities.add(enemyCube3);

        Entity wall = new Entity(circleWall);
        wall.setScale(30f);
        entities.add(wall);


        ambientLight = new Vector3f(0.1f, 0.1f, 0.1f);
        directionalLight = new DirectionalLight(new Vector3f(0.3f, 0.95f, 0.9f), new Vector3f(10f, 10f, 0), 5f);


        Vector3f lightPosition = new Vector3f(0, 1, -6);
        Vector3f lightColour = new Vector3f(0.3f, 0.3f, 1f);
        float lightIntensity = 3f;
        PointLight pointLight = new PointLight(lightColour, playerEntity.getPosition(), lightIntensity);
        PointLight.Attenuation  att = new PointLight.Attenuation(0.0f, 0.0f, 1.0f);
        pointLight.setAttenuation(att);
        pointLights[0] = pointLight;
}

    @Override
    public void input(Window window, MouseInput mouseInput) {
        PlayerEntity player = (PlayerEntity) entities.get(2);
        // Camera movement
        if (window.isKeyPressed(GLFW_KEY_LEFT_SHIFT)) {
            CAMERA_SPEED = 10;
        } else {
            CAMERA_SPEED = 1;
        }
        cameraInc.set(0, 0, 0);
        if (window.isKeyPressed(GLFW_KEY_W)) {
            cameraInc.z = -CAMERA_SPEED;
        }
        if (window.isKeyPressed(GLFW_KEY_S)) {
            cameraInc.z = CAMERA_SPEED;
        }
        if (window.isKeyPressed(GLFW_KEY_A)) {
            cameraInc.x = -CAMERA_SPEED;
        }
        if (window.isKeyPressed(GLFW_KEY_D)) {
            cameraInc.x = CAMERA_SPEED;
        }
        if (window.isKeyPressed(GLFW_KEY_Z)) {
            cameraInc.y = -CAMERA_SPEED;
        }
        if (window.isKeyPressed(GLFW_KEY_X)) {
            cameraInc.y = CAMERA_SPEED;
        }

        if (window.isKeyPressed(GLFW_KEY_LEFT)) {
            player.updateRotation(0, -3f, 0);
        }
        if (window.isKeyPressed(GLFW_KEY_RIGHT)) {
            player.updateRotation(0, 3f, 0);
        }
        if (window.isKeyPressed(GLFW_KEY_UP)) {
            player.increaseSpeed();
        }
        if (window.isKeyPressed(GLFW_KEY_DOWN)) {
            player.decreaseSpeed();
        }
        if (window.isKeyPressed(GLFW_KEY_PAGE_UP)) {
            player.updateRotation(3f, 0, 0);
        }
        if (window.isKeyPressed(GLFW_KEY_PAGE_DOWN)) {
            player.updateRotation(-3f, 0, 0);
        }
        if (window.isKeyPressed(GLFW_KEY_SPACE)) {
            player.boost();
        }
        if (window.isKeyPressed(GLFW_KEY_INSERT)) {
            Projectile projectile = player.fireProjectile();
            if (projectile != null) {
                entities.add(projectile);
            }
        }
    }

    @Override
    public void update(float interval, MouseInput mouseInput) {
        camera.movePosition(cameraInc.x * CAMERA_POS_STEP, cameraInc.y * CAMERA_POS_STEP,
                cameraInc.z * CAMERA_POS_STEP);

        // Update camera by mouse
        if (mouseInput.isRightButtonPressed()) {
            Vector2f rotVec = mouseInput.getDisplayVec();
            camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
        }
        entities.get(0).getRotation().y += 0.1f;
        PlayerEntity player = (PlayerEntity) entities.get(2);
        PlayerEntity enemy = (PlayerEntity) entities.get(3);
        AIUtils.rotateTowardsEntity(enemy, player);
        addProjectile(enemy);
        //System.out.println(enemy.getRotation().y + " - " + player.getPosition().z);
        //System.out.println(AIUtils.getFaceYRotation(enemy, player));
        pointLights[0].setPosition(player.getPosition());

        ArrayList<Entity> toDelete = new ArrayList<>();
        for (Entity entity : entities) {
            entity.update();
            if (entity.lifeOver()) {
                toDelete.add(entity);
            }
        }
        entities.removeAll(toDelete);
    }

    private void addProjectile(PlayerEntity entity) {
        Entity projectile = entity.fireProjectile();
        if (projectile != null) {
            entities.add(projectile);
        }
    }

    public PlayerEntity getPlayer(int i) {
        return players.get(i);
    }

    public boolean canMove(Entity entity, Vector3f newPos) {
        for (Entity e : entities) {
            Vector3f ePos = e.getPosition();
            if (Math.abs(ePos.x - newPos.x) < 0.1f && ePos.x != newPos.x) {
                return false;
            }
            if (Math.abs(ePos.z - newPos.z) < 0.1f && ePos.z != newPos.z) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void render(Window window) {
        renderer.render(window, camera, entities, ambientLight, directionalLight, pointLights);
    }

    @Override
    public void cleanup() {
        renderer.cleanup();
        for (Entity gameItem : entities) {
            gameItem.getMesh().cleanup();
        }
    }
}
