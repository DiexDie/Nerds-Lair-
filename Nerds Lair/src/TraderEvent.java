import javax.swing.*;
import java.util.Random;
import java.util.List;


public class TraderEvent extends Event {
    private GameScreenForm gameScreen;
    private Player currentPlayer;
    private final Random rand = new Random();
    private TradeOffer[] currentOffers;

    public TraderEvent() {
        super("You encountered a mysterious Trader!");
    }


    private static class TradeOffer {
        Item item;
        int price;

        public TradeOffer(Item item, int price) {
            this.item = item;
            this.price = price;
        }
    }


    private int generateItemPrice(Rarity rarity) {
        int min;
        int max;

        switch (rarity) {
            case COMMON:
                min = 0;
                max = 50;
                break;
            case UNCOMMON:
                min = 50;
                max = 100;
                break;
            case RARE:
                min = 100;
                max = 150;
                break;
            case EPIC:
                min = 150;
                max = 200;
                break;
            default:
                min = 5;
                max = 50;
        }
        return rand.nextInt(max - min + 1) + min;
    }


    private Item generateRandomEquipment(PlayerClass playerClass) {

        int itemType = rand.nextInt(10);
        List<? extends Item> sourceList;

        if (itemType < 4) {
            sourceList = ChestEvent.availableWeapons;
        } else if (itemType < 7) {
            sourceList = ChestEvent.availableArmor;
        } else {
            sourceList = ChestEvent.availableSpells;
        }

        Item randomItem = ChestEvent.getRandomCompatibleItem(sourceList, playerClass);

        if (randomItem == null) {
            return ChestEvent.availablePotions.get(0);
        }
        return randomItem;
    }


    @Override
    public void trigger(Player p, GameScreenForm screen) {
        this.currentPlayer = p;
        this.gameScreen = screen;
        gameScreen.setNewLog(description);
        gameScreen.displayMessage("Welcome, traveler! I have equipment for you.");
        setupTradeMenu();
    }

    private void setupTradeMenu() {
        gameScreen.setChoiceButtons(false);
        gameScreen.displayMessage("You have " + currentPlayer.getCoins() + " Coins. What would you like to buy?");


        currentOffers = new TradeOffer[3];

        for (int i = 0; i < 3; i++) {
            Item offeredItem = generateRandomEquipment(currentPlayer.getPlayerClass());


            Rarity rarity;
            if (offeredItem instanceof Weapon w) rarity = w.getRarity();
            else if (offeredItem instanceof Armor a) rarity = a.getRarity();
            else if (offeredItem instanceof Spell s) rarity = s.getRarity();
            else rarity = Rarity.COMMON;

            int price = generateItemPrice(rarity);
            currentOffers[i] = new TradeOffer(offeredItem, price);
        }


        for (int i = 0; i < 3; i++) {
            TradeOffer offer = currentOffers[i];




            JButton btnBuy = new JButton( offer.item.getName() +"\n"+ offer.price + " C");

            if (currentPlayer.getCoins() < offer.price) {
                btnBuy.setEnabled(false);
            }

            final int offerIndex = i;
            btnBuy.addActionListener(e -> handlePurchase(offerIndex));
            gameScreen.setChoiceButton(i + 1, btnBuy);
        }


        JButton btnExit = new JButton("Exit");
        btnExit.addActionListener(e -> gameScreen.setupMainMenuButtons());
        gameScreen.setChoiceButton(4, btnExit);

        gameScreen.setChoiceButtons(true);
        gameScreen.updateChoicePanel();
    }

    private void handlePurchase(int offerIndex) {
        TradeOffer offer = currentOffers[offerIndex];


        if (currentPlayer.getCoins() >= offer.price) {
            currentPlayer.addcoins(-offer.price);


            currentPlayer.addItem(offer.item);

            gameScreen.displayMessage("Purchase successful! Bought " + offer.item.getName() + " for " + offer.price + " Coins.");
            gameScreen.updateStatsDisplay();


            setupTradeMenu();

        } else {
            gameScreen.displayMessage("You don't have enough coins for " + offer.item.getName() + ".");
            setupTradeMenu();
        }
    }

    private void handleRefuse() {
        gameScreen.displayMessage("You refused the merchant. He disappears.");
        gameScreen.setupMainMenuButtons();
    }

}