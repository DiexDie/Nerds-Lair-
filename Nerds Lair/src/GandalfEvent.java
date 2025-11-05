import javax.swing.*;


public class GandalfEvent extends Event {
    private GameScreenForm gameScreen;
    private Player currentPlayer;


    private static final int HP_POTION_COST = 10;
    private static final int GTR_HP_POTION_COST = 40;
    private static final int SML_MANA_POTION_COST = 15;
    private static final int GTR_MANA_POTION_COST = 30;


    public GandalfEvent() {
        super("You encountered a wise Wizard, Gandalf!");
    }

    @Override
    public void trigger(Player p, GameScreenForm screen) {
        this.currentPlayer = p;
        this.gameScreen = screen;
        gameScreen.setNewLog(description);
        gameScreen.displayMessage("A wizard should know best! I have useful potions for your journey.");
        setupGandalfMenu();
    }

    private void setupGandalfMenu() {
        gameScreen.setChoiceButtons(false);
        gameScreen.displayMessage("Your Coins: " + currentPlayer.getCoins() + ". What do you need?");


        JButton btnBuyHP = new JButton("HP Potion (+20 HP) - " + HP_POTION_COST + " C");
        btnBuyHP.addActionListener(e -> handlePotionPurchase(" Health Potion ", 20, 0, Rarity.COMMON, HP_POTION_COST));
        btnBuyHP.setEnabled(currentPlayer.getCoins() >= HP_POTION_COST);
        gameScreen.setChoiceButton(1, btnBuyHP);


        JButton btnBuySMLMana = new JButton("Small Mana Potion (+15 Mana) - " + SML_MANA_POTION_COST + " C");
        btnBuySMLMana.addActionListener(e -> handlePotionPurchase(" Mana Potion (Small) ", 0, 15, Rarity.UNCOMMON, SML_MANA_POTION_COST));
        btnBuySMLMana.setEnabled(currentPlayer.getCoins() >= SML_MANA_POTION_COST);
        gameScreen.setChoiceButton(2, btnBuySMLMana);


        JButton btnBuyGTRHP = new JButton("Greater HP Potion (+50 HP) - " + GTR_HP_POTION_COST + " C");
        btnBuyGTRHP.addActionListener(e -> handlePotionPurchase(" Greater Healing Potion ", 50, 0, Rarity.RARE, GTR_HP_POTION_COST));
        btnBuyGTRHP.setEnabled(currentPlayer.getCoins() >= GTR_HP_POTION_COST);
        gameScreen.setChoiceButton(3, btnBuyGTRHP);


        JButton btnExit = new JButton("Goodbye");
        btnExit.addActionListener(e -> gameScreen.setupMainMenuButtons());
        gameScreen.setChoiceButton(4, btnExit);

        gameScreen.setChoiceButtons(true);
        gameScreen.updateChoicePanel();
    }

    private void handlePotionPurchase(String name, int heal, int mana, Rarity rarity, int cost) {
        if (currentPlayer.getCoins() >= cost) {


            String formattedName = name.trim() + " (" + rarity.getDisplayName() + ")";


            Potion potion = new Potion(formattedName, heal, mana);

            currentPlayer.addcoins(-cost);
            currentPlayer.addItem(potion);

            gameScreen.displayMessage("Purchased " + formattedName + " for " + cost + " Coins.");
            gameScreen.updateStatsDisplay();

            setupGandalfMenu();
        } else {
            gameScreen.displayMessage("You do not have enough coins for that potion.");
            setupGandalfMenu();
        }
    }
}