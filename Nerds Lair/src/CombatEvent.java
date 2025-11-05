import java.util.Random;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.util.List;
import javax.sound.sampled.Clip;
import java.awt.event.ActionListener;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;

public class CombatEvent extends Event {
    private final Mob enemy;
    private int escapeChance = 80;
    private final Random rand = new Random();

    private GameScreenForm gameScreen;
    private Player currentPlayer;

    private Clip combatMusic;
    private Clip previousBackgroundClip;


    private ActionListener backToCombatMenuListener;


    public void setBackgroundClip(Clip clip) {
        this.previousBackgroundClip = clip;
    }

    public CombatEvent() {
        super("An enemy appears!");
        Mob[] mobs = {
                new Mob(" Goblin ", 80, 10, 20),
                new Mob(" Dark Wizard ", 75, 15, 25),
                new Mob(" Skeleton ", 40, 5, 15),
                new Mob(" Dwarf ", 60, 10, 18),
                new Mob(" Skeleton General ", 100, 20, 30),
                new Mob(" Goblin Archer ", 50, 12, 8),
                new Mob(" Ice Elemental ", 95, 16, 22),
                new Mob(" Giant Spider ", 70, 14, 10),
                new Mob(" Armored Knight ", 130, 17, 35),
                new Mob(" Fire Imp ", 35, 10, 5),
                new Mob(" Mountain Troll ", 180, 30, 25),
                new Mob(" Apprentice Mage ", 65, 18, 12),
                new Mob(" Swamp Lizard ", 85, 13, 16),
                new Mob(" Wraith ", 60, 20, 10),
                new Mob(" Royal Guard ", 110, 20, 25),
        };
        this.enemy = mobs[rand.nextInt(mobs.length)];
    }

    public CombatEvent(Mob enemy) {
        super("An enemy appears");
        this.enemy = enemy;
    }

    @Override
    public void trigger(Player p, GameScreenForm screen) {
        this.currentPlayer = p;
        this.gameScreen = screen;


        if (previousBackgroundClip != null) {
            SoundManager.stopMusic(previousBackgroundClip);
        }


        combatMusic = SoundManager.playMusic("/resources/battle.wav");

        gameScreen.setNewLog("Combat Started! A wild " + enemy.getName().replace("###FINAL_BOSS### ", "") + " appears!");
        gameScreen.displayMessage("Enemy HP: " + enemy.getHp() + ".\nChoose your action:");
        gameScreen.updateStatsDisplay();


        this.backToCombatMenuListener = e -> {
            gameScreen.displayMessage("Canceled selection. Choose your action:");
            setupCombatButtons();
        };

        setupCombatButtons();
    }

    private void setupCombatButtons() {
        gameScreen.setChoiceButtons(true);

        JButton btnAttack = new JButton("Attack");
        btnAttack.addActionListener(e -> handleAttack());
        gameScreen.setChoiceButton(1, btnAttack);

        JButton btnItem = new JButton("Item");
        btnItem.addActionListener(e -> setupCombatItemSubMenu());
        gameScreen.setChoiceButton(2, btnItem);

        JButton btnEquip = new JButton("Equip");
        btnEquip.addActionListener(e -> setupEquipMenu());
        gameScreen.setChoiceButton(3, btnEquip);

        JButton btnRun = new JButton("Run (" + escapeChance + "%)");
        btnRun.addActionListener(e -> handleRun());
        gameScreen.setChoiceButton(4, btnRun);

        gameScreen.updateChoicePanel();
    }


    private void handleAttack() {
        SoundManager.playSound("/resources/swordslash.wav");

        int damageDealt = currentPlayer.attack(enemy);
        String message = currentPlayer.getName() + " attacks " + enemy.getName().replace("###FINAL_BOSS### ", "") + " for " + damageDealt + " damage!";
        String message1 = "Enemy HP: " + enemy.getHp();

        if (enemy.isDead()) {
            handleVictory(message);
            return;
        }

        handleEnemyTurn(message + "  " + message1);

    }

    private void handleEnemyTurn(String playerActionMessage) {
        if (playerActionMessage != null) {
            gameScreen.displayMessage(playerActionMessage);
        }

        int dmg = enemy.attack();
        currentPlayer.takeDamage(dmg);
        String enemyMessage = enemy.getName().replace("###FINAL_BOSS### ", "") + " attacks you for " + dmg + " damage! Your HP: " + currentPlayer.getHp();

        gameScreen.updateStatsDisplay();

        if (currentPlayer.getHp() <= 0) {
            handleDefeat(enemyMessage);
        } else {
            gameScreen.displayMessage(enemyMessage + "\n\nChoose your next action:");
            setupCombatButtons();
        }
    }


