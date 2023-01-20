
public class Mage extends Type{
    public Mage(int health, int maxHealth, int speed, int mana){
        super("Mage",health,maxHealth,speed,mana);
        this.setMainWeapon(new Staff());
    }

    public Mage(int health, int speed, int mana){
        super("Mage",health,speed,mana);
        this.setMainWeapon(new Staff());
    }

    public Mage(){
        super("Mage",30,8,30);
        this.setMainWeapon(new Staff());
    }
}
