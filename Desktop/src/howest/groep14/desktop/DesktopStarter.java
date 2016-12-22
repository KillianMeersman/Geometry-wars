package howest.groep14.desktop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import howest.groep14.game.GeometryWars;

//This is the frontend, running this class will display a screens
public class DesktopStarter {
    public static void main(String[] args){
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Example";
        cfg.fullscreen = false;

        cfg.width = 1920;
        cfg.height = 1080;

        new LwjglApplication(GeometryWars.getInstance(),cfg);
    }
}
