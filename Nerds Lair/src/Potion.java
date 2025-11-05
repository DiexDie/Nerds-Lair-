import java.io.Serializable;
import java.util.Collections;


public class Potion extends Item implements Serializable {
    private final int healAmount;
    private final int manaRestoreAmount;


    public Potion(String name, int healAmount) {
        super(name, Collections.emptySet());
        this.healAmount = healAmount;
        this.manaRestoreAmount = 0;
    }


    public Potion(String name, int healAmount, int manaRestoreAmount) {
        super(name, Collections.emptySet());
        this.healAmount = healAmount;
        this.manaRestoreAmount = manaRestoreAmount;
    }


    public int getHealAmount() { return healAmount; }
    public int getManaRestoreAmount() { return manaRestoreAmount; }


    @Override
    public void use(Player p) {
        if (this.healAmount > 0) {
            p.heal(this.healAmount);
            System.out.println(p.getName() + " used potion and heals: " + healAmount + " HP!");
        }
        if (this.manaRestoreAmount > 0) {
            p.restoreMana(this.manaRestoreAmount);
            System.out.println(p.getName() + " restored " + manaRestoreAmount + " Mana!");
        }
    }
}