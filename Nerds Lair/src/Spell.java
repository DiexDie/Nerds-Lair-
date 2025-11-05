import java.util.Set;


public class Spell extends Item {
    private final int damage;
    private final int manaCost;
    private final Rarity rarity;

    public Spell(String name, int damage, int manaCost, Rarity rarity, Set<PlayerClass> compatibleClasses) {
        super(name, compatibleClasses);
        this.damage = damage;
        this.manaCost = manaCost;
        this.rarity = rarity;
    }


    public int getDamage() {
        return damage;
    }

    public int getManaCost() {
        return manaCost;
    }

    public Rarity getRarity() {
        return rarity;
    }


    @Override
    public void use(Player p) {
        System.out.println(p.getName() + " equipped the spell " + this.getName() + ".");
    }
}