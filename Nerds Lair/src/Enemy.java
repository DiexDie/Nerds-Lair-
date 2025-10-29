public class Enemy {
    private String name;
    private int hp;
    private int attack;

    public Enemy(String name,int hp,int attack){
        this.name = name;
        this.hp = hp;
        this.attack = attack;
    }

    public boolean isAlive(){return hp>0;}

    public void takeDamage(int dmg){
        hp -= dmg;
        if(hp<0) hp=0;
        System.out.println(  name + "∩༼˵☯‿☯˵༽つ¤=[]:::::> "+ " received "+dmg+" damage! HP: "+hp);
    }

    public int getAttack(){return attack;}
    public String getName(){return name;}
    public int getHp(){return hp;}
}
