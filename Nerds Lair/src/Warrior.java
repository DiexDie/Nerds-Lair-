public class Warrior extends Player {
    public Warrior(String name) {
        super(name, 100, 100, 2,PlayerClass.WARRIOR);
    }

    @Override
    public int attack(Mob m) {
        int damage = 20;
        if (equippedWeapon != null) damage += equippedWeapon.getDamageBoost();
        m.takeDamage(damage);
        return damage;
    }
}