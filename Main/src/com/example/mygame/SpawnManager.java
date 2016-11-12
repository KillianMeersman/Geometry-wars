package com.example.mygame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.example.mygame.enemy.EnemyActor;
import com.example.mygame.enemy.KamikazeBehavior;

import java.util.Random;

class SpawnManager extends Actor {
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
        enemy.setBehavior(new KamikazeBehavior(enemy, gameStage.getPlayers().get(0), 3));
        enemy.setPosition(random.nextInt(1000), random.nextInt(500));
        gameStage.addEnemyActor(enemy);
    }
}
