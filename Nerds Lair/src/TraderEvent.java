import java.util.Scanner;

public class TraderEvent extends Event {
    public TraderEvent() {
        super("A shady merchant offers you a potion for 10 XP.");
    }

    @Override
    public void trigger(Player p) {
        Scanner sc = new Scanner(System.in);
        System.out.println(description + "\n1. Buy\n2. Refuse");
        int choice = sc.nextInt();

        if (choice == 1) {
            if (p.getHp() > 0) {
                p.heal(20);
                System.out.println("You bought a potion (+20 HP).");
            }
        } else {
            System.out.println("You refused the merchant.");
        }
    }
}