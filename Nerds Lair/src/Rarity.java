import java.io.Serializable;

public enum Rarity implements Serializable {
    COMMON("Common", "#FFFFFF"),
    UNCOMMON("Uncommon", "#00FF00"),
    RARE("Rare", "#0000FF"),
    EPIC("Epic", "#FF00FF");


    private final String displayName;
    private final String hexColor;

    Rarity(String displayName, String hexColor) {
        this.displayName = displayName;
        this.hexColor = hexColor;
    }

    public String getDisplayName() { return displayName; }
    public String getHexColor() { return hexColor; }
}