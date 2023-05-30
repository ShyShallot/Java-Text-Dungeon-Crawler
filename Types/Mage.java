
public class Mage extends Type{
    public Mage(int health, int maxHealth, int speed, int mana){
        super("Mage",health,maxHealth,speed,mana);
        this.setMainWeapon(Item.getItemFromName("Magic Staff"));
        this.addIllegalWeapon(Item.getItemFromName("Sword"));
        this.addIllegalWeapon(Item.getItemFromName("Heavy Club"));
        this.addIllegalWeapon(Item.getItemFromName("Wooden Bow"));
    }

    public Mage(int health, int speed, int mana){
        super("Mage",health,speed,mana);
        this.setMainWeapon(Item.getItemFromName("Magic Staff"));
        this.addIllegalWeapon(Item.getItemFromName("Sword"));
        this.addIllegalWeapon(Item.getItemFromName("Heavy Club"));
        this.addIllegalWeapon(Item.getItemFromName("Wooden Bow"));
    }

    public Mage(){
        super("Mage",30,8,30);
        this.setMainWeapon(Item.getItemFromName("Magic Staff"));
        this.addIllegalWeapon(Item.getItemFromName("Sword"));
        this.addIllegalWeapon(Item.getItemFromName("Heavy Club"));
        this.addIllegalWeapon(Item.getItemFromName("Wooden Bow"));
    }
}
