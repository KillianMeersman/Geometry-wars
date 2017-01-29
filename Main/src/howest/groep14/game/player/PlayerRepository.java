package howest.groep14.game.player;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class PlayerRepository {
    private List<Player> players;
    private PlayerMapper playerMapper;
    private Player activePlayer;

    private static PlayerRepository instance;

    public static void init() throws Exception {
        instance = new PlayerRepository("geometry-wars", "jEzPRAyKE6FsiiIjQXwq");
    }

    public static PlayerRepository getInstance() {
        return instance;
    }

    public void addGame() {

    }

    private PlayerRepository(String sqlUser, String sqlPassword) throws Exception {
        this.playerMapper = new PlayerMapper(sqlUser, sqlPassword);
        this.players = new ArrayList<Player>();
    }

    public List<Player> getPlayers() {
        return new ArrayList<Player>(players);
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public boolean loginPlayer(String username, String password) throws Exception {
        Player player = playerMapper.getPlayerByUsername(username);

        byte[] hash = getPasswordHash(password, player.getPasswordSalt());
        if (Arrays.equals(player.getPasswordHash(), hash)) {
            activePlayer = player;
            return true;
        } else {
            return false;
        }
    }

    public Player getPlayerByUsername(String username) throws Exception {
        for (Player player : players) {
            if (player.getUsername().equals(username)) {
                return player;
            }
        }
        Player player = playerMapper.getPlayerByUsername(username);
        players.add(player);
        return player;
    }

    public List<Ship> getPlayerShipsByPlayerID(Player player) {
        return playerMapper.getShipsByPlayerID(player);
    }

    public void updatePlayer(Player player) {
        playerMapper.updatePlayer(player);
    }

    public void updatePlayerPassword(String password, Player player) {
        player.setPasswordHash(getPasswordHash(password, player.getPasswordSalt()));
    }

    public Player createPlayer(String username, String email, String password) throws SQLException {
        byte[] salt = generateSalt();
        byte[] hash = getPasswordHash(password, salt);
        int id = playerMapper.addPlayer(username, email, hash, salt);
        Player player = new Player(id, username, hash, salt);
        players.add(player);
        id = playerMapper.addShip(player.getId(), 3, 1, 15);
        Ship ship = new Ship(id, 3, 1, 15, player);
        player.getShips().add(ship);
        return player;
    }

    private byte[] generateSalt() {
        Random random = new Random();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }

    private byte[] getPasswordHash(String password, byte[] salt) {
        try {
            KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
            SecretKeyFactory f = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            return f.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Player getActivePlayer() {
        return activePlayer;
    }
}
