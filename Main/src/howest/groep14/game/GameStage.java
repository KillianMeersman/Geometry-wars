package howest.groep14.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.IntFloatMap;
import com.badlogic.gdx.utils.IntIntMap;
import com.badlogic.gdx.utils.viewport.Viewport;
import howest.groep14.game.actor.PlayerActor;
import howest.groep14.game.actor.ProjectileActor;
import howest.groep14.game.actor.enemy.EnemyActor;

import java.util.ArrayList;
import java.util.List;

public class GameStage extends Stage {
    private List<ProjectileActor> projectiles = new ArrayList<ProjectileActor>();
    private List<EnemyActor> enemyActors = new ArrayList<EnemyActor>();
    private List<PlayerActor> players = new ArrayList<PlayerActor>();
    private SpawnManager spawnManager;

    private boolean collisionsEnabled = true;

    public GameStage(Viewport viewport) {
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

    public boolean isCollisionsEnabled() {
        return collisionsEnabled;
    }

    public void setCollisionsEnabled(boolean collisionsEnabled) {
        this.collisionsEnabled = collisionsEnabled;
        Label debugLabel = GeometryWars.getInstance().getGameScreen().getDebugLabel();
        if (!collisionsEnabled) {
            debugLabel.setText("COLLISIONS DISABLED (C to enable)");
            debugLabel.setVisible(true);
        } else {
            debugLabel.setText("");
        }
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
}
