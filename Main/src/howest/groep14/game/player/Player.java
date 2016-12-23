package howest.groep14.game.player;

import java.util.ArrayList;
import java.util.List;

public class Player {
    private int id;
    private String username, email;
    private byte[] passwordHash, passwordSalt;
    private List<Ship> ships = new ArrayList<Ship>();

    public Player(int id, String username, byte[] passwordHash, byte[] passwordSalt) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.passwordSalt = passwordSalt;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(byte[] passwordHash) {
        this.passwordHash = passwordHash;
    }

    public byte[] getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(byte[] passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public List<Ship> getShips() {
        return ships;
    }
}
