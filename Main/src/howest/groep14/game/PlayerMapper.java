package howest.groep14.game;

import sun.misc.BASE64Encoder;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;


public class PlayerMapper {
    private Connection connection;
    private String sqlUser, sqlPassword;

    public PlayerMapper(String sqlUser, String sqlPassword) throws SQLException, ClassNotFoundException {
        this.sqlUser = sqlUser;
        this.sqlPassword = sqlPassword;

        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://sporegen.net/geometry-wars?"
                + "user="+sqlUser+"&password="+sqlPassword);
    }

    public List<Player> getPlayers() {
        List<Player> players = new ArrayList<Player>();
        try {
            Class.forName("com.mysql.jdbc.Driver");

            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM players");

            while (resultSet.next()) {
                Player player = new Player(resultSet.getInt("playerID"), resultSet.getString("username"),
                        resultSet.getString("passwordHash"), resultSet.getString("passwordSalt"));
                players.add(player);
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
            PreparedStatement prep = connection.prepareStatement("SELECT * FROM players WHERE username = '"+username+"'");

            ResultSet results = prep.executeQuery();

            if (results.next()) {
                int id = results.getInt("playerID");
                String email = results.getString("email");
                String passwordHash = results.getString("passwordHash");
                String passwordSalt = results.getString("passwordSalt");
                Player player = new Player(id, username, passwordHash, passwordSalt);
                player.setEmail(email);
                return player;
            } else {
                throw new Exception("User not found");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int addPlayer(String username, String email, String password) throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            PreparedStatement prep = connection.prepareStatement("INSERT INTO players" +
                    "(username, email, passwordHash, passwordSalt)" +
                    "VALUES (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);

            Random random = new Random();
            byte[] salt = new byte[16];
            random.nextBytes(salt);
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            byte[] hash = f.generateSecret(spec).getEncoded();
            BASE64Encoder enc = new BASE64Encoder();
            String hashBase64 = enc.encode(hash);
            String saltBase64 = enc.encode(salt);

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
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
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
