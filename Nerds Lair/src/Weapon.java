import java.util.Set;
import java.io.Serializable;

public class Weapon extends Item implements Serializable {
    private final int damageBoost;
    private final Rarity rarity;

    public Weapon(String name, int damageBoost, Rarity rarity, Set<PlayerClass> compatibleClasses) {
        super(name, compatibleClasses);
        this.damageBoost = damageBoost;
        this.rarity = rarity;
    }

    public int getDamageBoost() {
        return damageBoost;
    }

    public Rarity getRarity() {
        return rarity;
    }

    @Override
    public void use(Player p) {
        p.equipWeapon(this);
    }
}