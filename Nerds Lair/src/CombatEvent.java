import java.util.Random;
import java.util.Scanner;

public class CombatEvent extends Event {
    private Mob enemy;
    private int escapeChance = 80; // sansa de fuga
    private Random rand = new Random();

    public CombatEvent() {
        super("Un inamic apare!");
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
        super("Un inamic apare!");
        this.enemy = enemy;
    }

    @Override
    public void trigger(Player p) {
        Scanner sc = new Scanner(System.in);

        while (!enemy.isDead() && p.getHp() > 0) {
            showInterface(p, enemy);

            int choice = sc.nextInt();
            boolean consumaTura = false;

            switch (choice) {
                case 1: // Atacă
                    p.attack(enemy);
                    consumaTura = true;
                    if (enemy.isDead()) {
                        showInterface(p, enemy);
                        System.out.println("Ai invins " + enemy.getName() + "!");
                        p.addExp(10);
                        return;
                    }
                    break;

                case 2: // Foloseste item
                    useItem(p);
                    break;

                case 3: // Echipare arme/armuri
                    equipItem(p);
                    break;

                case 4: // Inventar
                    p.showInventory();
                    System.out.println("Apasa Enter pentru a continua...");
                    sc.nextLine(); sc.nextLine();
                    break;

                case 5: // Fugi
                    int valoare = rand.nextInt(100) + 1;
                    if (valoare <= escapeChance) {
                        System.out.println("Ai reusit sa fugi din lupta!");
                        return;
                    } else {
                        System.out.println("Ai incercat sa fugi, dar ai ramas blocat în lupta!");
                        escapeChance -= 20;
                        if (escapeChance < 10) escapeChance = 10;
                        consumaTura = true; // dacă nu reușești, inamicul lovește
                    }
                    break;

                default:
                    System.out.println("Optiune invalida.");
                    continue;
            }


            if (consumaTura && !enemy.isDead()) {
                int dmg = enemy.attack();
                System.out.println(enemy.getName() + "ﺤ══════ι▭▭༼ຈل͜ຈ༽"+" te loveste pentru " + dmg + " damage!");
                p.takeDamage(dmg);

                if (p.getHp() <= 0) {
                    System.out.println("""
                        
                        
                        ▗▖  ▗▖  ▗▄▖   ▗▖  ▗▖    ▗▄▖    ▗▄▄▖  ▗▄▄▄▖     ▗▄▄▄     ▗▄▄▄▖   ▗▄▖         ▗▄▄▄
                         ▝▚▞▘▐▌  ▐▌ ▐▌  ▐▌   ▐▌ ▐▌ ▐▌ ▐▌  ▐▌            ▐▌    █     ▐▌         ▐▌ ▐▌     ▐▌     █
                            ▐▌  ▐▌   ▐▌▐▌  ▐▌   ▐▛▀▜▌▐▛▀▚▖▐▛▀▀▘    ▐▌    █     ▐▛▀▀▘▐▛▀▜▌    ▐▌     █
                            ▐▌  ▝▚▄▞▘▝▚▄▞▘   ▐▌ ▐▌ ▐▌ ▐▌  ▐▙▄▄▖    ▐▙▄▄▀   ▐▙▄▄▖▐▌   ▐▌    ▐▙▄▄▀
                        
                        
                        """);
                    System.exit(0);
                }

                System.out.println("Apasa Enter pentru a continua...");
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
            System.out.println("| Arma: " + ((p.equippedWeapon != null) ? p.equippedWeapon.getName() : "Nicio arma") + " |");
            System.out.println("| Armura: " + ((p.equippedArmor != null) ? p.equippedArmor.getName() : "Nicio armura") + " |");
            System.out.println("+----------------------+");

            System.out.println("\nEveniment: Te confrunți cu " + enemy.getName() + " HP: " + enemy.getHp());
            System.out.println("1. Ataca");
            System.out.println("2. Foloseste item");
            System.out.println("3. Echipare arme/armuri");
            System.out.println("4. Vezi inventarul");
            System.out.println("5. Fugi");
            System.out.print("Alege: ");
        }else if(p.getHp()>=33&&p.getHp()<=66)
        {
            clearScreen();
            System.out.println("+----------------------+");
            System.out.println("| HP: " + YELLOW +p.getHp()+RESET + "/100          |");
            System.out.println("| Mana: " + p.getMana() + "/100       |");
            System.out.println("| Exp: " + p.getExp() + "            |");
            System.out.println("| Arma: " + ((p.equippedWeapon != null) ? p.equippedWeapon.getName() : "Nicio arma") + " |");
            System.out.println("| Armura: " + ((p.equippedArmor != null) ? p.equippedArmor.getName() : "Nicio armura") + " |");
            System.out.println("+----------------------+");

            System.out.println("\nEveniment: Te confrunți cu " + enemy.getName() + " HP: " + enemy.getHp());
            System.out.println("1. Ataca");
            System.out.println("2. Foloseste item");
            System.out.println("3. Echipare arme/armuri");
            System.out.println("4. Vezi inventarul");
            System.out.println("5. Fugi");
            System.out.print("Alege: ");
        }else if(p.getHp()<=33)
        {
            clearScreen();
            System.out.println("+----------------------+");
            System.out.println("| HP: " + RED +p.getHp()+RESET + "/100          |");
            System.out.println("| Mana: " + p.getMana() + "/100       |");
            System.out.println("| Exp: " + p.getExp() + "            |");
            System.out.println("| Arma: " + ((p.equippedWeapon != null) ? p.equippedWeapon.getName() : "Nicio arma") + " |");
            System.out.println("| Armura: " + ((p.equippedArmor != null) ? p.equippedArmor.getName() : "Nicio armura") + " |");
            System.out.println("+----------------------+");

            System.out.println("\nEveniment: Te confrunți cu " + enemy.getName() + " HP: " + enemy.getHp());
            System.out.println("1. Ataca");
            System.out.println("2. Foloseste item");
            System.out.println("3. Echipare arme/armuri");
            System.out.println("4. Vezi inventarul");
            System.out.println("5. Fugi");
            System.out.print("Alege: ");
        }

    }


    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    private void useItem(Player p) {
        Scanner sc = new Scanner(System.in);
        if (p.getInventory().isEmpty()) {
            System.out.println("Inventarul este gol!");
            return;
        }

        p.showInventory();
        System.out.print("Alege itemul de folosit (număr): ");
        int index = sc.nextInt() - 1;

        if (index >= 0 && index < p.getInventory().size()) {
            Item item = p.getInventory().get(index);
            if (item instanceof Potion) {
                item.use(p);
                p.getInventory().remove(index);
            } else {
                System.out.println("Poți folosi doar poțiuni aici!");
            }
        } else {
            System.out.println("Opțiune invalidă.");
        }
    }

    private void equipItem(Player p) {
        Scanner sc = new Scanner(System.in);
        if (p.getInventory().isEmpty()) {
            System.out.println("Inventarul este gol!");
            return;
        }

        p.showInventory();
        System.out.print("Alege itemul de echipat (număr): ");
        int index = sc.nextInt() - 1;

        if (index >= 0 && index < p.getInventory().size()) {
            Item item = p.getInventory().get(index);
            if (item instanceof Weapon) {
                p.equipWeapon((Weapon) item);
            } else if (item instanceof Armor) {
                p.equipArmor((Armor) item);
            } else {
                System.out.println("Acest item nu poate fi echipat!");
            }
        } else {
            System.out.println("Opțiune invalidă.");
        }
    }
}
