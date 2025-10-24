public class Archer extends Player {
    public Archer(String name) {
        super(name, 100, 60, 5);
    }

    @Override
    public void attack(Mob m) {
        int damage = 18;
        if (equippedWeapon != null) damage += equippedWeapon.getDamageBoost();
        m.takeDamage(damage);
        System.out.println(" ∩༼˵☯‿☯˵༽つ¤=[]:::::>  " + name + " ataca " + m.getName() + " și provoaca " + damage + " damage!");
    }
}