package howest.groep14.game.screens;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.Viewport;
import howest.groep14.game.CustomUtils;
import howest.groep14.game.GeometryWars;
import howest.groep14.game.player.PlayerRepository;
import java.sql.SQLException;

public class RegisterScreenOld implements Screen {
    private Stage stage;
    private Viewport viewport;
    private Skin skin;
    private Label notificationLabel;

    public RegisterScreenOld(Viewport viewport, Skin skin) {
        this.viewport = viewport;
        this.skin = skin;
        stage = new Stage(viewport);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    private void createUI(int width, int height) {
        final float label_width = 300;
        final float x_center = Gdx.graphics.getWidth() / 2;

        stage.clear();
        Label usernameLabel = new Label("Username:", skin);
        usernameLabel.setWidth(label_width);
        usernameLabel.setHeight(20);
        usernameLabel.setPosition(x_center - 350, height - 50);
        stage.addActor(usernameLabel);

        final TextField usernameField = new TextField("", skin);
        usernameField.setWidth(500);
        usernameField.setHeight(20);
        usernameField.setPosition(x_center - 250, height - 50);
        stage.addActor(usernameField);

        Label emailLabel = new Label("Email:", skin);
        emailLabel.setWidth(label_width);
        emailLabel.setHeight(20);
        emailLabel.setPosition(x_center - 350, height - 100);
        stage.addActor(emailLabel);

        final TextField emailField = new TextField("", skin);
        emailField.setWidth(500);
        emailField.setHeight(20);
        emailField.setPosition(x_center - 250, height - 100);
        stage.addActor(emailField);

        Label passwordLabel = new Label("Password:", skin);
        passwordLabel.setWidth(label_width);
        passwordLabel.setHeight(20);
        passwordLabel.setPosition(x_center - 350, height - 150);
        stage.addActor(passwordLabel);

        final TextField passwordField = new TextField("", skin);
        passwordField.setWidth(500);
        passwordField.setHeight(20);
        passwordField.setPosition(x_center - 250, height - 150);
        stage.addActor(passwordField);


        TextButton registerButton = CustomUtils.generateTextButton(skin, "R E G I S T E R", x_center - 350, height - 250, 650, 50);
        stage.addActor(registerButton);
        registerButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                try {
                    notificationLabel.setText("REGISTERING...");
                    doRegister(usernameField.getText(), emailField.getText(), passwordField.getText());
                    notificationLabel.setText("REGISTERED");
                } catch (SQLException e) {
                    notificationLabel.setText("USERNAME TAKEN");
                }
            }
        });

        final Label notificationLabel = new Label("TEST", skin);
        notificationLabel.setWidth(100);
        notificationLabel.setHeight(20);
        notificationLabel.setPosition(x_center, height - 300, Align.center);
        this.notificationLabel = notificationLabel;
        stage.addActor(notificationLabel);



        TextButton backButton = CustomUtils.generateTextButton(skin, "B A C K", x_center - 350, height - 310, 650, 50);
        stage.addActor(backButton);
        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                GeometryWars main = GeometryWars.getInstance();
                main.setScreen(main.getMenuScreenOld());
            }
        });
    }

    private void doRegister(String username, String email, String password) throws SQLException {
        PlayerRepository.getInstance().createPlayer(username, email, password);
    }

    @Override
    public void render(float delta) {
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        createUI(width, height);
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
