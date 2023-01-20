public class Goblin extends Type{
    public Goblin(int health, int maxHealth, int speed, int mana){
        super("Goblin",health,maxHealth,speed,mana);
        this.setMainWeapon(new Dagger());
    }

    public Goblin(int health, int speed, int mana){
        super("Goblin",health,speed,mana);
        this.setMainWeapon(new Dagger());
    }

    public Goblin(){
        super("Goblin",20,12,15);
        this.setMainWeapon(new Dagger());
    }
}
