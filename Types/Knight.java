public class Knight extends Type{
    public Knight(int health, int maxHealth, int speed, int mana){
        super("Knight",health,maxHealth,speed,mana);
        this.setMainWeapon(Item.getItemFromName("Sword"));
        this.setMainArmor(Item.getArmorFromName("Iron Armor"));
        this.addIllegalWeapon(Item.getItemFromName("Magic Staff"));
    }

    public Knight(int health, int speed, int mana){
        super("Knight",health,speed,mana);
        this.setMainWeapon(Item.getItemFromName("Sword"));
        this.setMainArmor(Item.getArmorFromName("Iron Armor"));
        this.addIllegalWeapon(Item.getItemFromName("Magic Staff"));
    }

    public Knight(){
        super("Knight",50,10,5);
        this.setMainWeapon(Item.getItemFromName("Sword"));
        this.setMainArmor(Item.getArmorFromName("Iron Armor"));
        this.addIllegalWeapon(Item.getItemFromName("Magic Staff"));
    }
}
