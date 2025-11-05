import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public abstract class Player implements Serializable {
    protected String name;
    protected int hp;
    protected int mana;
    protected int coins;
    protected int dexterity;
    protected int strength;

    protected final PlayerClass playerClass;
    protected Weapon equippedWeapon;
    protected Armor equippedArmor;
    protected List<Item> inventory;


    public Player(String name, int hp, int mana, int dexterity, PlayerClass playerClass) {
        this.name = name;
        this.hp = hp;
        this.mana = mana;
        this.coins = 0;
        this.dexterity = dexterity;
        this.playerClass = playerClass;
        this.inventory = new ArrayList<>();
        this.strength = 0;
    }


    public Player(String name, int hp, int mana, PlayerClass playerClass) {
        this(name, hp, mana, 0, playerClass);
    }


    public String getName() { return name; }
    public int getHp() { return hp; }
    public int getMana() { return mana; }
    public int getCoins() { return coins; }
    public int getDexterity() { return dexterity; }
    public int getStrength() { return strength; }
    public List<Item> getInventory() { return inventory; }
    public PlayerClass getPlayerClass() { return playerClass; }


    public void setMana(int newMana) {
        this.mana = newMana;
        if (this.mana < 0) this.mana = 0;
        if (this.mana > 100) this.mana = 100;
    }

    public void setHp(int newHp) {
        this.hp = newHp;
        if (this.hp < 0) this.hp = 0;
        if (this.hp > 100) this.hp = 100;
    }

    public void addStrength(int amount) {
        this.strength += amount;
    }

    public void restoreMana(int amount) {
        this.mana += amount;
        if (this.mana > 100) this.mana = 100;
    }

    public void addDexterity(int amount) { dexterity += amount; }
    public void addcoins(int amount) { coins += amount; }


    public void takeDamage(int dmg) {

        if (CheatManager.isGodModeActive()) {
            System.out.println("INVULNERABLE: Damage ignored due to active cheat.");
            return;
        }


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
        if (!w.getCompatibleClasses().contains(this.playerClass) && !w.getCompatibleClasses().isEmpty()) {
            System.out.println("❌ ERROR: Weapon " + w.getName() + " isn't compatible with your class(" + this.playerClass + ")!");
            return;
        }
        this.equippedWeapon = w;
        System.out.println(name + " You equipped Weapon: " + w.getName());
    }


    public void equipArmor(Armor a) {
        if (!a.getCompatibleClasses().contains(this.playerClass) && !a.getCompatibleClasses().isEmpty()) {
            System.out.println("❌ ERROR: Armor " + a.getName() + " isn't compatible with your class (" + this.playerClass + ")!");
            return;
        }
        this.equippedArmor = a;
        System.out.println(name + " You equipped Armor: " + a.getName());
    }

    public void addItem(Item i) {
        if (i instanceof Potion) {
            inventory.add(i);
            System.out.println(name + " received " + i.getName());
            return;
        }

        boolean isDuplicate = inventory.stream().anyMatch(existingItem ->
                existingItem.getClass().equals(i.getClass()) && existingItem.getName().equals(i.getName())
        );

        if (isDuplicate) {
            final int COINS_REWARD = 25;
            this.addcoins(COINS_REWARD);
            System.out.println("⚠️ Duplicate found: " + i.getName() + " converted into " + COINS_REWARD + " Coins!");
        } else {
            inventory.add(i);
            System.out.println(name + " received " + i.getName());
        }
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
            else if (item instanceof Spell) type = " [Spell]";
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
        }
        else {
            System.out.println("You can only use potions with this option!");
        }
    }


    public abstract int attack(Mob m);


    @Override
    public String toString() {
        String weapon = (equippedWeapon != null) ? equippedWeapon.getName() : "No Weapon";
        String armor = (equippedArmor != null) ? equippedArmor.getName() : "No Armor";
        return name + " [Class: " + playerClass + ", HP: " + hp + ", Mana: " + mana + ", Exp: " + coins +
                ", Dex: " + dexterity + ", Str: " + strength + ", Weapon: " + weapon + ", Armor: " + armor + "]";
    }
}