public class Goblin extends Type{
    public Goblin(int health, int maxHealth, int speed, int mana){
        super("Goblin",health,maxHealth,speed,mana);
        this.setMainWeapon(new Dagger());
        this.setMainArmor(new GoblinArmor());
        this.addIllegalWeapon(new Sword());
        this.addIllegalWeapon(new Staff());
        this.addIllegalWeapon(new Club());
        this.addIllegalWeapon(new Bow());
    }

    public Goblin(int health, int speed, int mana){
        super("Goblin",health,speed,mana);
        this.setMainWeapon(new Dagger());
        this.setMainArmor(new GoblinArmor());
        this.addIllegalWeapon(new Sword());
        this.addIllegalWeapon(new Staff());
        this.addIllegalWeapon(new Club());
        this.addIllegalWeapon(new Bow());
    }

    public Goblin(){
        super("Goblin",20,12,15);
        this.setMainWeapon(new Dagger());
        this.setMainArmor(new GoblinArmor());
        this.addIllegalWeapon(new Sword());
        this.addIllegalWeapon(new Staff());
        this.addIllegalWeapon(new Club());
        this.addIllegalWeapon(new Bow());
    }
}
