import javax.swing.*;


public class OwlEvent extends Event {
    private GameScreenForm gameScreen;
    private Player currentPlayer;

    public OwlEvent() {
        super("A wise Owl gives you mystical advice");
    }

    @Override
    public void trigger(Player p, GameScreenForm screen) {
        this.currentPlayer = p;
        this.gameScreen = screen;

        gameScreen.setNewLog(description);
        gameScreen.displayMessage("Do you listen to her advice?");
        gameScreen.updateStatsDisplay();

        setupOwlButtons();
    }

    private void setupOwlButtons() {
        gameScreen.setChoiceButtons(false);

        JButton btnListen = new JButton("1. Listen");
        btnListen.addActionListener(e -> handleListen());
        gameScreen.setChoiceButton(1, btnListen);

        JButton btnIgnore = new JButton("2. Ignore");
        btnIgnore.addActionListener(e -> handleIgnore());
        gameScreen.setChoiceButton(2, btnIgnore);

        gameScreen.updateChoicePanel();
    }

    private void handleListen() {
        currentPlayer.heal(20);
        gameScreen.displayMessage("Congratulations! You received 20 HP");
        gameScreen.updateStatsDisplay();

        gameScreen.setupMainMenuButtons();
    }

    private void handleIgnore() {
        gameScreen.displayMessage("You ignored the Owl. She flies away.");
        gameScreen.setupMainMenuButtons();
    }

}