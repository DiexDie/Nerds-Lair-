import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.sound.sampled.Clip;

public class GameScreenForm {
    private GameBackgroundPanel mainGamePanel;
    private JTextArea mainTextArea;
    private JProgressBar hpBar;
    private JProgressBar manaBar;
    private JProgressBar coinsBar;
    private JLabel nameLabel;
    private JLabel hpTextLabel;
    private JLabel manaTextLabel;
    private JLabel coinsTextLabel;
    private JScrollPane mainTextScrollPane;
    private JButton choiceButton1;
    private JButton choiceButton2;
    private JButton choiceButton3;
    private JButton choiceButton4;


    private JLabel coinIconLabel;
    private JLabel hpIconLabel;
    private JLabel manaIconLabel;

    private final JButton[] actionButtons = new JButton[4];
    private final Player currentPlayer;
    private final Random rand = new Random();
    private final JFrame menuFrame;

    private Clip gameBackgroundClip;

    public void setGameBackgroundClip(Clip clip) {
        this.gameBackgroundClip = clip;
    }

    public Clip getGameBackgroundClip() {
        return this.gameBackgroundClip;
    }


    public GameScreenForm(JFrame menuFrame, Player player) {
        this.menuFrame = menuFrame;
        this.currentPlayer = player;


        applyActiveCheats(player);

        gameBackgroundClip = SoundManager.playMusic("Sounds/Song1.wav");


        Font statFont = new Font("ThaleahFat", Font.PLAIN, 20);
        Font textFont = new Font("ThaleahFat", Font.PLAIN, 20);
        Font buttonFont = new Font("ThaleahFat", Font.PLAIN, 20);

        actionButtons[0] = choiceButton1;
        actionButtons[1] = choiceButton2;
        actionButtons[2] = choiceButton3;
        actionButtons[3] = choiceButton4;

        mainTextArea.setFont(textFont);
        mainTextArea.setForeground(Color.WHITE);
        mainTextArea.setOpaque(false);
        mainTextArea.setEditable(false);
        mainTextArea.setLineWrap(true);
        mainTextArea.setWrapStyleWord(true);

        mainTextScrollPane.setOpaque(false);
        mainTextScrollPane.getViewport().setOpaque(false);

        nameLabel.setFont(statFont);
        nameLabel.setForeground(Color.WHITE);
        hpTextLabel.setFont(statFont); hpTextLabel.setForeground(Color.RED);
        manaTextLabel.setFont(statFont); manaTextLabel.setForeground(Color.BLUE);
        coinsTextLabel.setFont(statFont); coinsTextLabel.setForeground(Color.YELLOW);

        initializeStatsBars();
        updateStatsDisplay();

        setupMainMenuButtons();

        setNewLog("Welcome to the dungeon, " + currentPlayer.getName() + "! Choose your first action.");
    }

