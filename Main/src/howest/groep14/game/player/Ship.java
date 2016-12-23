package howest.groep14.game.player;

public class Ship {
    private int ID, health, damage, fireRate;
    private Player player;

    public Ship(int ID, int health, int damage, int fireRate, Player player) {
        this.ID = ID;
        this.health = health;
        this.fireRate = fireRate;
        this.damage = damage;
        this.player = player;
    }

    public int getID() {
        return ID;
    }

    public int getHealth() {
        return health;
    }

    public int getFireRate() {
        return fireRate;
    }

    public Player getPlayer() {
        return player;
    }

    public int getDamage() {
        return damage;
    }
}
