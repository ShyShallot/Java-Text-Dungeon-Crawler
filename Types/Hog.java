public class Hog extends Type{
    public Hog(int health, int maxHealth, int speed, int mana){
        super("Hog",health,maxHealth,speed,mana);
        this.setMainWeapon(new Ram());
    }

    public Hog(int health, int speed, int mana){
        super("Hog",health,speed,mana);
        this.setMainWeapon(new Ram());
    }

    public Hog(){
        super("Hog",50,5,0);
        this.setMainWeapon(new Ram());
    }
}
