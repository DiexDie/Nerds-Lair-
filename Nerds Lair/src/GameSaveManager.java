import java.io.*;

public class GameSaveManager {
    private static final String SAVE_FILE = "savegame.dat";

    public static void saveGame(Player p) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(p);
            System.out.println("Game successfully saved to: " + SAVE_FILE);
        } catch (IOException e) {
            System.err.println("Error saving game: " + e.getMessage());
        }
    }

    public static Player loadGame() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SAVE_FILE))) {
            Player p = (Player) ois.readObject();
            System.out.println("Game successfully loaded from: " + SAVE_FILE);
            return p;
        } catch (FileNotFoundException e) {
            System.out.println("No save file found (" + SAVE_FILE + ").");
            return null;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading game. Corrupt save file or missing class: " + e.getMessage());
            return null;
        }
    }
}