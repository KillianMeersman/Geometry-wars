package howest.groep14.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import howest.groep14.game.GeometryWars;

public class LoginScreen implements Screen {

    GeometryWars game;

    public LoginScreen (GeometryWars game){
        this.game = game;
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        float color = 230 / 255f;
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(color, color, color, 1);
       

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
