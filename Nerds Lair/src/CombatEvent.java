import java.util.Random;
import java.util.Scanner;

public class CombatEvent extends Event {
    private Mob enemy;
    private int escapeChance = 80; // sansa de fuga
    private Random rand = new Random();

    public CombatEvent() {
        super("An enemy appears!");
        Mob[] mobs = {
                new Mob(" 👺 Goblin 👺", 80, 10, 20),
                new Mob("🧙🏼 Dark Wizard 🧙🏼", 75, 15, 25),
                new Mob("🦴 Skeleton 🦴", 40,  5, 15),
                new Mob("🤪  Dwarf 🤪", 60, 10, 18),
                new Mob(" 🦴🚩 Skeleton General 🚩🦴 ", 100, 20, 30)
        };
        this.enemy = mobs[rand.nextInt(mobs.length)];
    }

    public CombatEvent(Mob enemy) {
        super("An enemy appears");
        this.enemy = enemy;
    }

    @Override
    public void trigger(Player p) {
        String RESET = "\u001B[0m";
        String RED = "\u001B[31m";
        Scanner sc = new Scanner(System.in);

        while (!enemy.isDead() && p.getHp() > 0) {
            showInterface(p, enemy);

            int choice = sc.nextInt();
            boolean consumesShift = false;

            switch (choice) {
                case 1: //attack sys
                    p.attack(enemy);
                    consumesShift = true;
                    if (enemy.isDead()) {
                        showInterface(p, enemy);
                        System.out.println("You have slain:  " + enemy.getName() + "!");
                        p.addExp(10);
                        return;
                    }
                    break;

                case 2: // useItem
                    useItem(p);
                    break;

                case 3: // Equipment system for Armor and Weapon
                    equipItem(p);
                    break;

                case 4: // Inventory
                    p.showInventory();
                    System.out.println("Press Enter to continue.....");
                    sc.nextLine(); sc.nextLine();
                    break;

                case 5: // run system
                    int valoare = rand.nextInt(100) + 1;
                    if (valoare <= escapeChance) {
                        System.out.println("You managed to escape from fight!");
                        return;
                    } else {
                        System.out.println("You tried to run,but you got stuck in the fight!");
                        escapeChance -= 20;
                        if (escapeChance < 10) escapeChance = 10;
                        consumesShift = true;
                    }
                    break;

                default:
                    System.out.println("Invalid option.");
                    continue;
            }


            if (consumesShift && !enemy.isDead()) {
                int dmg = enemy.attack();
                System.out.println(enemy.getName() + "ﺤ══════ι▭▭༼ຈل͜ຈ༽"+" attacks you for " + dmg + " damage!");
                p.takeDamage(dmg);

                if (p.getHp() <= 0) {
                    System.out.println(RED+"""
                        
                        
                        ▗▖  ▗▖  ▗▄▖   ▗▖  ▗▖    ▗▄▖    ▗▄▄▖  ▗▄▄▄▖     ▗▄▄▄     ▗▄▄▄▖   ▗▄▖         ▗▄▄▄
                         ▝▚▞▘▐▌  ▐▌ ▐▌  ▐▌   ▐▌ ▐▌ ▐▌ ▐▌  ▐▌            ▐▌    █     ▐▌         ▐▌ ▐▌     ▐▌     █
                            ▐▌  ▐▌   ▐▌▐▌  ▐▌   ▐▛▀▜▌▐▛▀▚▖▐▛▀▀▘    ▐▌    █     ▐▛▀▀▘▐▛▀▜▌    ▐▌     █
                            ▐▌  ▝▚▄▞▘▝▚▄▞▘   ▐▌ ▐▌ ▐▌ ▐▌  ▐▙▄▄▖    ▐▙▄▄▀   ▐▙▄▄▖▐▌   ▐▌    ▐▙▄▄▀
                        
                        
                        """+RESET);
                    System.exit(0);
                }

                System.out.println("Press Enter to continue.....");
                sc.nextLine(); sc.nextLine();
            }
        }
    }


