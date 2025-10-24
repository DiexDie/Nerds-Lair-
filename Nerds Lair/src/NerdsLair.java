import java.awt.*;
import java.util.Scanner;


public class NerdsLair{
    private Player player;
    String RESET = "\u001B[0m";
    String RED = "\u001B[31m";



    public void start() {
        Scanner sc = new Scanner(System.in);


        System.out.println(RED+ """
                ==================================================
                ▗▄     ▗▖                                ▗▖                \s                                ██ 
                ▐█     ▐▌                                ▐▌                \s                               
                ▐▛▌  ▐▌ ▟█▙     █▟█▌ ▟█▟▌ ▗▟██▖     ▐▌          ▟██▖      ██          █▟█▌
                ▐▌ █ ▐▌▐▙▄▟▌ █▘    ▐▛ ▜▌  ▐▙▄▖▘    ▐▌          ▘▄▟▌       █            █▘ \s
                ▐▌ ▐ ▟▌▐▛▀▀▘ █      ▐▌ ▐▌      ▀▀█▖    ▐▌        ▗█▀▜▌      █           █  \s
                ▐▌     █▌▝█▄▄▌ █     ▝█▄█▌  ▐▄▄▟▌    ▐▙▄▄▖▐▙▄█▌▗▄█▄▖      █  \s
                ▝▘     ▀▘ ▝▀▀      ▀      ▝▀▝▘      ▀▀▀      ▝▀▀▀▘   ▀▀▝▘ ▝▀▀▀▘     ▀  \s
                ==================================================
                """+RESET);

        System.out.print("Introdu numele personajului: ");
        String name = sc.nextLine();

        System.out.println("Alege clasa:");
        System.out.println("1. Warrior");
        System.out.println("2. Archer");
        int choice = sc.nextInt();

        if (choice == 1) {
            player = new Warrior(name);
        } else {
            player = new Archer(name);
        }

        System.out.println("Bun venit, " + player.getName() + "!");
        mainMenu();
    }

    private void mainMenu() {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== MENIU PRINCIPAL ===");
            System.out.println("1. Exploreaza");
            System.out.println("2. Vezi inventarul");
            System.out.println("3. Statistici");
            System.out.println("4. Iesire");
            System.out.print("Alege: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> explore();
                case 2 -> player.showInventory();
                case 3 -> System.out.println(player);
                case 4 -> {
                    running = false;
                    System.out.println("Jocul s-a inchis.");
                }
                default -> System.out.println("Opțiune invalida.");
            }
        }
    }


    private void explore() {
        Event event;
        java.util.Random rand = new java.util.Random();
        int chance = rand.nextInt(100);

        if (chance < 50) {
            event = new CombatEvent();
        } else if (chance < 75) {
            event = new ChestEvent();
        } else if (chance < 85) {
            event = new OwlEvent();
        } else if (chance < 87) {
            event = new GhostEvent();
        } else if (chance < 89) {
            event = new TraderEvent();
        } else { 
            event = new SudokuBookEvent();
        }

        event.trigger(player);
    }

    public static void main(String[] args) {
        new NerdsLair().start();
    }
}
