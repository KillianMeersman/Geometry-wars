package howest.groep14.game.player;

import com.badlogic.gdx.Game;

import java.sql.*;

public class GameMapper {

    private Connection connection;
    private String sqlUser, sqlPassword;
    private static GameMapper instance;

    public static GameMapper getInstance() throws SQLException {
        if (instance == null) {
            instance = new GameMapper("geometry-wars", "jEzPRAyKE6FsiiIjQXwq");
        }
        return instance;
    }

    private GameMapper(String sqlUser, String sqlPassword) throws SQLException {
        this.sqlUser = sqlUser;
        this.sqlPassword = sqlPassword;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        DriverManager.setLoginTimeout(3);
        connection = DriverManager.getConnection("jdbc:mysql://sporegen.net/geometry-wars?"
                + "user="+sqlUser+"&password="+sqlPassword);
    }

    public void addHighscore(int gameID, int shipID, int score) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            PreparedStatement prep = connection.prepareStatement("INSERT INTO gameShips" +
                    "(gameID, shipID, score)" +
                    "VALUES (?, ?, ?);");
            prep.setInt(1, gameID);
            prep.setInt(2, shipID);
            prep.setInt(3, score);

            int affectedRows  = prep.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Score creation failed");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int getHighscore() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            PreparedStatement prep = connection.prepareStatement("SELECT score FROM gameShips ORDER BY DESC LIMIT 1;");
            ResultSet results = prep.executeQuery();

            int score = results.getInt("score");

            return score;

        }catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getHighScoreByShipID(int shipID) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            PreparedStatement prep = connection.prepareStatement("SELECT * FROM gameShips WHERE shipID = ? ORDER BY score DESC LIMIT 1;");
            prep.setInt(1, shipID);
            ResultSet results = prep.executeQuery();
            if (results.next()) {
                return results.getInt("score");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }


    public int addGame(int difficultyID, int gameMode, String level) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            PreparedStatement prep = connection.prepareStatement("INSERT INTO games" +
                    "(difficultyID, gameMode, level)" +
                    "VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            prep.setInt(1, difficultyID);
            prep.setInt(2, gameMode);
            prep.setString(3,level);

            int affectedRows  = prep.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Game creation failed");
            }
            ResultSet generatedKey = prep.getGeneratedKeys();
            if (generatedKey.next()) {
                return generatedKey.getInt(1);
            } else {
                throw new SQLException("Game creation failed, no ID obtained");
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void finalize() throws Throwable {
        super.finalize();
        closeConnection();
    }

    public void closeConnection() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
