public class Goblin extends Type{
    public Goblin(int health, int maxHealth, int speed, int mana){
        super("Goblin",health,maxHealth,speed,mana);
        this.setMainWeapon(new Dagger());
        this.setMainArmor(new GoblinArmor());
    }

    public Goblin(int health, int speed, int mana){
        super("Goblin",health,speed,mana);
        this.setMainWeapon(new Dagger());
        this.setMainArmor(new GoblinArmor());
    }

    public Goblin(){
        super("Goblin",20,12,15);
        this.setMainWeapon(new Dagger());
        this.setMainArmor(new GoblinArmor());
    }
}
