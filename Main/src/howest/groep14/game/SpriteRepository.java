package howest.groep14.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class SpriteRepository {
    private static Sprite cube, circle, arrow, geome, projectile, blueProjectile, redProjectile, shield;

    public static void init() {
        Texture texture = new Texture(Gdx.files.internal("cube.png"));
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        cube = new Sprite(texture);

        texture = new Texture(Gdx.files.internal("circle.png"));
        circle = new Sprite(texture);

        texture = new Texture(Gdx.files.internal("arrow.png"));
        arrow = new Sprite(texture);

        texture = new Texture(Gdx.files.internal("geome.png"));
        geome = new Sprite(texture);

        texture = new Texture(Gdx.files.internal("projectile.png"));
        projectile = new Sprite(texture);

        texture = new Texture(Gdx.files.internal("blueProjectile.png"));
        blueProjectile = new Sprite(texture);

        texture = new Texture(Gdx.files.internal("redProjectile.png"));
        redProjectile = new Sprite(texture);

        texture = new Texture(Gdx.files.internal("shield.png"));
        shield = new Sprite(texture);
    }

    public static Sprite getCube() {
        return new Sprite(cube);
    }

    public static Sprite getCircle() {
        return new Sprite(circle);
    }

    public static Sprite getArrow() {
        return new Sprite(arrow);
    }

    public static Sprite getGeome() {
        return new Sprite(geome);
    }

    public static Sprite getProjectile() {
        return new Sprite(projectile);
    }

    public static Sprite getBlueProjectile() {
        return new Sprite(blueProjectile);
    }

    public static Sprite getRedProjectile() {
        return new Sprite(redProjectile);
    }

    public static Sprite getShield() {
        return new Sprite(shield);
    }
}
