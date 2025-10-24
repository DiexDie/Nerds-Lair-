import java.util.Scanner;

public class TraderEvent extends Event {
    public TraderEvent() {
        super("Un negustor dubios iti ofera o potiune pentru 10 XP.");
    }

    @Override
    public void trigger(Player p) {
        Scanner sc = new Scanner(System.in);
        System.out.println(description + "\n1. Cumpara\n2. Refuza");
        int choice = sc.nextInt();

        if (choice == 1) {
            if (p.getHp() > 0) {
                p.heal(20);
                System.out.println("Ai cumparat o potiune (+20 HP).");
            }
        } else {
            System.out.println("Ai refuzat negustorul.");
        }
    }
}