    private void setupChoiceButton(JButton button, Font font) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setFont(font);
    }

    public void setNewLog(String message) {
        mainTextArea.setText(message + "\n");
        mainTextArea.setCaretPosition(0);
    }

    public void displayMessage(String message) {
        mainTextArea.append("\n============================================\n");
        mainTextArea.append(message + "\n");
        mainTextArea.setCaretPosition(mainTextArea.getDocument().getLength());
    }


    private void setupTransparentProgressBar(JProgressBar bar, Color color) {
        bar.setMaximum(100);
        bar.setForeground(color);
        bar.setOpaque(false);
        bar.setBorderPainted(false);
    }

    private void initializeStatsBars() {

    }

    public void updateStatsDisplay() {

        hpBar.setValue(currentPlayer.getHp());
        manaBar.setValue(currentPlayer.getMana());
        coinsBar.setValue(Math.min(currentPlayer.getCoins(), 100));

        nameLabel.setText(currentPlayer.getName());
        hpTextLabel.setText(currentPlayer.getHp() + " / 100");
        manaTextLabel.setText(currentPlayer.getMana() + " / 100");
        coinsTextLabel.setText(" Coins: " + currentPlayer.getCoins());
    }


    private void applyActiveCheats(Player p) {
        String code = CheatManager.getActiveCheatCode();
        if (code == null) return;

        String result = "Cheat applied: ";

        switch (code) {
            case "DEBUG" -> {
                p.addcoins(999 - p.getCoins());
                p.setHp(100);
                p.setMana(100);
                p.addStrength(999);
                p.addDexterity(999);
                result += "DEBUG (999 Stats) activated.";
            }
            case "GOD" -> {
                p.setHp(100);
                p.setMana(100);
                result += "GOD (Full Restore) activated.";
            }
            case "LOSE" -> {
                p.setHp(1);
                result += "LOSE (HP=1) activated.";
            }
            case "EDI" -> {

                result += "EDI (Sudoku King Encounter) is active.";
            }
            default -> {
                return;
            }
        }


        String welcomeMessage = "Welcome to the dungeon, " + currentPlayer.getName() + "! Choose your first action.";
        setNewLog(result + "\n\n" + welcomeMessage);

        CheatManager.setActiveCheatCode(null);
    }


    private void handleExplore() {
        Event event;
        int chance = rand.nextInt(100);


        if (CheatManager.isForceNextSudokuKing()) {
            event = new SudokuBookEvent();
            CheatManager.setForceNextSudokuKing(false);
            event.trigger(currentPlayer, this);
            return;
        }




        if (chance < 45) { // 0 - 44 (45%)
            CombatEvent combatEvent = new CombatEvent();
            combatEvent.setBackgroundClip(getGameBackgroundClip());
            event = combatEvent;

        } else if (chance < 70) { // 45 - 69 (25%)
            ChestEvent chestEvent = new ChestEvent();
            chestEvent.setBackgroundClip(getGameBackgroundClip());
            event = chestEvent;

        } else if (chance < 75) { // 70 - 74 (5%)
            event = new TraderEvent();

        } else if (chance < 80) { // 75 - 79 (5%)
            event = new GandalfEvent();

        } else if (chance < 83) { // 80 - 82 (3%)
            event = new GhostEvent();

        } else if (chance < 88) { // 83 - 87 (5%)
            event = new OwlEvent();

        } else { // 88 - 99 (12%)
            event = new SudokuBookEvent();
        }

        event.trigger(currentPlayer, this);
    }


    public void showCredits() {

        JFrame gameFrame = (JFrame) SwingUtilities.getWindowAncestor(mainGamePanel);

        if (gameFrame == null) {
            System.err.println("Cannot find game frame for credits transition.");
            return;
        }


        if (gameBackgroundClip != null) {
            SoundManager.stopMusic(gameBackgroundClip);
        }


        CreditsForm creditsScreen = new CreditsForm(gameFrame);


        gameFrame.setContentPane(creditsScreen.getCreditsPanel());
        gameFrame.revalidate();
        gameFrame.repaint();
    }



    public void setupMainMenuButtons() {
        Font buttonFont = new Font("ThaleahFat", Font.PLAIN, 20);
        String[] finalOptions = {"Explore", "Inventory", "Save", "Exit"};

        for (int i = 0; i < finalOptions.length; i++) {
            if (actionButtons[i] == null) continue;

            setupChoiceButton(actionButtons[i], buttonFont);
            actionButtons[i].setText(finalOptions[i]);

            for (ActionListener al : actionButtons[i].getActionListeners()) {
                actionButtons[i].removeActionListener(al);
            }
            final String action = finalOptions[i];
            actionButtons[i].addActionListener(e -> handleMainMenuChoice(action));
            actionButtons[i].setVisible(true);
            actionButtons[i].setEnabled(true);
        }
    }


    public void handleMainMenuChoice(String choice) {
        switch (choice) {
            case "Explore" -> {
                setNewLog("Action: Explore");
                SoundManager.stopMusic(gameBackgroundClip);
                handleExplore();
            }
            case "Inventory" -> {
                currentPlayer.showInventory();
                displayMessage("INVENTORY:\nItems: " + currentPlayer.getInventory().size() + "\n" + currentPlayer.toString());
            }
            case "Save" -> {
                GameSaveManager.saveGame(currentPlayer);
                displayMessage("Game Saved Successfully!");
            }
            case "Exit" -> {
                int confirm = JOptionPane.showConfirmDialog(mainGamePanel, "Are you sure you want to exit?", "Exit Game", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
                displayMessage("You chose not to exit. Continuing...");
            }
            default -> setNewLog("Invalid action. Please choose one of the options.");
        }
        updateStatsDisplay();
    }


    public void setupCombatItemMenu(ActionListener backToCombatListener) {
        Font buttonFont = new Font("ThaleahFat", Font.PLAIN, 20);
        setChoiceButtons(false);

        JButton btnPotion = new JButton("Use Health Potion");
        setupChoiceButton(btnPotion, buttonFont);
        btnPotion.addActionListener(e -> {
            displayMessage("Using Health Potion.");
        });
        setChoiceButton(1, btnPotion);

        JButton btnSpell = new JButton("Use Spell");
        setupChoiceButton(btnSpell, buttonFont);
        btnSpell.addActionListener(e -> {
            displayMessage("Using Spell.");
        });
        setChoiceButton(2, btnSpell);

        JButton btnBack = new JButton("Back");
        setupChoiceButton(btnBack, buttonFont);

        btnBack.addActionListener(backToCombatListener);
        setChoiceButton(4, btnBack);

        choiceButton1.setVisible(true);
        choiceButton2.setVisible(true);
        choiceButton4.setVisible(true);
        choiceButton3.setVisible(false);
        updateChoicePanel();
    }



    public void setChoiceButton(int index, JButton newButton) {
        if (index > 0 && index <= actionButtons.length) {
            JButton oldButton = actionButtons[index - 1];

            setupChoiceButton(oldButton, newButton.getFont() != null ? newButton.getFont() : new Font("ThaleahFat", Font.PLAIN, 16));

            oldButton.setText(newButton.getText());

            for (ActionListener al : oldButton.getActionListeners()) {
                oldButton.removeActionListener(al);
            }
            for (ActionListener al : newButton.getActionListeners()) {
                oldButton.addActionListener(al);
            }
            oldButton.setVisible(true);
            oldButton.setEnabled(true);
        }
    }


    public JButton getChoiceButton(int index) {
        if (index > 0 && index <= actionButtons.length) {
            return actionButtons[index - 1];
        }
        return null;
    }

    public void setChoiceButtons(boolean active) {
        for (JButton button : actionButtons) {
            if (button != null) {
                button.setVisible(active);
                button.setEnabled(active);
            }
        }
    }

    public void updateChoicePanel() {
        mainGamePanel.revalidate();
        mainGamePanel.repaint();
    }

    public JPanel getMainGamePanel() {
        return mainGamePanel;
    }

    private void createUIComponents() {
        mainGamePanel = new GameBackgroundPanel();
        mainGamePanel.setLayout(null);

        final int barIconWidth = 200;
        final int barIconHeight = 200;
        final int iconSize = 60;

        nameLabel = new JLabel();
        nameLabel.setVisible(false);
        nameLabel.setBounds(60, 19, 200, 20);
        mainGamePanel.add(nameLabel);


        hpBar = new JProgressBar();
        setupTransparentProgressBar(hpBar, Color.RED);
        hpBar.setBounds(100, 40, 130, 20);
        mainGamePanel.add(hpBar);


        hpIconLabel = new JLabel();
        try {
            ImageIcon hpIcon = new ImageIcon(getClass().getResource("/Images/hb.png"));
            if (hpIcon.getImage() != null) {
                Image img = hpIcon.getImage();
                Image scaledImg = img.getScaledInstance(barIconWidth, barIconHeight, Image.SCALE_SMOOTH);
                hpIconLabel.setIcon(new ImageIcon(scaledImg));
            }
        } catch (Exception e) {
            System.err.println("Error loading HP frame: " + e.getMessage());
        }
        hpIconLabel.setBounds(50, -55, barIconWidth, barIconHeight);
        mainGamePanel.add(hpIconLabel);

        hpTextLabel = new JLabel();
        hpTextLabel.setBounds(130, 70, 70, 15);
        mainGamePanel.add(hpTextLabel);



        manaBar = new JProgressBar();
        setupTransparentProgressBar(manaBar, Color.BLUE);
        manaBar.setBounds(350, 40, 135, 20);
        mainGamePanel.add(manaBar);


        manaIconLabel = new JLabel();
        try {
            ImageIcon manaIcon = new ImageIcon(getClass().getResource("/Images/mb.png"));
            if (manaIcon.getImage() != null) {
                Image img = manaIcon.getImage();
                Image scaledImg = img.getScaledInstance(barIconWidth, barIconHeight, Image.SCALE_SMOOTH);
                manaIconLabel.setIcon(new ImageIcon(scaledImg));
            }
        } catch (Exception e) {
            System.err.println("Error loading Mana frame: " + e.getMessage());
        }
        manaIconLabel.setBounds( 300, -55, barIconWidth, barIconHeight);
        mainGamePanel.add(manaIconLabel);

        manaTextLabel = new JLabel();
        manaTextLabel.setBounds(380, 70, 70, 15);
        mainGamePanel.add(manaTextLabel);



        coinIconLabel = new JLabel();
        try {
            ImageIcon coinIcon = new ImageIcon(getClass().getResource("/Images/GOLD.png"));
            if (coinIcon.getImage() != null) {
                Image img = coinIcon.getImage();
                Image scaledImg = img.getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
                coinIconLabel.setIcon(new ImageIcon(scaledImg));
            }
        } catch (Exception e) {
            System.err.println("Error! " + e.getMessage());
            coinIconLabel.setText("C");
            coinIconLabel.setForeground(Color.YELLOW);
        }

        coinIconLabel.setBounds(670, 25, iconSize, iconSize);
        mainGamePanel.add(coinIconLabel);

        coinsBar = new JProgressBar();
        coinsBar.setBounds(565, 45, 140, 15);
        coinsBar.setVisible(false);
        mainGamePanel.add(coinsBar);
        coinsTextLabel = new JLabel();
        coinsTextLabel.setBounds(575, 37, 200, 35);
        mainGamePanel.add(coinsTextLabel);

        mainTextArea = new JTextArea();
        mainTextScrollPane = new JScrollPane(mainTextArea);
        mainTextScrollPane.setBounds(107, 125, 580, 165);
        mainGamePanel.add(mainTextScrollPane);

        choiceButton1 = new JButton();
        choiceButton1.setBounds(80, 320, 170, 110);
        mainGamePanel.add(choiceButton1);

        choiceButton2 = new JButton();
        choiceButton2.setBounds(230, 320, 170, 110);
        mainGamePanel.add(choiceButton2);

        choiceButton3 = new JButton();
        choiceButton3.setBounds(385, 320, 170, 110);
        mainGamePanel.add(choiceButton3);

        choiceButton4 = new JButton();
        choiceButton4.setBounds(535, 320, 170, 110);
        mainGamePanel.add(choiceButton4);
    }
}