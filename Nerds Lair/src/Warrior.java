public class Warrior extends Player {
    public Warrior(String name) {
        super(name, 100, 40, 2);
    }

    @Override
    public void attack(Mob m) {
        int damage = 20;
        if (equippedWeapon != null) damage += equippedWeapon.getDamageBoost();
        m.takeDamage(damage);
        System.out.println(" ∩༼˵☯‿☯˵༽つ¤=[]:::::>  " + name + " attacks " + m.getName() + " and deals " + damage + " damage!");
    }
}
