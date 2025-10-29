public class SudokuBookEvent extends Event {
    String RESET = "\u001B[0m";
    String RED = "\u001B[31m";
    public SudokuBookEvent() {
    }

    @Override
    public void trigger(Player p) {
        System.out.println("You found a sudoku book on the floor, what do you do?");

        System.out.println("1. Step on it");

        System.out.println("2. Pick it up");

        java.util.Scanner sc = new java.util.Scanner(System.in);
        int choice = sc.nextInt();

        if (choice == 1) {
            Mob sudokuKing = new Mob(RED+"Sudoku King Edi"+RESET, 275, 45, 75);
            System.out.println("The book transforms into " + sudokuKing.getName() + "!");
            CombatEvent combat = new CombatEvent(sudokuKing);
            combat.trigger(p);
        } else {
            p.addDexterity(2);
            System.out.println("You received +2 Dexterity!");
        }
    }
}