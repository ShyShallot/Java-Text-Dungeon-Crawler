
public class Skeleton extends Type{
    public Skeleton(int health, int maxHealth, int speed, int mana){
        super("Skeleton",health,maxHealth,speed,mana);
        this.setMainWeapon(new Sword());
    }

    public Skeleton(int health, int speed, int mana){
        super("Skeleton",health,speed,mana);
        this.setMainWeapon(new Sword());
    }

    public Skeleton(){
        super("Skeleton",15,10,8);
        this.setMainWeapon(new Sword());
    }
}
