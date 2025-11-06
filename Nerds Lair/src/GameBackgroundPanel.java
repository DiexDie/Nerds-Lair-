import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.net.URL;

public class GameBackgroundPanel extends JPanel {
    private Image backgroundImage;

    public GameBackgroundPanel() {
        try {

            URL imageUrl = getClass().getResource("/Images/GUI.png");
            if (imageUrl != null) {
                backgroundImage = ImageIO.read(imageUrl);
            } else {
                System.err.println("Error! Game scene image not found at: " + "/resources/GUI..png");
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
        } else {
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}