import javax.sound.sampled.Clip;
import javax.swing.*;
import java.awt.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;




public class CharacterSelection {
    private CharacterBackgroundPanel characterscreen;
    private JButton archerSelectButton;
    private JButton warriorSelectButton;
    private JTextField nameField;
    private JButton nextButton;
    private JButton backButton;
    private JLabel nameLabel;

    private String selectedClass = null;
    private final JFrame parentFrame;
    private final Clip backgroundMusic;

    public CharacterSelection(JFrame menuFrame) {
        this.parentFrame = menuFrame;

        Font buttonFont = new Font("Gotfridus", Font.BOLD, 20);
        Color lightColor = Color.WHITE;
        Color redColor = Color.RED;
        Color greenColor = Color.GREEN;

        nameField.setOpaque(false);
        nameField.setForeground(lightColor);
        nameField.setFont(new Font("Gotfridus", Font.PLAIN, 24));
        nameField.setHorizontalAlignment(JTextField.CENTER);

        nameLabel.setForeground(Color.WHITE);
        nameLabel.setFont(new Font("Gotfridus", Font.BOLD, 20));
        nameLabel.setHorizontalAlignment(JLabel.CENTER);

        setupClassButton(archerSelectButton, "ARCHER", buttonFont, lightColor, greenColor);
        setupClassButton(warriorSelectButton, "WARRIOR", buttonFont, lightColor, redColor);

        setupNavigationButton(nextButton, "NEXT", buttonFont, lightColor);
        setupNavigationButton(backButton, "BACK", buttonFont, lightColor);
        nextButton.setEnabled(false);

        archerSelectButton.addActionListener(e -> {
            selectedClass = "Archer";
            updateClassSelectionUI(archerSelectButton, warriorSelectButton, greenColor);
            checkFormValidity();
        });

        backgroundMusic = SoundManager.playMusic("Song1.wav");

        warriorSelectButton.addActionListener(e -> {
            selectedClass = "Warrior";
            updateClassSelectionUI(warriorSelectButton, archerSelectButton, redColor);
            checkFormValidity();
        });

        nameField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) { checkFormValidity(); }
            public void removeUpdate(DocumentEvent e) { checkFormValidity(); }
            public void insertUpdate(DocumentEvent e) { checkFormValidity(); }
        });

        nextButton.addActionListener(e -> {
            startTheGame();
        });

        backButton.addActionListener(e -> {
            parentFrame.setVisible(true);
            SwingUtilities.getWindowAncestor(characterscreen).dispose();
        });
    }

    private void setupClassButton(JButton button, String text, Font font, Color defaultColor, Color selectedColor) {
        button.setText(text);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setForeground(defaultColor);
        button.setFont(font);
    }

    private void setupNavigationButton(JButton button, String text, Font font, Color textColor) {
        button.setText(text);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setForeground(textColor);
        button.setFont(font);
    }

    private void updateClassSelectionUI(JButton selectedBtn, JButton unselectedBtn, Color selectedColor) {
        selectedBtn.setForeground(selectedColor);
        unselectedBtn.setForeground(Color.WHITE);
    }

    private void checkFormValidity() {
        boolean nameEntered = !nameField.getText().trim().isEmpty();
        boolean classSelected = (selectedClass != null);
        nextButton.setEnabled(nameEntered && classSelected);
    }

    private void startTheGame() {
        String playerName = nameField.getText().trim();
        Player newPlayer = null;

        if ("Warrior".equals(selectedClass)) {
            newPlayer = new Warrior(playerName);
        } else if ("Archer".equals(selectedClass)) {
            newPlayer = new Archer(playerName);
        }

        if (newPlayer != null) {
            JFrame selectionFrame = (JFrame) SwingUtilities.getWindowAncestor(characterscreen);
            JFrame gameFrame = new JFrame("Nerds Lair - The Dungeon");

            SoundManager.stopMusic(backgroundMusic);

            GameScreenForm gameScreen = new GameScreenForm(selectionFrame, newPlayer);

            gameFrame.setContentPane(gameScreen.getMainGamePanel());
            gameFrame.setSize(800, 600);
            gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            gameFrame.setLocationRelativeTo(null);

            selectionFrame.dispose();
            gameFrame.setVisible(true);
        }
    }

    public JPanel getCharacterscreen() {
        return characterscreen;
    }

    private void createUIComponents() {
        characterscreen = new CharacterBackgroundPanel();
        characterscreen.setLayout(null);

        nameLabel = new JLabel("CHOOSE YOUR NAME");
        nameLabel.setBounds(250, 360, 300, 40);
        characterscreen.add(nameLabel);

        nameField = new JTextField(20);
        nameField.setBounds(250, 400, 300, 40);
        characterscreen.add(nameField);

        archerSelectButton = new JButton();
        archerSelectButton.setBounds(150, 460, 150, 30);
        characterscreen.add(archerSelectButton);

        warriorSelectButton = new JButton();
        warriorSelectButton.setBounds(500, 460, 150, 30);
        characterscreen.add(warriorSelectButton);

        backButton = new JButton();
        backButton.setBounds(290, 520, 100, 30);
        characterscreen.add(backButton);

        nextButton = new JButton();
        nextButton.setBounds(410, 520, 100, 30);
        characterscreen.add(nextButton);
    }
}