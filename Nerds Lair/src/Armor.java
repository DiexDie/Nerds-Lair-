import java.util.Set;
import java.io.Serializable;

public class Armor extends Item implements Serializable {
    private final int defenseBoost;
    private final Rarity rarity;


    public Armor(String name, int defenseBoost, Rarity rarity, Set<PlayerClass> compatibleClasses) {
        super(name, compatibleClasses);
        this.defenseBoost = defenseBoost;
        this.rarity = rarity;
    }

    public int getDefenseBoost() {
        return defenseBoost;
    }

    public Rarity getRarity() {
        return rarity;
    }

    @Override
    public void use(Player p) {
        p.equipArmor(this);
    }
}