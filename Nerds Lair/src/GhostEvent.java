import java.util.Scanner;

public class GhostEvent extends Event {
    public GhostEvent() {
        super("O fantoma prietenoasa vrea sa iti redea energie.");
    }

    @Override
    public void trigger(Player p) {
        Scanner sc = new Scanner(System.in);
        System.out.println(description + "\n1. Accepti\n2. Refuzi");
        int choice = sc.nextInt();

        if (choice == 1) {
            p.heal(20);
            System.out.println("Fantoma ti-a dat +20 HP!");
        } else {
            System.out.println("Ai refuzat ajutorul fantomei.");
        }
    }
}
