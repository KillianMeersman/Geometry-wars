package com.example.mygame;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.example.mygame.enemy.EnemyActor;
import java.util.ArrayList;
import java.util.List;

public class GameStage extends Stage {
    private List<ProjectileActor> projectiles = new ArrayList<ProjectileActor>();
    private List<EnemyActor> enemyActors = new ArrayList<EnemyActor>();
    private List<PlayerActor> players = new ArrayList<PlayerActor>();
    private SpawnManager spawnManager;

    GameStage(Viewport viewport) {
        super(viewport);
        spawnManager = new SpawnManager(this);
        addActor(spawnManager);
    }

    @Override
    public void act() {
        super.act();
    }

    public List<ProjectileActor> getProjectiles() {
        return projectiles;
    }

    public List<EnemyActor> getEnemyActors() {
        return enemyActors;
    }

    public List<PlayerActor> getPlayers() {
        return players;
    }

    public void addProjectile(ProjectileActor projectile) {
        addActor(projectile);
        projectiles.add(projectile);
    }

    public void addEnemyActor(EnemyActor enemy) {
        addActor(enemy);
        enemyActors.add(enemy);
    }

    public void addPlayer(PlayerActor player) {
        addActor(player);
        players.add(player);
    }

    public void removeProjectile(ProjectileActor actor) {
        projectiles.remove(actor);
        actor.remove();
    }

    public void removeEnemyActor(EnemyActor actor) {
        enemyActors.remove(actor);
        actor.remove();
    }

    public void removePlayer(PlayerActor actor) {
        players.remove(actor);
        actor.remove();
    }
}
