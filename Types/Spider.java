public class Spider extends Type {
    public Spider(int health, int maxHealth, int speed, int mana){
        super("Spider",health,maxHealth,speed,mana);
        this.setMainWeapon(Item.getItemFromName("Bite"));
    }

    public Spider(int health, int speed, int mana){
        super("Spider",health,speed,mana);
        this.setMainWeapon(Item.getItemFromName("Bite"));
    }

    public Spider(){
        super("Spider",16,20,0);
        this.setMainWeapon(Item.getItemFromName("Bite"));
    }
}
