import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    protected String name;
    protected int hp;
    protected int mana;
    protected int exp;
    protected int dexterity;

    protected Weapon equippedWeapon;
    protected Armor equippedArmor;
    protected List<Item> inventory;


    public Player(String name, int hp, int mana, int dexterity) {
        this.name = name;
        this.hp = hp;
        this.mana = mana;
        this.exp = 0;
        this.dexterity = dexterity;
        this.inventory = new ArrayList<>();
    }


    public Player(String name, int hp, int mana) {
        this(name, hp, mana, 0);
    }


    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMana() { return mana; }
    public int getExp() { return exp; }
    public int getDexterity() { return dexterity; }
    public List<Item> getInventory() { return inventory; }


    public void addDexterity(int amount) { dexterity += amount; }
    public void addExp(int amount) { exp += amount; }


    public void takeDamage(int dmg) {
        if (equippedArmor != null) {
            dmg -= equippedArmor.getDefenseBoost();
            if (dmg < 0) dmg = 0;
        }
        hp -= dmg;
        if (hp < 0) hp = 0;
    }

    public void heal(int amount) {
        hp += amount;
        if (hp > 100) hp = 100;
    }


    public void equipWeapon(Weapon w) {
        this.equippedWeapon = w;
        System.out.println(name + " You equipped Weapon: " + w.getName());
    }

    public void equipArmor(Armor a) {
        this.equippedArmor = a;
        System.out.println(name + " You equipped Armor: " + a.getName());
    }


    public void addItem(Item i) {
        inventory.add(i);
        System.out.println(name + " received " + i.getName());
    }

    public void showInventory() {
        System.out.println("\n===" + name +"'s "+"INVENTORY ===");
        if (inventory.isEmpty()) {
            System.out.println("Inventory is empty.");
            return;
        }
        for (int i = 0; i < inventory.size(); i++) {
            Item item = inventory.get(i);
            String type = "";
            if (item instanceof Weapon) type = " [Weapon]";
            else if (item instanceof Armor) type = " [Armor]";
            else if (item instanceof Potion) type = " [Potion]";
            System.out.println((i + 1) + ". " + item.getName() + type);
        }
    }


    public void useItem(int index) {
        if (index < 0 || index >= inventory.size()) {
            System.out.println("Invalid index!");
            return;
        }

        Item item = inventory.get(index);
        if (item instanceof Potion) {
            item.use(this);
            inventory.remove(index);
        } else {
            System.out.println("You can only use potions with this option!");
        }
    }


    public abstract void attack(Mob m);

    @Override
    public String toString() {
        String weapon = (equippedWeapon != null) ? equippedWeapon.getName() : "No Weapon";
        String armor = (equippedArmor != null) ? equippedArmor.getName() : "No Armor";
        return name + " [HP: " + hp + ", Mana: " + mana + ", Exp: " + exp +
                ", Dex: " + dexterity + ", Weapon: " + weapon + ", Armor: " + armor + "]";
    }
}
