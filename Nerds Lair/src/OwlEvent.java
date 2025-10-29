import java.util.Scanner;

public class OwlEvent extends Event {
    public OwlEvent() {
        super("A wise Owl gives you mystical advice");
    }

    @Override
    public void trigger(Player p) {
        System.out.println(description);
        System.out.println("1. You listen to her\n2. Ignore her");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        if (choice == 1) {
            System.out.println("Congratulations! You received +1 Dex");
            p.dexterity += 1;
            System.out.println("You now have " + p.dexterity + " Dexterity.");
        } else {
            System.out.println("You ignored Owl.");
        }
    }
}
