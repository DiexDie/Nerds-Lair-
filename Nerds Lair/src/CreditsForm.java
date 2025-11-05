import javax.swing.*;
import java.awt.*;
import javax.sound.sampled.Clip;
import java.net.URL;

public class CreditsForm {
    private final JPanel creditsPanel;
    private final JFrame gameFrameToDispose;
    private final Clip finalMusicClip;

    private static final String ICON_PATH = "/edire.png";
    private static final int ICON_SIZE = 250;

    public CreditsForm(JFrame gameFrameToDispose) {
        this.gameFrameToDispose = gameFrameToDispose;

        finalMusicClip = SoundManager.playMusic("credits.wav");

        creditsPanel = new JPanel();
        creditsPanel.setLayout(new BorderLayout());
        creditsPanel.setBackground(Color.BLACK);



        JLabel iconLabel = new JLabel();

        try {
            URL iconURL = getClass().getResource(ICON_PATH);
            if (iconURL != null) {
                ImageIcon originalIcon = new ImageIcon(iconURL);
                Image originalImage = originalIcon.getImage();

                Image scaledImage = originalImage.getScaledInstance(ICON_SIZE, ICON_SIZE, Image.SCALE_SMOOTH);
                iconLabel.setIcon(new ImageIcon(scaledImage));

            } else {
                iconLabel.setText("ICON MISSING");
                iconLabel.setForeground(Color.RED);
                iconLabel.setFont(new Font("ThaleahFat", Font.BOLD, 24));
                System.err.println("Icon resource not found: " + ICON_PATH);
            }
        } catch (Exception e) {
            iconLabel.setText("ICON ERROR");
            iconLabel.setForeground(Color.RED);
            iconLabel.setFont(new Font("ThaleahFat", Font.BOLD, 24));
            System.err.println("Error loading icon: " + e.getMessage());
        }

        JTextArea creditsText = new JTextArea();
        creditsText.setText(
                "=========================================\n" +
                        "            NERDS LAIR - THE DUNGEON\n" +
                        "=========================================\n\n" +
                        "CONGRATULATIONS! YOU HAVE DEFEATED THE\n" +
                        "SUDOKU KING AND RESTORED THE ORDER AMONG THE NERDS!\n" +
                        "GAME DESIGN & CODE: Denis Sara\n" +
                        "SPECIAL THANKS TO SUPREME NERD EDI AND MY COFFEE MACHINE\n"+
                        "SOUNDS:YouTube\n"+
                        "THANK YOU FOR PLAYING!\n"+
                        "UNTIL YOU BUY THE GAME, I WILL KEEP THIS NERD CAPTIVE IN MY CELLAR!\n\n" +
                        "=========================================="

        );

        creditsText.setFont(new Font("ThaleahFat", Font.PLAIN, 20));
        creditsText.setForeground(Color.YELLOW);
        creditsText.setBackground(Color.BLACK);
        creditsText.setEditable(false);
        creditsText.setBorder(BorderFactory.createEmptyBorder(10, 50, 50, 50));


        JButton exitButton = new JButton("RETURN TO MAIN MENU");
        exitButton.setFont(new Font("ThaleahFat", Font.BOLD, 28));
        exitButton.setForeground(Color.GREEN);
        exitButton.setBackground(Color.DARK_GRAY);
        exitButton.setFocusPainted(false);
        exitButton.addActionListener(e -> returnToMainMenu());


        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.BLACK);


        JPanel buttonWrapperPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonWrapperPanel.setBackground(Color.BLACK);
        buttonWrapperPanel.add(exitButton);
        bottomPanel.add(buttonWrapperPanel, BorderLayout.CENTER);


        JPanel iconWrapperPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        iconWrapperPanel.setBackground(Color.BLACK);


        iconLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 50));
        iconWrapperPanel.add(iconLabel);


        bottomPanel.add(iconWrapperPanel, BorderLayout.EAST);


        creditsPanel.add(creditsText, BorderLayout.CENTER);
        creditsPanel.add(bottomPanel, BorderLayout.SOUTH);
    }

    private void returnToMainMenu() {
        if (finalMusicClip != null) {
            SoundManager.stopMusic(finalMusicClip);
        }

        if (gameFrameToDispose != null) {
            gameFrameToDispose.dispose();
            SwingUtilities.invokeLater(MainMenu::new);
        }
    }

    public JPanel getCreditsPanel() {
        return creditsPanel;
    }
}