    private int calculateManaRegen() {
        return (currentPlayer.getDexterity()) * 10;
    }


    private void handleVictory(String playerActionMessage) {


        if (enemy.getName().contains("###FINAL_BOSS###")) {
            gameScreen.displayMessage(playerActionMessage + "\n\nYOU HAVE DEFEATED THE SUDOKU KING!");


            if (combatMusic != null) {
                SoundManager.stopMusic(combatMusic);
            }

            gameScreen.showCredits();
            return;
        }


        int rcoins = rand.nextInt(50);
        currentPlayer.addcoins(rcoins);


        int manaRegen = calculateManaRegen();
        currentPlayer.setMana(currentPlayer.getMana() + manaRegen);

        gameScreen.displayMessage(
                playerActionMessage +
                        "\n\nYou have slain " + enemy.getName().replace("###FINAL_BOSS### ", "") + "! Received: " + rcoins + " Coins!" +
                        "\nMana Regenerated: " + manaRegen + " (Current Mana: " + currentPlayer.getMana() + ")"
        );
        endCombat();
    }

    private void handleDefeat(String lastMessage) {
        gameScreen.displayMessage(lastMessage + "\n\nYOU DIED. Game Over.");


        JOptionPane.showMessageDialog(
                gameScreen.getMainGamePanel(),
                "Game Over! Your adventure ends here.",
                "DEFEAT",
                JOptionPane.WARNING_MESSAGE
        );


        if (combatMusic != null) {
            SoundManager.stopMusic(combatMusic);
        }


        JFrame gameFrame = (JFrame) SwingUtilities.getWindowAncestor(gameScreen.getMainGamePanel());
        if (gameFrame != null) {
            gameFrame.dispose();
            SwingUtilities.invokeLater(MainMenu::new);
        } else {
            System.exit(0);
        }

    }

    private void handleRun() {
        int value = rand.nextInt(100) + 1;
        if (value <= escapeChance) {
            gameScreen.displayMessage("You successfully escaped the fight!");
            endCombat();
        } else {
            gameScreen.displayMessage("You tried to run, but got stuck!");
            escapeChance -= 20;
            if (escapeChance < 10) escapeChance = 10;
            handleEnemyTurn(null);
        }
    }


    private void setupCombatItemSubMenu() {

        gameScreen.setupCombatItemMenu(backToCombatMenuListener);


        JButton btnPotion = gameScreen.getChoiceButton(1);
        JButton btnSpell = gameScreen.getChoiceButton(2);


        for (ActionListener al : btnPotion.getActionListeners()) {
            btnPotion.removeActionListener(al);
        }
        btnPotion.addActionListener(e -> setupUsePotionMenu());


        for (ActionListener al : btnSpell.getActionListeners()) {
            btnSpell.removeActionListener(al);
        }
        btnSpell.addActionListener(e -> setupUseSpellMenu());

        gameScreen.displayMessage("Choose an item type to use:");
        gameScreen.updateChoicePanel();
    }


    private void setupUsePotionMenu() {
        List<Item> inventory = currentPlayer.getInventory();
        gameScreen.setChoiceButtons(false);

        List<Item> potions = inventory.stream()
                .filter(item -> item instanceof Potion)
                .toList();

        if (potions.isEmpty()) {
            gameScreen.displayMessage("You have no potions! Returning to item menu.");
            setupCombatItemSubMenu();
            return;
        }

        gameScreen.displayMessage("Choose a potion to use:");

        int buttonIndex = 1;

        for (int i = 0; i < inventory.size() && buttonIndex <= 3; i++) {
            Item item = inventory.get(i);
            if (item instanceof Potion) {
                JButton btnPotion = new JButton("Use " + item.getName());
                final int originalIndex = i;
                btnPotion.addActionListener(e -> handleUseItem(originalIndex));
                gameScreen.setChoiceButton(buttonIndex, btnPotion);
                buttonIndex++;
            }
        }

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(e -> {
            gameScreen.displayMessage("Canceled potion selection.");
            setupCombatItemSubMenu();
        });
        gameScreen.setChoiceButton(4, btnBack);

        gameScreen.updateChoicePanel();
    }

