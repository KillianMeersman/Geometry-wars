package howest.groep14.game;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import howest.groep14.game.actor.GeomeActor;
import howest.groep14.game.actor.PlayerActor;
import howest.groep14.game.actor.ProjectileActor;
import howest.groep14.game.actor.EnemyActor;

import java.util.ArrayList;
import java.util.List;

public class GameStage extends Stage {
    private final List<PlayerActor> players = new ArrayList<PlayerActor>();
    private final List<EnemyActor> enemies = new ArrayList<EnemyActor>();
    private final List<ProjectileActor> projectiles = new ArrayList<ProjectileActor>();
    private final List<GeomeActor> geomes = new ArrayList<GeomeActor>();
    private final SpawnManager spawnManager;

    public GameStage(Viewport viewport) {
        super(viewport);
        spawnManager = new SpawnManager(this);
        addActor(spawnManager);
    }


    private void test_shield() {
        /*
        Texture texture = new Texture("Desktop/Assets/shield.png");
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Sprite projectileSprite = new Sprite(texture);

        SpriteActor shieldActor = new EnemyActor(this, projectileSprite);
        shieldActor.setActorScale(0.5f);
        shieldActor.setMovementBehavior(new StayOnActor(shieldActor, players.get(0)));
        addActor(shieldActor);
        */
    }

    public List<PlayerActor> getPlayers() {
        return players;
    }

    public void addPlayer(PlayerActor player) {
        players.add(player);
        addActor(player);
        //test_shield();
    }

    public void removePlayer(PlayerActor actor) {
        actor.setEnabled(false);
        for (PlayerActor player: players) {
            if (player.isEnabled()) {
                break;
            }
        }
        GeometryWars.getInstance().getGameScreen().gameOver();
    }

    public PlayerActor getPlayer() {
        return players.get(CustomUtils.intRandom(players.size()));
    }

    public List<EnemyActor> getEnemies() {
        return enemies;
    }

    public void addEnemy(EnemyActor actor) {
        enemies.add(actor);
        addActor(actor);
    }

    public void removeEnemy(EnemyActor actor) {
        enemies.remove(actor);
        spawnManager.removeEnemy(actor);
    }

    public List<ProjectileActor> getProjectiles() {
        return projectiles;
    }

    public void addProjectile(ProjectileActor actor) {
        projectiles.add(actor);
        addActor(actor);
    }

    public void removeProjectile(ProjectileActor actor) {
        projectiles.remove(actor);
    }

    public List<GeomeActor> getGeomes() {
        return geomes;
    }

    public void addGeome(GeomeActor actor) {
        geomes.add(actor);
        addActor(actor);
    }

    public void removeGeome(GeomeActor actor) {
        geomes.remove(actor);
        spawnManager.removeGeome(actor);
    }

    public SpawnManager getSpawnManager() {
        return spawnManager;
    }
}
