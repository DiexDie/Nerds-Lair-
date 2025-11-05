import javax.swing.*;

public class SudokuBookEvent extends Event {
    private GameScreenForm gameScreen;
    private Player currentPlayer;

    public SudokuBookEvent() {
    }

    @Override
    public void trigger(Player p, GameScreenForm screen) {
        this.currentPlayer = p;
        this.gameScreen = screen;


        String initialLog = "You found a sudoku book on the floor, what do you do?";
        String activeCheat = CheatManager.getActiveCheatCode();
        if (activeCheat != null) {
            initialLog = "CHEAT (" + activeCheat + ") Active. " + initialLog;
        }


        gameScreen.setNewLog(initialLog);
        gameScreen.displayMessage("Choose your action:");
        gameScreen.updateStatsDisplay();

        setupChoiceButtons();
    }

    private void setupChoiceButtons() {
        gameScreen.setChoiceButtons(false);

        JButton btnStep = new JButton("1. Step on it");
        btnStep.addActionListener(e -> handleStepOnIt());
        gameScreen.setChoiceButton(1, btnStep);

        JButton btnPick = new JButton("2. Pick it up");
        btnPick.addActionListener(e -> handlePickUp());
        gameScreen.setChoiceButton(2, btnPick);

        gameScreen.updateChoicePanel();
    }

    private void handleStepOnIt() {
        Mob sudokuKing = new Mob("###FINAL_BOSS### Sudoku King Edi", 275, 45, 75);

        gameScreen.displayMessage("The book transforms into " + sudokuKing.getName().replace("###FINAL_BOSS### ", "") + "! Prepare for combat!");
        gameScreen.updateStatsDisplay();


        new CombatEvent(sudokuKing).trigger(currentPlayer, gameScreen);
    }

    private void handlePickUp() {
        currentPlayer.heal(20);
        gameScreen.displayMessage("You received 20 HP! Returning to main menu.");
        gameScreen.updateStatsDisplay();

        gameScreen.setupMainMenuButtons();
    }

}