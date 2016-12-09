package howest.groep14.game;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import howest.groep14.game.actor.PlayerActor;
import howest.groep14.game.actor.SpriteActor;
import howest.groep14.game.actor.actions.SnipeAction;
import howest.groep14.game.actor.enemy.EnemyActor;
import howest.groep14.game.actor.enemy.KamikazeBehavior;
import howest.groep14.game.actor.enemy.SniperBehavior;

import java.util.Random;

class SpawnManager extends Actor {
    private final int SPAWN_PLAYER_MARGIN = 100;
    private final int NUMBER_CUBES = 5;
    private final int NUMBER_CIRCLES = 2;
    private final Random random = new Random();
    private final GameStage gameStage;

    SpawnManager(GameStage stage) {
        gameStage = stage;
    }

    @Override
    public void act(float delta) {
        if (gameStage.getCubeEnemies().size() < NUMBER_CUBES) {
            spawnCube();
        }
        if (gameStage.getCircleEnemies().size() < NUMBER_CIRCLES) {
            spawnCircle();
        }
    }

    private void spawnCube() {
        Texture cubeTexture = new Texture("Desktop/Assets/greyRectangle.png");
        Sprite cubeSprite = new Sprite(cubeTexture);
        EnemyActor enemyActor = new EnemyActor(gameStage, cubeSprite, 0.2f);
        enemyActor.setBehavior(new KamikazeBehavior(enemyActor, gameStage.getPlayers().get(0), 1));
        Vector2 position = getSpawnCoordinates(SPAWN_PLAYER_MARGIN);
        enemyActor.setPosition(position);
        enemyActor.setVisible(true);
        gameStage.addCubeEnemy(enemyActor);
    }

    private void spawnCircle() {
        Texture circleTexture = new Texture("Desktop/Assets/greyCircle.png");
        Sprite circleSprite = new Sprite(circleTexture);
        SpriteActor enemyActor = new EnemyActor(gameStage, circleSprite, 0.2f);
        enemyActor = new SnipeAction(enemyActor, gameStage.getPlayers().get(0), 1);
        Vector2 position = getSpawnCoordinates(SPAWN_PLAYER_MARGIN);
        enemyActor.setPosition(position);
        enemyActor.setVisible(true);

        gameStage.addCircleEnemy(enemyActor);
    }

    private Vector2 getSpawnCoordinates(float margin) {
        float x = 0;
        float y = 0;
        boolean xClear = false;
        boolean yClear = false;

        while (!xClear || !yClear) {
            xClear = true;
            yClear = true;
            x = random.nextInt(Math.round(gameStage.getWidth() - 100));
            y = random.nextInt(Math.round(gameStage.getHeight() - 100));

            for (PlayerActor player : gameStage.getPlayers()) {
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

    /*
    private Vector2 generateSpawnPosition(PlayerActor player) {
        int randX = random.nextInt(Gdx.graphics.getWidth() - (int)player.getX());
        int randY = random.nextInt(Gdx.graphics.getHeight() - (int)player.getY());
        if (random.nextBoolean()) { randX = -randX; }
        if (random.nextBoolean()) { randY = -randY; }
        return new Vector2(player.getX() + randX, player.getY() + randY);
    }
    */

    /*
    private Vector2 generateSpawnPosition(List<PlayerActor> players) {
        Vector2 position = new Vector2(random.nextInt(Gdx.graphics.getWidth() - 5), random.nextInt(Gdx.graphics.getHeight() - 5));
        for (SpriteActor player : players) {
            Vector2 distance = position.sub(player.getPosition());
            if (distance.x < SPAWN_PLAYER_MARGIN || distance.y < SPAWN_PLAYER_MARGIN) {
                return generateSpawnPosition(players);
            }
        }
        return position;
    }
    */
}
