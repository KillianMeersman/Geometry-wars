package howest.groep14.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import howest.groep14.game.GeometryWars;

//This is the frontend, running this class will display a screens
public class DesktopStarter {
    public static void main(String[] args){
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Geometry Wars";
        cfg.fullscreen = false;

        cfg.width = 1280;
        cfg.height = 1024;

        new LwjglApplication(GeometryWars.getInstance(), cfg);
    }
}
