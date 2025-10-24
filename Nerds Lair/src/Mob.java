import java.util.Random;

public class Mob {
    private String name;
    private int hp;
    private int attackMin;
    private int attackMax;

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
        return rand.nextInt(attackMax - attackMin + 1) + attackMin;
    }

    @Override
    public String toString() {
        return name + " [HP: " + hp + "]";
    }
}
