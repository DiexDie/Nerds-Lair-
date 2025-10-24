import java.util.Scanner;

public class OwlEvent extends Event {
    public OwlEvent() {
        super("O bufnita înțeleapta îți ofera un sfat mistic.");
    }

    @Override
    public void trigger(Player p) {
        System.out.println(description);
        System.out.println("1. O asculti\n2. O ignori");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        if (choice == 1) {
            System.out.println("Felicitari! Ai primit +1 Dex");
            p.dexterity += 1;
            System.out.println("Ai acum " + p.dexterity + " Dexterity.");
        } else {
            System.out.println("Ai ignorat bufnita.");
        }
    }
}
