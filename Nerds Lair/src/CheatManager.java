public class CheatManager {
    private static String activeCheatCode = null;
    private static boolean forceNextSudokuKing = false;
    private static boolean isGodModeActive = false;

    public static String getActiveCheatCode() {
        return activeCheatCode;
    }

    public static void setActiveCheatCode(String code) {
        activeCheatCode = code;
    }

    public static boolean isForceNextSudokuKing() {
        return forceNextSudokuKing;
    }

    public static void setForceNextSudokuKing(boolean force) {
        forceNextSudokuKing = force;
    }

    public static boolean isGodModeActive() { // NOU: Getter
        return isGodModeActive;
    }

    public static void setGodModeActive(boolean active) { // NOU: Setter
        isGodModeActive = active;
    }
}