import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.net.URL;

public class CharacterBackgroundPanel extends JPanel {
    private Image backgroundImage;

    public CharacterBackgroundPanel() {
        try {
            URL imageUrl = getClass().getResource("/resources/CharacterSelection.png");
            if (imageUrl != null) {
                backgroundImage = ImageIO.read(imageUrl);
            } else {
                System.err.println("Error! Image not found!.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}