import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

public class SoundManager {


    private static URL getSoundResource(String resourcePath) {

        if (!resourcePath.startsWith("/")) {
            resourcePath = "/" + resourcePath;
        }

        return SoundManager.class.getResource(resourcePath);
    }

    public static void playSound(String resourcePath) {
        try {
            URL audioUrl = getSoundResource(resourcePath);
            if (audioUrl == null) {
                System.err.println("Error: Sound file was not found at: " + resourcePath);
                return;
            }

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(audioUrl);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();

            clip.addLineListener(event -> {
                if (event.getType() == LineEvent.Type.STOP) {
                    clip.close();
                }
            });

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error when playing sound: " + e.getMessage());
        }
    }

    public static Clip playMusic(String resourcePath) {
        try {
            URL audioUrl = getSoundResource(resourcePath);

            if (audioUrl == null) {
                System.err.println("Error: Sound file was not found at: " + resourcePath);
                return null;
            }

            AudioInputStream audioIn = AudioSystem.getAudioInputStream(audioUrl);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
            clip.start();
            return clip;
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.err.println("Error when playing sound: " + e.getMessage());
            return null;
        }
    }

    public static void stopMusic(Clip clip) {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.close();
        }
    }
}