    private void setupUseSpellMenu() {
        List<Item> inventory = currentPlayer.getInventory();
        gameScreen.setChoiceButtons(false);

        List<Item> spells = inventory.stream()
                .filter(item -> item instanceof Spell)
                .toList();

        if (spells.isEmpty()) {
            gameScreen.displayMessage("You have no spells! Returning to item menu.");
            setupCombatItemSubMenu();
            return;
        }

        gameScreen.displayMessage("Choose a spell to cast (Mana: " + currentPlayer.getMana() + "):");

        int buttonIndex = 1;

        for (int i = 0; i < inventory.size() && buttonIndex <= 3; i++) {
            Item item = inventory.get(i);
            if (item instanceof Spell s) {
                String btnText = s.getName() + " (DMG: " + s.getDamage() + ", Cost: " + s.getManaCost() + ")";
                JButton btnSpell = new JButton(btnText);


                if (currentPlayer.getMana() < s.getManaCost()) {
                    btnSpell.setEnabled(false);
                }

                final int originalIndex = i;
                btnSpell.addActionListener(e -> handleUseSpell(originalIndex));
                gameScreen.setChoiceButton(buttonIndex, btnSpell);
                buttonIndex++;
            }
        }


        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(e -> {
            gameScreen.displayMessage("Canceled spell selection.");
            setupCombatItemSubMenu();
        });
        gameScreen.setChoiceButton(4, btnBack);

        gameScreen.updateChoicePanel();
    }


    private void handleUseSpell(int itemIndex) {
        List<Item> inventory = currentPlayer.getInventory();
        Spell spellToUse = (Spell) inventory.get(itemIndex);


        if (currentPlayer.getMana() < spellToUse.getManaCost()) {
            gameScreen.displayMessage("Not enough mana to cast " + spellToUse.getName() + "! Returning to spell menu.");
            setupUseSpellMenu();
            return;
        }

        enemy.takeDamage(spellToUse.getDamage());

        currentPlayer.setMana(currentPlayer.getMana() - spellToUse.getManaCost());

        String message = currentPlayer.getName() + " casts " + spellToUse.getName() + " for " + spellToUse.getDamage() + " damage! Mana: -" + spellToUse.getManaCost();
        String message1 = "Enemy HP: " + enemy.getHp();

        if (enemy.isDead()) {
            handleVictory(message);
            return;
        }

        handleEnemyTurn(message + " " + message1);
    }


    private void handleUseItem(int itemIndex) {
        List<Item> inventory = currentPlayer.getInventory();
        Item itemToUse = inventory.get(itemIndex);

        itemToUse.use(currentPlayer);
        inventory.remove(itemIndex);

        gameScreen.displayMessage(itemToUse.getName() + " used! " + enemy.getName().replace("###FINAL_BOSS### ", "") + " ripostes!");
        gameScreen.updateStatsDisplay();

        handleEnemyTurn(null);
    }

    private void setupEquipMenu() {
        List<Item> inventory = currentPlayer.getInventory();
        gameScreen.setChoiceButtons(false);

        if (inventory.isEmpty()) {
            gameScreen.displayMessage("Inventory is empty! Nothing to equip. Choose your action:");
            setupCombatButtons();
            return;
        }

        gameScreen.displayMessage("Choose an item to equip:");

        int buttonIndex = 1;
        for (int i = 0; i < inventory.size(); i++) {
            Item item = inventory.get(i);

            if ((item instanceof Weapon || item instanceof Armor || item instanceof Spell) && buttonIndex <= 3) {
                String type;
                if (item instanceof Weapon) type = "WPN";
                else if (item instanceof Armor) type = "ARM";
                else type = "SPL"; // NOU

                JButton btnEquipItem = new JButton(type + ": " + item.getName());
                final int originalIndex = i;
                btnEquipItem.addActionListener(e -> handleEquipItem(originalIndex));
                gameScreen.setChoiceButton(buttonIndex, btnEquipItem);
                buttonIndex++;
            }
        }

        JButton btnBack = new JButton("Back");
        btnBack.addActionListener(backToCombatMenuListener);
        gameScreen.setChoiceButton(4, btnBack);

        gameScreen.updateChoicePanel();
    }

    private void handleEquipItem(int itemIndex) {
        List<Item> inventory = currentPlayer.getInventory();
        Item itemToEquip = inventory.get(itemIndex);

        itemToEquip.use(currentPlayer);

        gameScreen.displayMessage(itemToEquip.getName() + " equipped! " + enemy.getName().replace("###FINAL_BOSS### ", "") + " ripostes!");
        gameScreen.updateStatsDisplay();

        handleEnemyTurn(null);
    }

    private void endCombat() {

        SoundManager.stopMusic(combatMusic);


        Clip newBackgroundClip = SoundManager.playMusic("/resources/Song1.wav");


        gameScreen.setGameBackgroundClip(newBackgroundClip);

        gameScreen.setupMainMenuButtons();
    }
}