import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.sound.sampled.Clip;

public class MainMenu {
    private JButton EXITButton;
    private JButton PLAYButton;
    private JButton LOADButton;
    private JButton CHEATSButton; // NOU
    private BackgroundPanel mainmenu;
    private JLabel titlelabel;
    private final JFrame menuframe;

    private final Clip backgroundMusic;

    public MainMenu(){
        createUIComponents();


        if (EXITButton == null) EXITButton = new JButton("EXIT");
        if (PLAYButton == null) PLAYButton = new JButton("PLAY");
        if (LOADButton == null) LOADButton = new JButton("LOAD GAME");
        if (CHEATSButton == null) CHEATSButton = new JButton("CHEATS");
        if (titlelabel == null) titlelabel = new JLabel("NERDS LAIR");

        initializeButtonStyles();
        initializeActionListeners();

        this.titlelabel.setText("NERDS LAIR");
        this.titlelabel.setFont(new Font("Gotfridus", Font.BOLD, 48));
        this.titlelabel.setForeground(Color.RED);


        menuframe = new JFrame();
        menuframe.setContentPane(mainmenu);
        menuframe.setSize(800, 600);
        menuframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuframe.setLocationRelativeTo(null);
        menuframe.setVisible(true);


        backgroundMusic = SoundManager.playMusic("Sounds/Song1.wav");
    }


    private void initializeButtonStyles() {
        Font buttonFont = new Font("Gotfridus", Font.BOLD, 32);

        if (mainmenu.getLayout() != null && !(mainmenu.getLayout() instanceof BorderLayout)) {
            mainmenu.setLayout(null);
        }

        final int BTN_WIDTH = 250;
        final int BTN_HEIGHT = 70;
        final int START_Y = 200;
        final int SPACING = 25;
        final int CENTER_X = (800 - BTN_WIDTH) / 2;
        int currentY = START_Y;

        titlelabel.setBounds(230, 40, 400, 60);
        mainmenu.add(titlelabel);


        PLAYButton.setBounds(CENTER_X, currentY, BTN_WIDTH, BTN_HEIGHT);
        styleButton(PLAYButton, buttonFont, Color.GREEN);
        mainmenu.add(PLAYButton);
        currentY += BTN_HEIGHT + SPACING;



        LOADButton.setBounds(CENTER_X, currentY, BTN_WIDTH, BTN_HEIGHT);
        styleButton(LOADButton, buttonFont, Color.YELLOW);
        mainmenu.add(LOADButton);
        currentY += BTN_HEIGHT + SPACING;


        CHEATSButton.setBounds(CENTER_X, currentY, BTN_WIDTH, BTN_HEIGHT);
        styleButton(CHEATSButton, buttonFont, Color.CYAN);
        mainmenu.add(CHEATSButton);
        currentY += BTN_HEIGHT + SPACING;


        EXITButton.setBounds(CENTER_X, currentY, BTN_WIDTH, BTN_HEIGHT);
        styleButton(EXITButton, buttonFont, Color.RED);
        mainmenu.add(EXITButton);

        mainmenu.revalidate();
        mainmenu.repaint();
    }

    private void styleButton(JButton button, Font font, Color color) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setForeground(color);
        button.setFont(font);
    }

    private void initializeActionListeners() {
        EXITButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int optionR=JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "EXIT", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE);
                if(optionR==JOptionPane.YES_OPTION){
                    SoundManager.stopMusic(backgroundMusic);
                    menuframe.dispose();
                }
            }
        });

        PLAYButton.addActionListener(e -> {
            startNewGame();
        });

        LOADButton.addActionListener(e -> {
            Player loadedPlayer = GameSaveManager.loadGame();

            if (loadedPlayer != null) {
                JOptionPane.showMessageDialog(menuframe, "Game loaded successfully!", "LOAD SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                startGameWithLoadedPlayer(loadedPlayer);
            } else {
                JOptionPane.showMessageDialog(menuframe, "No save file found or file is corrupt.", "LOAD FAILED", JOptionPane.ERROR_MESSAGE);
            }
        });


        CHEATSButton.addActionListener(e -> {
            displayCheatDialog();
        });
    }


    private void displayCheatDialog() {
        JTextArea cheatInfo = new JTextArea(
                "Enter Cheat Code (Applied upon starting/loading game):\n" +
                        "• DEBUG: 999 Coins, Full HP/Mana, High Strength/Dex (Activates God Mode)\n" +
                        "• EDI: Next 'Explore' guarantees Sudoku King Edi encounter.\n" +
                        "• LOSE: Sets HP to 1 (Deactivates God Mode)."
        );
        cheatInfo.setEditable(false);
        cheatInfo.setBackground(UIManager.getColor("Panel.background"));

        JTextField inputField = new JTextField(20);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.add(cheatInfo);
        panel.add(Box.createVerticalStrut(10));
        panel.add(new JLabel("Code:"));
        panel.add(inputField);

        int result = JOptionPane.showConfirmDialog(
                menuframe,
                panel,
                "NERDS LAIR - CHEAT MENU",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            String code = inputField.getText().trim().toUpperCase();

            if (code.equals("DEBUG") || code.equals("EDI") || code.equals("GOD") || code.equals("LOSE")) {


                CheatManager.setActiveCheatCode(code);


                if (code.equals("DEBUG") || code.equals("GOD")) {
                    CheatManager.setGodModeActive(true);
                } else if (code.equals("LOSE")) {
                    CheatManager.setGodModeActive(false);
                }

                if (code.equals("EDI")) {
                    CheatManager.setForceNextSudokuKing(true);
                } else {
                    CheatManager.setForceNextSudokuKing(false);
                }

                JOptionPane.showMessageDialog(
                        menuframe,
                        "Code '" + code + "' accepted. Will be applied upon starting/loading a game.",
                        "CHEAT ACCEPTED",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                        menuframe,
                        "Invalid code: " + code + ".",
                        "CHEAT REJECTED",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        }
    }


    private void createUIComponents() {
        mainmenu = new BackgroundPanel();
    }

    private void startNewGame() {
        SoundManager.stopMusic(backgroundMusic);

        JFrame selectionFrame = new JFrame("Nerds Lair - Create Character");
        CharacterSelection selectionScreen = new CharacterSelection(menuframe);

        selectionFrame.setContentPane(selectionScreen.getCharacterscreen());
        selectionFrame.setSize(800, 600);
        selectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        selectionFrame.setLocationRelativeTo(null);

        menuframe.setVisible(false);
        selectionFrame.setVisible(true);
    }

    private void startGameWithLoadedPlayer(Player p) {
        SoundManager.stopMusic(backgroundMusic);

        JFrame gameFrame = new JFrame("Nerds Lair - Adventure");
        GameScreenForm gameScreen = new GameScreenForm(menuframe, p);

        gameFrame.setContentPane(gameScreen.getMainGamePanel());
        gameFrame.setSize(800, 600);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLocationRelativeTo(null);

        menuframe.dispose();
        gameFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenu::new);
    }
}