    private void showInterface(Player p, Mob enemy) {

        String RED = "\u001B[31m";
        String GREEN = "\u001B[32m";
        String YELLOW = "\u001B[33m";
        String RESET = "\u001B[0m";

        if(p.getHp()>=66&&p.getHp()<=1000)
        {
            clearScreen();
            System.out.println("+----------------------+");
            System.out.println("| HP: " + GREEN +p.getHp()+RESET + "/100          |");
            System.out.println("| Mana: " + p.getMana() + "/100       |");
            System.out.println("| Exp: " + p.getExp() + "            |");
            System.out.println("| Weapon: " + ((p.equippedWeapon != null) ? p.equippedWeapon.getName() : "No Weapon") + " |");
            System.out.println("| Armor: " + ((p.equippedArmor != null) ? p.equippedArmor.getName() : "No Armor") + " |");
            System.out.println("+----------------------+");

            System.out.println("\nEvent: You are fighting:  " + enemy.getName() + " HP: " + enemy.getHp());
            System.out.println("1. Attack");
            System.out.println("2. Use item");
            System.out.println("3. Equip Armor/Weapon");
            System.out.println("4. Inventory");
            System.out.println("5. Run");
            System.out.print("Choose: ");
        }else if(p.getHp()>=33&&p.getHp()<=66)
        {
            clearScreen();
            System.out.println("+----------------------+");
            System.out.println("| HP: " + YELLOW +p.getHp()+RESET + "/100          |");
            System.out.println("| Mana: " + p.getMana() + "/100       |");
            System.out.println("| Exp: " + p.getExp() + "            |");
            System.out.println("| Weapon: " + ((p.equippedWeapon != null) ? p.equippedWeapon.getName() : "No Weapon") + " |");
            System.out.println("| Armor: " + ((p.equippedArmor != null) ? p.equippedArmor.getName() : "No Armor") + " |");
            System.out.println("+----------------------+");

            System.out.println("\nEvent: You are fighting: " + enemy.getName() + " HP: " + enemy.getHp());
            System.out.println("1. Attack");
            System.out.println("2. Use item");
            System.out.println("3. Equip Armor/Weaponi");
            System.out.println("4. Inventory");
            System.out.println("5. Run");
            System.out.print("Choose: ");
        }else if(p.getHp()<=33)
        {
            clearScreen();
            System.out.println("+----------------------+");
            System.out.println("| HP: " + RED +p.getHp()+RESET + "/100          |");
            System.out.println("| Mana: " + p.getMana() + "/100       |");
            System.out.println("| Exp: " + p.getExp() + "            |");
            System.out.println("| Weapon: " + ((p.equippedWeapon != null) ? p.equippedWeapon.getName() : "No Weapon") + " |");
            System.out.println("| Armor: " + ((p.equippedArmor != null) ? p.equippedArmor.getName() : "No Armor") + " |");
            System.out.println("+----------------------+");

            System.out.println("\nEvent: You are fighting: " + enemy.getName() + " HP: " + enemy.getHp());
            System.out.println("1. Attack");
            System.out.println("2. Use item");
            System.out.println("3. Equip Armor/Weapon");
            System.out.println("4. Inventory");
            System.out.println("5. Run");
            System.out.print("Choose: ");
        }

    }


    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void useItem(Player p) {
        Scanner sc = new Scanner(System.in);
        if (p.getInventory().isEmpty()) {
            System.out.println("Inventory is empty!");
            return;
        }

        p.showInventory();
        System.out.print("Choose a item! (number): ");
        int index = sc.nextInt() - 1;

        if (index >= 0 && index < p.getInventory().size()) {
            Item item = p.getInventory().get(index);
            if (item instanceof Potion) {
                item.use(p);
                p.getInventory().remove(index);
            } else {
                System.out.println("You can only use potions!!");
            }
        } else {
            System.out.println("Invalid Option.");
        }
    }

    private void equipItem(Player p) {
        Scanner sc = new Scanner(System.in);
        if (p.getInventory().isEmpty()) {
            System.out.println("Inventory is empty!");
            return;
        }

        p.showInventory();
        System.out.print("Choose a item!(number): ");
        int index = sc.nextInt() - 1;

        if (index >= 0 && index < p.getInventory().size()) {
            Item item = p.getInventory().get(index);
            if (item instanceof Weapon) {
                p.equipWeapon((Weapon) item);
            } else if (item instanceof Armor) {
                p.equipArmor((Armor) item);
            } else {
                System.out.println("This item cannot be equipped!");
            }
        } else {
            System.out.println("Invalid Option.");
        }
    }
}
