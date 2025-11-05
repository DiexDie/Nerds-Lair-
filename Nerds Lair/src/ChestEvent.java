import java.util.Random;
import java.util.List;
import java.util.Arrays;
import java.util.Set;
import java.util.Collections;
import java.util.stream.Collectors;
import javax.swing.JButton;
import javax.swing.Timer;
import javax.sound.sampled.Clip;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ChestEvent extends Event {
    private GameScreenForm gameScreen;
    private Player currentPlayer;
    private static final Random rand = new Random();

    private Clip chestMusic;
    private Clip previousBackgroundClip;

    public void setBackgroundClip(Clip clip) {
        this.previousBackgroundClip = clip;
    }


    private static final Set<PlayerClass> WARRIOR_ONLY = Collections.unmodifiableSet(Set.of(PlayerClass.WARRIOR));
    private static final Set<PlayerClass> ARCHER_ONLY = Collections.unmodifiableSet(Set.of(PlayerClass.ARCHER));
    private static final Set<PlayerClass> ALL_CLASSES = Collections.unmodifiableSet(Set.of(PlayerClass.WARRIOR, PlayerClass.ARCHER));


    private static String formatItemName(String name, Rarity rarity) {
        String trimmedName = name.trim();
        return trimmedName + " (" + rarity.getDisplayName() + ")";
    }

    private static Weapon createWeapon(String name, int damage, Rarity rarity, Set<PlayerClass> classes) {
        return new Weapon(formatItemName(name, rarity), damage, rarity, classes);
    }

    private static Armor createArmor(String name, int defense, Rarity rarity, Set<PlayerClass> classes) {
        return new Armor(formatItemName(name, rarity), defense, rarity, classes);
    }


    private static Spell createSpell(String name, int damage, int manaCost, Rarity rarity, Set<PlayerClass> classes) {
        String fullName = " Book of " + name.trim();
        return new Spell(formatItemName(fullName, rarity), damage, manaCost, rarity, classes);
    }


    private static Potion createHealthPotion(String name, int heal, Rarity rarity) {
        return new Potion(formatItemName(name, rarity), heal, 0);
    }

    private static Potion createManaPotion(String name, int manaRestore, Rarity rarity) {
        return new Potion(formatItemName(name, rarity), 0, manaRestore);
    }


    public static final List<Potion> availablePotions = Arrays.asList(
            createHealthPotion(" Health Potion ", 20, Rarity.COMMON),
            createHealthPotion(" Greater Healing Potion ", 50, Rarity.RARE),
            createManaPotion(" Mana Potion (Small) ", 15, Rarity.UNCOMMON),
            createManaPotion(" Mana Potion (Great) ", 40, Rarity.EPIC)
    );


    public static final List<Weapon> availableWeapons = Arrays.asList(
            createWeapon(" Rusty Dagger ", 3, Rarity.COMMON, ALL_CLASSES),
            createWeapon(" Sharp Stone Axe ", 5, Rarity.COMMON, ALL_CLASSES),
            createWeapon(" Diamond Dagger ", 10, Rarity.RARE, ALL_CLASSES),
            createWeapon(" Mithril Longsword ", 14, Rarity.RARE, WARRIOR_ONLY),
            createWeapon(" Heavy Mace ", 15, Rarity.EPIC, WARRIOR_ONLY),
            createWeapon(" Knight's Lance ", 12, Rarity.RARE, WARRIOR_ONLY),
            createWeapon(" Training Sword ", 4, Rarity.COMMON, WARRIOR_ONLY),
            createWeapon(" Obsidian Warhammer ", 18, Rarity.EPIC, WARRIOR_ONLY),
            createWeapon(" Bronze Broadsword ", 7, Rarity.UNCOMMON, WARRIOR_ONLY),
            createWeapon(" Simple Bow ", 4, Rarity.COMMON, ARCHER_ONLY),
            createWeapon(" Long Bow ", 7, Rarity.UNCOMMON, ARCHER_ONLY),
            createWeapon(" Hunter's Crossbow ", 8, Rarity.RARE, ARCHER_ONLY),
            createWeapon(" Elven Recurve Bow ", 18, Rarity.EPIC, ARCHER_ONLY),
            createWeapon(" Throwing Knives ", 6, Rarity.UNCOMMON, ARCHER_ONLY),
            createWeapon(" Iron Dart ", 3, Rarity.COMMON, ARCHER_ONLY),
            createWeapon(" Composite Bow ", 11, Rarity.RARE, ARCHER_ONLY),
            createWeapon(" Ancient Pistol ", 20, Rarity.EPIC, ARCHER_ONLY)
    );

    public static final List<Armor> availableArmor = Arrays.asList(
            createArmor(" Ragged Tunic ", 2, Rarity.COMMON, ALL_CLASSES),
            createArmor(" Leather Vest ", 5, Rarity.COMMON, ALL_CLASSES),
            createArmor(" Steel Shield ", 10, Rarity.RARE, ALL_CLASSES),
            createArmor(" Iron Helmet ", 8, Rarity.UNCOMMON, WARRIOR_ONLY),
            createArmor(" Full Plate Greaves ", 15, Rarity.EPIC, WARRIOR_ONLY),
            createArmor(" Chainmail Coif ", 10, Rarity.RARE, WARRIOR_ONLY),
            createArmor(" Padded Shoulders ", 4, Rarity.COMMON, WARRIOR_ONLY),
            createArmor(" Aegis Breastplate ", 22, Rarity.EPIC, WARRIOR_ONLY),
            createArmor(" Iron Gauntlets ", 6, Rarity.UNCOMMON, WARRIOR_ONLY),
            createArmor(" Simple Hood ", 3, Rarity.COMMON, ARCHER_ONLY),
            createArmor(" Studded Leather ", 7, Rarity.UNCOMMON, ARCHER_ONLY),
            createArmor(" Quiver of Endless Arrows ", 5, Rarity.RARE, ARCHER_ONLY),
            createArmor(" Cloak of Shadows ", 15, Rarity.EPIC, ARCHER_ONLY),
            createArmor(" Leather Boots ", 3, Rarity.COMMON, ARCHER_ONLY),
            createArmor(" Ranger Cloak ", 9, Rarity.RARE, ARCHER_ONLY),
            createArmor(" Swift Gloves ", 5, Rarity.UNCOMMON, ARCHER_ONLY),
            createArmor(" Ghillie Suit ", 18, Rarity.EPIC, ARCHER_ONLY)
    );

    public static final List<Spell> availableSpells = Arrays.asList(
            createSpell(" Fire Bolt ", 8, 5, Rarity.COMMON, ALL_CLASSES),
            createSpell(" Frost Shard ", 7, 5, Rarity.COMMON, ALL_CLASSES),
            createSpell(" Magic Missile ", 12, 8, Rarity.UNCOMMON, ALL_CLASSES),
            createSpell(" Wind Slash ", 10, 7, Rarity.UNCOMMON, ALL_CLASSES),
            createSpell(" Lightning Orb ", 18, 12, Rarity.RARE, ALL_CLASSES),
            createSpell(" Arcane Focus ", 15, 10, Rarity.RARE, ALL_CLASSES),
            createSpell(" Chain Lightning ", 20, 15, Rarity.RARE, ALL_CLASSES),
            createSpell(" Meteor Strike ", 30, 25, Rarity.EPIC, ALL_CLASSES),
            createSpell(" Blazing Inferno ", 25, 20, Rarity.EPIC, ALL_CLASSES),
            createSpell(" Void Drain ", 35, 30, Rarity.EPIC, ALL_CLASSES)
    );


    public static <T extends Item> T getRandomCompatibleItem(List<T> sourceList, PlayerClass playerClass) {
        List<T> compatibleItems = sourceList.stream()
                .filter(item -> item.getCompatibleClasses().contains(playerClass) || item.getCompatibleClasses().isEmpty())
                .collect(Collectors.toList());

        if (compatibleItems.isEmpty()) {
            return null;
        }
        return compatibleItems.get(rand.nextInt(compatibleItems.size()));
    }


    public ChestEvent() {
        super("You found a Chest!");
    }


    @Override
    public void trigger(Player p, GameScreenForm screen) {
        this.currentPlayer = p;
        this.gameScreen = screen;

        if (previousBackgroundClip != null) {
            SoundManager.stopMusic(previousBackgroundClip);
        }
        chestMusic = SoundManager.playMusic("resources/chestsound.wav");

        gameScreen.setNewLog(description);
        gameScreen.displayMessage("The Chest opens....");


        Timer delayTimer = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ((Timer) e.getSource()).stop();


                handleChestDropLogic();
            }
        });
        delayTimer.setRepeats(false);
        delayTimer.start();

    }


    private void handleChestDropLogic() {
        int drop = rand.nextInt(100);
        String dropMessage;
        PlayerClass playerClass = currentPlayer.getPlayerClass();

        if (drop < 35) {
            Potion p = availablePotions.get(rand.nextInt(availablePotions.size()));

            currentPlayer.addItem(p);


            String effect = "";
            if (p.getHealAmount() > 0) effect += " (+" + p.getHealAmount() + " HP)";
            if (p.getManaRestoreAmount() > 0) effect += " (+" + p.getManaRestoreAmount() + " Mana)";

            dropMessage = "You found a Potion: " + p.getName() + effect + ".";

        } else if (drop < 60) {
            Weapon w = getRandomCompatibleItem(availableWeapons, playerClass);

            if (w != null) {
                currentPlayer.addItem(w);
                dropMessage = "You found a Weapon: " + w.getName();
            } else {
                currentPlayer.addcoins(25);
                dropMessage = "Chest items were incompatible! You received **25 Coins** instead.";
            }

        } else if (drop < 80) {
            Armor a = getRandomCompatibleItem(availableArmor, playerClass);

            if (a != null) {
                currentPlayer.addItem(a);
                dropMessage = "You found Armor: " + a.getName();
            } else {
                currentPlayer.addcoins(25);
                dropMessage = "Chest items were incompatible! You received **25 Coins** instead.";
            }

        } else if (drop < 90) {
            Spell s = getRandomCompatibleItem(availableSpells, playerClass);

            if (s != null) {
                currentPlayer.addItem(s);
                dropMessage = "You found a Spell: " + s.getName() + " (Damage: " + s.getDamage() + ", Mana Cost: " + s.getManaCost() + ")";
            } else {
                currentPlayer.addcoins(25);
                dropMessage = "Chest items were incompatible! You received **25 Coins** instead.";
            }

        } else {
            int rrcoins = rand.nextInt(150);
            currentPlayer.addcoins(rrcoins);
            dropMessage = "You found: " + rrcoins + " Coins!";
        }


        gameScreen.displayMessage(dropMessage);
        gameScreen.updateStatsDisplay();


        setupContinueButton();
    }


    private void setupContinueButton() {
        gameScreen.setChoiceButtons(false);
        JButton btnContinue = new JButton("Continue");

        btnContinue.addActionListener(e -> {

            SoundManager.stopMusic(chestMusic);


            Clip newBackgroundClip = SoundManager.playMusic("resources/Song1.wav");


            gameScreen.setGameBackgroundClip(newBackgroundClip);

            gameScreen.setupMainMenuButtons();
        });

        gameScreen.setChoiceButton(1, btnContinue);
        gameScreen.updateChoicePanel();
    }

}