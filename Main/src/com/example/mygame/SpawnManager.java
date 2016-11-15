package com.example.mygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.example.mygame.actor.enemy.EnemyActor;
import com.example.mygame.actor.enemy.KamikazeBehavior;
import java.util.Random;

class SpawnManager extends Actor {
    private final int SPAWN_PLAYER_MARGIN = 100;
    private Random random = new Random();
    private GameStage gameStage;

    SpawnManager(GameStage stage) {
        gameStage = stage;
    }

    @Override
    public void act(float delta) {
        try {
            if (gameStage.getEnemyActors().size() < 5) {
                spawnEnemy();
            }
        } catch (NullPointerException e) {
            spawnEnemy();
        }
    }

    private void spawnEnemy() {
        Texture bacteria2 = new Texture("Desktop/Assets/greyRectangle.png");
        Sprite bacteria2Sprite = new Sprite(bacteria2);
        EnemyActor enemy = new EnemyActor(gameStage, bacteria2Sprite, 0.2f);
        enemy.setBehavior(new KamikazeBehavior(enemy, gameStage.getPlayers().get(0), 2));
        enemy.setPosition(random.nextInt(Gdx.graphics.getWidth() - 150), random.nextInt(Gdx.graphics.getHeight() - 150));
        enemy.setVisible(true);
        gameStage.addEnemyActor(enemy);
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
