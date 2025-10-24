public class SudokuBookEvent extends Event {
    String RESET = "\u001B[0m";
    String RED = "\u001B[31m";
    public SudokuBookEvent() {
    }

    @Override
    public void trigger(Player p) {
        System.out.println("Ai gasit o carte de sudoku pe jos,ce faci?");
        System.out.println("1. O calci ");
        System.out.println("2. O ridici ");

        java.util.Scanner sc = new java.util.Scanner(System.in);
        int choice = sc.nextInt();

        if (choice == 1) {
            Mob sudokuKing = new Mob(RED+"Sudoku King Edi"+RESET, 275, 45, 75);
            System.out.println("Cartea se transforma în " + sudokuKing.getName() + "!");
            CombatEvent combat = new CombatEvent(sudokuKing);
            combat.trigger(p);
        } else {
            p.addDexterity(2);
            System.out.println("Ai primit +2 Dexterity!");
        }


    }
}
