package howest.groep14.game;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import howest.groep14.game.actor.*;
import howest.groep14.game.actor.attack.AttackBehavior;
import howest.groep14.game.actor.attack.SnipeAttack;
import howest.groep14.game.actor.collision.CollisionBehavior;
import howest.groep14.game.actor.collision.DamagePlayersOnContact;
import howest.groep14.game.actor.movement.Kamikaze;
import howest.groep14.game.actor.movement.MovementBehavior;

import java.util.Random;

class SpawnManager extends Actor {
    private final int SPAWN_PLAYER_MARGIN = 100;
    private final int NUMBER_CUBES = 6;
    private final int NUMBER_CIRCLES = 0;
    private final float CUBE_SPEED = 2f;
    private final Random random = new Random();
    private final GameStage stage;

    private int cube_amount = 0, circle_amount = 0, geome_amount = 0;

    SpawnManager(GameStage stage) {
        this.stage = stage;
    }

    @Override
    public void act(float delta) {
        if (cube_amount < NUMBER_CUBES) {
            spawnCube();
            cube_amount++;
        }
        if (circle_amount < NUMBER_CIRCLES) {
            spawnCircle();
            circle_amount++;
        }
    }

    private void spawnCube() {
        EnemyActor enemyActor = new EnemyActor(stage, SpriteRepository.getCube(), EnemyActor.ENEMY_TYPE.CUBE);
        MovementBehavior kamikaze = new Kamikaze(enemyActor, stage.getPlayer(), CUBE_SPEED, false);
        enemyActor.setMovementBehavior(kamikaze);
        enemyActor.setScale(0.2f);
        Vector2 position = getSpawnCoordinates(SPAWN_PLAYER_MARGIN);

        enemyActor.setPosition(position);
        enemyActor.setVisible(true);
        stage.addEnemy(enemyActor);
    }

    private void spawnCircle() {
        final EnemyActor enemyActor = new EnemyActor(stage, SpriteRepository.getCircle(), EnemyActor.ENEMY_TYPE.CIRCLE);
        Vector2 position = getSpawnCoordinates(SPAWN_PLAYER_MARGIN);
        enemyActor.setPosition(position);
        enemyActor.setVisible(true);
        enemyActor.setScale(0.2f);

        IProjectileObserver projectileObserver = new IProjectileObserver() {
            @Override
            public void projectileHit(ProjectileActor projectile, SpriteActor victim) {

            }

            @Override
            public void projectileOutOfBounds(ProjectileActor projectile) {

            }

            @Override
            public SpriteActor getOwner() {
                return enemyActor;
            }
        };
        ProjectileActor projectileActor = new ProjectileActor(stage, SpriteRepository.getProjectile(), projectileObserver);
        projectileActor.setScale(0.1f);

        CollisionBehavior collisionBehavior = new DamagePlayersOnContact(enemyActor, 1, 1);
        projectileActor.setCollisionBehavior(collisionBehavior);

        AttackBehavior snipe = new SnipeAttack(enemyActor, stage.getPlayer(), projectileActor, 4f);
        enemyActor.setAttackBehavior(snipe);

        stage.addEnemy(enemyActor);
    }

    private void spawnGeome(float x, float y) {
        GeomeActor geome = new GeomeActor(stage, SpriteRepository.getGeome(), 1);
        geome.setScale(0.1f);
        geome.setPosition(x, y);
        stage.addGeome(geome);
    }

    public void removeEnemy(EnemyActor actor) {
        switch (actor.getTypeCode()) {
            case CUBE:
                cube_amount--;
                break;
            case CIRCLE:
                circle_amount--;
                break;
        }
        spawnGeome(actor.getX(), actor.getY());
    }

    private Vector2 getSpawnCoordinates(float margin) {
        float x = 0;
        float y = 0;
        boolean xClear = false;
        boolean yClear = false;

        while (!xClear || !yClear) {
            xClear = true;
            yClear = true;
            x = random.nextInt(Math.round(stage.getWidth() - 100));
            y = random.nextInt(Math.round(stage.getHeight() - 100));

            for (PlayerActor player : stage.getPlayers()) {
                if (!(Math.abs(player.getX() - x) >= margin)) {
                    xClear = false;
                    break;
                }
                if (!(Math.abs(player.getY() - y) >= margin)) {
                    yClear = false;
                    break;
                }
            }
        }
        return new Vector2(x, y);
    }
}
