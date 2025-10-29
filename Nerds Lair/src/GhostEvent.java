import java.util.Scanner;

public class GhostEvent extends Event {
    public GhostEvent() {
        super("A friendly ghost wants to give you energy!");
    }

    @Override
    public void trigger(Player p) {
        Scanner sc = new Scanner(System.in);
        System.out.println(description + "\n1. Accept\n2. Refuse");
        int choice = sc.nextInt();

        if (choice == 1) {
            p.heal(20);
            System.out.println("The ghost gave you+20 HP!");
        } else {
            System.out.println("You refused ghost help.");
        }
    }
}
