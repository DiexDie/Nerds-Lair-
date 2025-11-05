import javax.swing.*;


public class GhostEvent extends Event {
    private GameScreenForm gameScreen;
    private Player currentPlayer;

    public GhostEvent() {
        super("A friendly ghost wants to give you energy!");
    }

    @Override
    public void trigger(Player p, GameScreenForm screen) {
        this.currentPlayer = p;
        this.gameScreen = screen;

        gameScreen.setNewLog(description);
        gameScreen.displayMessage("Do you accept the gift?");
        gameScreen.updateStatsDisplay();

        setupGhostButtons();
    }

    private void setupGhostButtons() {
        gameScreen.setChoiceButtons(false);

        JButton btnAccept = new JButton("1. Accept");
        btnAccept.addActionListener(e -> handleAccept());
        gameScreen.setChoiceButton(1, btnAccept);

        JButton btnRefuse = new JButton("2. Refuse");
        btnRefuse.addActionListener(e -> handleRefuse());
        gameScreen.setChoiceButton(2, btnRefuse);

        gameScreen.updateChoicePanel();
    }

    private void handleAccept() {
        currentPlayer.heal(20);
        gameScreen.displayMessage("The ghost gave you +20 HP! Your HP is now " + currentPlayer.getHp() + ".");
        gameScreen.updateStatsDisplay();

        gameScreen.setupMainMenuButtons();
    }

    private void handleRefuse() {
        gameScreen.displayMessage("You refused the ghost's help. It fades away.");
        gameScreen.setupMainMenuButtons();
    }
}