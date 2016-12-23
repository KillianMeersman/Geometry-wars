package howest.groep14.game;

/**
 * Created by Timo on 22/12/2016.
 */

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameMapper {

    private Connection connection;
    private String sqlUser, sqlPassword;

    public GameMapper(String sqlUser, String sqlPassword) throws SQLException {
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
            PreparedStatement prep = connection.prepareStatement("SELECT score FROM gameShips ORDER BY DESC LIMIT 1;");
            ResultSet results = prep.executeQuery();


            int score = results.getInt("score");

            return score;

        }catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }


    public void addGame(int gameID, int difficultyID, int gameMode, String level) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            PreparedStatement prep = connection.prepareStatement("INSERT INTO games" +
                    "(gameID, difficultyID, gameMode, level)" +
                    "VALUES (?, ?, ?, ?);");
            prep.setInt(1, gameID);
            prep.setInt(2, difficultyID);
            prep.setInt(3, gameMode);
            prep.setString(4,level);

            int affectedRows  = prep.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Game creation failed");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
