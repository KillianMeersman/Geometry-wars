package howest.groep14.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.viewport.Viewport;
import howest.groep14.game.actor.GeomeActor;
import howest.groep14.game.actor.PlayerActor;
import howest.groep14.game.actor.SpriteActor;
import howest.groep14.game.actor.drone.DroneActor;
import howest.groep14.game.actor.movement.StayOnActor;
import howest.groep14.game.actor.projectile.ProjectileActor;
import howest.groep14.game.actor.enemy.EnemyActor;

import java.util.ArrayList;
import java.util.List;

public class GameStage extends Stage {
    private List<ProjectileActor> projectiles = new ArrayList<ProjectileActor>();
    private List<EnemyActor> cubeEnemies = new ArrayList<EnemyActor>();
    private List<EnemyActor> circleEnemies = new ArrayList<EnemyActor>();
    private List<SpriteActor> geomes = new ArrayList<SpriteActor>();
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

    public List<EnemyActor> getCubeEnemies() {
        return cubeEnemies;
    }

    public void addCubeEnemy(EnemyActor enemy) {
        addActor(enemy);
        cubeEnemies.add(enemy);
    }

    public List<EnemyActor> getCircleEnemies() {
        return circleEnemies;
    }

    public void addCircleEnemy(EnemyActor enemy) {
        addActor(enemy);
        circleEnemies.add(enemy);
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

    public void addPlayer(PlayerActor player) {
        addActor(player);
        players.add(player);

        Texture texture = new Texture("Desktop/Assets/greyProjectile.png");
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Sprite projectileSprite = new Sprite(texture);
        DroneActor actor = new DroneActor(this, projectileSprite, player);
        actor.setScale(0.5f);
        addActor(actor);
        test_shield();

    }

    private void test_shield() {
        Texture texture = new Texture("Desktop/Assets/shield.png");
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Sprite projectileSprite = new Sprite(texture);

        SpriteActor shieldActor = new SpriteActor(this, projectileSprite);
        shieldActor.setScale(0.5f);
        shieldActor.setMovementBehavior(new StayOnActor(shieldActor, players.get(0)));
        addActor(shieldActor);
    }

    public void removeProjectile(ProjectileActor actor) {
        projectiles.remove(actor);
        actor.remove();
    }

    public void removeCubeEnemy(EnemyActor actor) {
        cubeEnemies.remove(actor);
        spawnGeome(actor.getX(), actor.getY());
        actor.remove();
    }

    public void removeCircleEnemy(EnemyActor actor) {
        circleEnemies.remove(actor);
        spawnGeome(actor.getX(), actor.getY());
        actor.remove();
    }

    private void spawnGeome(float x, float y) {
        Texture texture = new Texture("Desktop/Assets/geome.png");
        Sprite geomeSprite = new Sprite(texture);
        GeomeActor geome = new GeomeActor(this, geomeSprite);
        geome.setScale(0.1f);
        geome.setPosition(x, y);
        geomes.add(geome);
        addActor(geome);
    }

    public void removePlayer(PlayerActor actor) {
        players.remove(actor);
        if (players.size() < 1) {
            GeometryWars.getInstance().getGameScreen().gameOver();
        }
    }
}
