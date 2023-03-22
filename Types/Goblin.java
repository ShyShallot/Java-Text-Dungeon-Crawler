public class Goblin extends Type{
    public Goblin(int health, int maxHealth, int speed, int mana){
        super("Goblin",health,maxHealth,speed,mana);
        this.setMainWeapon(Item.getItemFromName("Dagger"));
        this.setMainArmor(Item.getArmorFromName("Goblin Armor"));
        this.addIllegalWeapon(Item.getItemFromName("Sword"));
        this.addIllegalWeapon(Item.getItemFromName("Magic Staff"));
        this.addIllegalWeapon(Item.getItemFromName("Club"));
        this.addIllegalWeapon(Item.getItemFromName("Wooden Bow"));
    }

    public Goblin(int health, int speed, int mana){
        super("Goblin",health,speed,mana);
        this.setMainWeapon(Item.getItemFromName("Dagger"));
        this.setMainArmor(Item.getArmorFromName("Goblin Armor"));
        this.addIllegalWeapon(Item.getItemFromName("Sword"));
        this.addIllegalWeapon(Item.getItemFromName("Magic Staff"));
        this.addIllegalWeapon(Item.getItemFromName("Club"));
        this.addIllegalWeapon(Item.getItemFromName("Wooden Bow"));
    }

    public Goblin(){
        super("Goblin",20,12,15);
        this.setMainWeapon(Item.getItemFromName("Dagger"));
        this.setMainArmor(Item.getArmorFromName("Goblin Armor"));
        this.addIllegalWeapon(Item.getItemFromName("Sword"));
        this.addIllegalWeapon(Item.getItemFromName("Magic Staff"));
        this.addIllegalWeapon(Item.getItemFromName("Club"));
        this.addIllegalWeapon(Item.getItemFromName("Wooden Bow"));
    }
}
