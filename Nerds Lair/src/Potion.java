public class Potion extends Item {
    private int healAmount;

    public Potion(String name, int healAmount) {
        super(name);
        this.healAmount = healAmount;
    }

    @Override
    public void use(Player p) {
        p.heal(healAmount);
        System.out.println(p.getName() + " used potion and heals: " + healAmount + " HP!");
    }
}
