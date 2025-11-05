public class Archer extends Player {
    public Archer(String name) {
        super(name, 100, 100, 3,PlayerClass.ARCHER);
    }

    @Override
    public int attack(Mob m) {
        int damage = 18;
        if (equippedWeapon != null) damage += equippedWeapon.getDamageBoost();
        m.takeDamage(damage);
        return damage;
    }
}