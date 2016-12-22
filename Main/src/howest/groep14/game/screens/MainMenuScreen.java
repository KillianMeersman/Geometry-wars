package howest.groep14.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.viewport.Viewport;
import howest.groep14.game.GeometryWars;
import sun.java2d.windows.GDIBlitLoops;

public class MainMenuScreen implements Screen {

    private int WIDTH;
    private int HEIGHT;
    private int ELEMENT_WIDTH;
    private int ELEMENT_HEIGTH = 125;
    float buttonWidth;
    final float margin = 5;

    private Stage stage;
    private Viewport viewport;
    private Skin skin;

    Texture background;
    Texture btnCampaignInactive;
    Texture btnQuitInactive;
    Texture btnQuitActive;
    Texture btnSettingsInactive;
    Texture btnSettingsActive;
    Texture btnStartInactive;
    Texture btnStartActive;

    public MainMenuScreen (Viewport viewport, Skin skin) {
        this.viewport = viewport;
        this.skin = skin;
        this.WIDTH = viewport.getScreenWidth();
        this.HEIGHT = viewport.getScreenHeight();
        this.buttonWidth = WIDTH / 2 - 120;
        stage = new Stage(viewport);

        background = new Texture("Desktop/Assets/MainMenu/screen.png");

        btnStartInactive = new Texture("Desktop/Assets/MainMenu/btn-start-game.png");
        btnStartActive = new Texture("Desktop/Assets/MainMenu/btn-start-game-active.png");

        btnCampaignInactive = new Texture("Desktop/Assets/MainMenu/btn-campaign.png");

        btnSettingsInactive = new Texture("Desktop/Assets/MainMenu/btn-settings.png");
        btnSettingsActive = new Texture("Desktop/Assets/MainMenu/btn-settings-active.png");

        btnQuitInactive = new Texture("Desktop/Assets/MainMenu/btn-quit.png");
        btnQuitActive = new Texture("Desktop/Assets/MainMenu/btn-quit-active.png");


    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        float color = 230 / 255f;
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(color, color, color, 1);
        stage.getBatch().begin();
        stage.getBatch().draw(background, 0, 0, WIDTH, HEIGHT);

        if( WIDTH/2 - buttonWidth/2 < Gdx.input.getX() && Gdx.input.getX() < WIDTH/2 - buttonWidth/2 + buttonWidth
                && HEIGHT-350 < HEIGHT - Gdx.input.getY() && HEIGHT - Gdx.input.getY()< HEIGHT-350 + ELEMENT_HEIGTH){
            stage.getBatch().draw(btnStartActive, WIDTH/2 - buttonWidth/2,HEIGHT-350,buttonWidth,ELEMENT_HEIGTH);
            if(Gdx.input.isTouched()){
                GeometryWars main = GeometryWars.getInstance();
                main.setScreen(main.getGameScreen());
            }
        } else {
            stage.getBatch().draw(btnStartInactive, WIDTH/2 - buttonWidth/2,HEIGHT-350,buttonWidth,ELEMENT_HEIGTH);
        }

        stage.getBatch().draw(btnCampaignInactive, WIDTH/2 - buttonWidth/2,HEIGHT-350-margin-ELEMENT_HEIGTH,buttonWidth,ELEMENT_HEIGTH);
        if( WIDTH/2 - buttonWidth/2 < Gdx.input.getX() && Gdx.input.getX() < WIDTH/2 - buttonWidth/2 + buttonWidth
                && HEIGHT-350 - margin*2-ELEMENT_HEIGTH*2 < HEIGHT - Gdx.input.getY() && HEIGHT - Gdx.input.getY()< HEIGHT-350 - margin*2-ELEMENT_HEIGTH*2 + ELEMENT_HEIGTH){
            stage.getBatch().draw(btnSettingsActive, WIDTH/2 - buttonWidth/2,HEIGHT-350-margin*2-ELEMENT_HEIGTH*2,buttonWidth,ELEMENT_HEIGTH);
            if(Gdx.input.isTouched()){
                /* TODO Go to Settings*/
            }
        } else {
            stage.getBatch().draw(btnSettingsInactive, WIDTH/2 - buttonWidth/2,HEIGHT-350-margin*2-ELEMENT_HEIGTH*2,buttonWidth,ELEMENT_HEIGTH);
        }

        if( WIDTH/2 - buttonWidth/2 < Gdx.input.getX() && Gdx.input.getX() < WIDTH/2 - buttonWidth/2 + buttonWidth
                && HEIGHT-350 - margin*3-ELEMENT_HEIGTH*3 < HEIGHT - Gdx.input.getY() && HEIGHT - Gdx.input.getY()< HEIGHT-350 - margin*3-ELEMENT_HEIGTH*3 + ELEMENT_HEIGTH){
            stage.getBatch().draw(btnQuitActive, WIDTH/2 - buttonWidth/2,HEIGHT-350-margin*3-ELEMENT_HEIGTH*3,buttonWidth,ELEMENT_HEIGTH);
            if(Gdx.input.isTouched()){
               Gdx.app.exit();
            }
        } else {
            stage.getBatch().draw(btnQuitInactive, WIDTH/2 - buttonWidth/2,HEIGHT-350-margin*3-ELEMENT_HEIGTH*3,buttonWidth,ELEMENT_HEIGTH);
        }
        stage.getBatch().end();

        stage.draw();
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
