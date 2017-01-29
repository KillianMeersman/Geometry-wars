package howest.groep14.game;

import java.sql.SQLException;

public class SettingsRepository {
    private static SettingsRepository instance = new SettingsRepository();
    private SettingsMapper mapper;
    private boolean dbaccess = false;
    private float actorScale = 0.5f;
    private boolean login_required = false;
    private static final String SQL_USER = "geometry-wars";
    private static final String SQL_PWD = "jEzPRAyKE6FsiiIjQXwq";

    public static SettingsRepository getInstance() {
        return instance;
    }

    private SettingsRepository() {
        tryDbAccess();
    }

    public boolean hasDbAccess() {
        return dbaccess;
    }

    public void tryDbAccess() {
        try {
            mapper = new SettingsMapper();
            dbaccess = true;
        } catch (SQLException e) {
            dbaccess = false;
        }
        init();
    }

    private void init() {
        try {
            login_required = !(mapper.getSetting("login-required").equals("false"));
        } catch (SQLException e) {
            dbaccess = false;
            login_required = false;
        }
    }

    public float getActorScale() {
        return actorScale;
    }

    public void setActorScale(float actorScale) {
        this.actorScale = actorScale;
    }

    public boolean isLoginRequired() {
        return login_required;
    }

    public static String getSqlUser() {
        return SQL_USER;
    }

    public static String getSqlPwd() {
        return SQL_PWD;
    }
}
