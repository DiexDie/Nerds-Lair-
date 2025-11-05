import java.util.Random;

public class Mob {
    private final String name;
    private int hp;
    private final int attackMin;
    private final int attackMax;

    public Mob(String name, int hp, int attackMin, int attackMax) {
        this.name = name;
        this.hp = hp;
        this.attackMin = attackMin;
        this.attackMax = attackMax;
    }

    public String getName() { return name; }
    public int getHp() { return hp; }
    public boolean isDead() { return hp <= 0; }

    public void takeDamage(int dmg) {
        hp -= dmg;
        if (hp < 0) hp = 0;
    }

    public int attack() {
        Random rand = new Random();


        int range = attackMax - attackMin + 1;



        if (range <= 0) {
            range = 1;
        }


        return rand.nextInt(range) + attackMin;
    }

    @Override
    public String toString() {
        return name + " [HP: " + hp + "]";
    }
}