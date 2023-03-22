
public class Skeleton extends Type{
    public Skeleton(int health, int maxHealth, int speed, int mana){
        super("Skeleton",health,maxHealth,speed,mana);
        this.setMainWeapon(Item.getItemFromName("Wooden Bow"));
        this.setMainArmor(Item.getArmorFromName("Skeleton Armor"));
    }

    public Skeleton(int health, int speed, int mana){
        super("Skeleton",health,speed,mana);
        this.setMainWeapon(Item.getItemFromName("Wooden Bow"));
        this.setMainArmor(Item.getArmorFromName("Skeleton Armor"));
    }

    public Skeleton(){
        super("Skeleton",15,12,8);
        this.setMainWeapon(Item.getItemFromName("Wooden Bow"));
        this.setMainArmor(Item.getArmorFromName("Skeleton Armor"));
    }
}
