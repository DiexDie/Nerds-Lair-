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

        System.out.print("Type a name for your Hero!: ");
        String name = sc.nextLine();

        System.out.println("Choose your Class:");
        System.out.println("1. Warrior");
        System.out.println("2. Archer");
        int choice = sc.nextInt();

        if (choice == 1) {
            player = new Warrior(name);
        } else {
            player = new Archer(name);
        }

        System.out.println("Welcome " + player.getName() + "!");
        mainMenu();
    }

    private void mainMenu() {
        Scanner sc = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== MENU ===");
            System.out.println("1. Explore");
            System.out.println("2. Inventoryl");
            System.out.println("3. Statistics");
            System.out.println("4. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> explore();
                case 2 -> player.showInventory();
                case 3 -> System.out.println(player);
                case 4 -> {
                    running = false;
                    System.out.println("The game has closed!.");
                }
                default -> System.out.println("Invalid Option.");
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
