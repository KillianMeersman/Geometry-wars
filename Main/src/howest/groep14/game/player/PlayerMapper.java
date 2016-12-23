package howest.groep14.game.player;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


class PlayerMapper {
    private Connection connection;
    private String sqlUser, sqlPassword;

    public PlayerMapper(String sqlUser, String sqlPassword) throws SQLException {
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

    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<Player>();
        try {
            Class.forName("com.mysql.jdbc.Driver");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM players");

            BASE64Decoder dec = new BASE64Decoder();
            while (resultSet.next()) {
                try {
                    byte[] hash = dec.decodeBuffer(resultSet.getString("passwordHash"));
                    byte[] salt = dec.decodeBuffer(resultSet.getString("passwordSalt"));
                    Player player = new Player(resultSet.getInt("playerID"), resultSet.getString("username"), hash, salt);
                    players.add(player);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return players;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Player getPlayerByUsername(String username) throws Exception {
        try {
            PreparedStatement prep = connection.prepareStatement("SELECT * FROM players WHERE username = ?");

            prep.setString(1, username);
            ResultSet results = prep.executeQuery();

            if (results.next()) {
                int id = results.getInt("playerID");
                String email = results.getString("email");
                String passwordHash = results.getString("passwordHash");
                String passwordSalt = results.getString("passwordSalt");
                BASE64Decoder dec = new BASE64Decoder();
                try {
                    byte[] hash = dec.decodeBuffer(passwordHash);
                    byte[] salt = dec.decodeBuffer(passwordSalt);
                    Player player = new Player(id, username, hash, salt);
                    player.setEmail(email);
                    return player;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                throw new Exception("User not found");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int addPlayer(String username, String email, byte[] passwordHash, byte[] passwordSalt) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            PreparedStatement prep = connection.prepareStatement("INSERT INTO players" +
                    "(username, email, passwordHash, passwordSalt)" +
                    "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            BASE64Encoder enc = new BASE64Encoder();
            String hashBase64 = enc.encode(passwordHash);
            String saltBase64 = enc.encode(passwordSalt);

            prep.setString(1, username);
            prep.setString(2, email);
            prep.setString(3, hashBase64);
            prep.setString(4, saltBase64);

            int affectedRows  = prep.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("User creation failed");
            }
            ResultSet generatedKey = prep.getGeneratedKeys();
            if (generatedKey.next()) {
                return generatedKey.getInt(1);
            } else {
                throw new SQLException("User creation failed, no ID obtained");
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int addShip(int playerID, int health, int damage, int fireRate) {
        try {
            PreparedStatement prep = connection.prepareStatement("INSERT INTO ships (playerID, health, damage, fireRate) VALUES (?, ?, ?, ?)"
                    , Statement.RETURN_GENERATED_KEYS);
            prep.setInt(1, playerID);
            prep.setInt(2, health);
            prep.setInt(3, damage);
            prep.setInt(4, fireRate);

            int affectedRows  = prep.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Ship creation failed");
            }
            ResultSet generatedKey = prep.getGeneratedKeys();
            if (generatedKey.next()) {
                return generatedKey.getInt(1);
            } else {
                throw new SQLException("Ship creation failed, no ID obtained");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }


    public List<Ship> getShipsByPlayerID(Player player) {
        try {
            PreparedStatement prep = connection.prepareStatement("SELECT * FROM ships WHERE playerID = ?");
            prep.setInt(1, player.getId());
            ResultSet results = prep.executeQuery();
            List<Ship> ships = new ArrayList<Ship>();
            while (results.next()) {
                Ship ship = new Ship(results.getInt("shipID"), results.getInt("health"), results.getInt("damage"), results.getInt("fireRate"), player);
                ships.add(ship);
            }
            return ships;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void updatePlayer(Player player) {
        try {
            PreparedStatement prep = connection.prepareStatement("UPDATE players SET username = ?, email = ?, passwordHash = ?, passwordSalt = ?" +
                    "WHERE playerID = ?");
            prep.setString(1, player.getUsername());
            prep.setString(2, player.getEmail());
            BASE64Encoder enc = new BASE64Encoder();
            String hash = enc.encode(player.getPasswordHash());
            String salt = enc.encode(player.getPasswordSalt());
            prep.setString(3, hash);
            prep.setString(4, salt);
            prep.setInt(5, player.getId());

            prep.execute();

        } catch (SQLException e) {
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

    public String getSqlUser() {
        return sqlUser;
    }

    public void setSqlUser(String sqlUser) {
        this.sqlUser = sqlUser;
    }

    public String getSqlPassword() {
        return sqlPassword;
    }

    public void setSqlPassword(String sqlPassword) {
        this.sqlPassword = sqlPassword;
    }
}
