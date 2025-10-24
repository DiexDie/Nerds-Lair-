public class Weapon extends Item {
    private int damageBoost;

    public Weapon(String name, int damageBoost) {
        super(name);
        this.damageBoost = damageBoost;
    }

    public int getDamageBoost() {
        return damageBoost;
    }

    @Override
    public void use(Player p) {
        p.equipWeapon(this);
    }
}
