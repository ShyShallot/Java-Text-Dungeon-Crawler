import java.util.*;
public class Type {
    private String typeName;
    private Item mainWeapon;
    private Armor mainArmor;
    private ArrayList<Item> prohibitedWeapons = new ArrayList<>();
    private BaseProperties baseStats;

    public Type(String name, int health, int maxHealth, int speed, int mana ,Item mainWeapon){
        this.baseStats = new BaseProperties(health, maxHealth, speed, mana);
        this.typeName = name;
        this.baseStats.setHealth(health, maxHealth);
        this.baseStats.setSpeed(speed);
        this.baseStats.setMana(mana);
        this.mainWeapon = mainWeapon;
    }

    public Type(String name, int health, int speed, int mana ,Item mainWeapon){
        this.baseStats = new BaseProperties(health, speed, mana);
        this.typeName = name;
        this.baseStats.setHealth(health);
        this.baseStats.setSpeed(speed);
        this.baseStats.setMana(mana);
        this.mainWeapon = mainWeapon;
    }

    public Type(String name, int health, int maxHealth, int speed, int mana ,Item mainWeapon, Armor mainArmor){
        this.baseStats = new BaseProperties(health, maxHealth, speed, mana);
        this.typeName = name;
        this.baseStats.setHealth(health, maxHealth);
        this.baseStats.setSpeed(speed);
        this.baseStats.setMana(mana);
        this.mainWeapon = mainWeapon;
        this.mainArmor = mainArmor;
    }

    public Type(String name, int health, int speed, int mana ,Item mainWeapon, Armor mainArmor){
        this.baseStats = new BaseProperties(health, speed, mana);
        this.typeName = name;
        this.baseStats.setHealth(health);
        this.baseStats.setSpeed(speed);
        this.baseStats.setMana(mana);
        this.mainWeapon = mainWeapon;
        this.mainArmor = mainArmor;
    }

    public Type(String name, int health, int maxHealth, int speed, int mana){
        this.baseStats = new BaseProperties(health, maxHealth, speed, mana);
        this.typeName = name;
    }

    public Type(String name, int health, int speed, int mana){
        this.baseStats = new BaseProperties(health, speed, mana);
        this.typeName = name;
    }

    public Type(){
        // Empty type
    }

    public void setMainWeapon(Item weap){
        if(weap.isHeal()){
            return;
        }
        this.mainWeapon = weap;
    }

    public void setMainWeapon(){
        this.mainWeapon = null;
    }

    public void setMainArmor(Armor armor){
        this.mainArmor = armor;
    }

    public Armor getMainArmor(){
        return this.mainArmor;
    }

    public void addIllegalWeapon(Item weap){
        this.prohibitedWeapons.add(weap);
    }

    public void removeIllegalWeapon(Item weap){
        for(int i=0;i<this.prohibitedWeapons.size();i++){
            Item curWeap = this.prohibitedWeapons.get(i);
            if(curWeap.getName().equals(weap.getName())){
                this.prohibitedWeapons.remove(i);
            }
        }
    }

    public void removeIllegalWeapon(String weap){
        for(int i=0;i<this.prohibitedWeapons.size();i++){
            Item curWeap = this.prohibitedWeapons.get(i);
            if(curWeap.getName().equals(weap)){
                this.prohibitedWeapons.remove(i);
            }
        }
    }

    public void removeIllegalWeapon(int index){
        this.prohibitedWeapons.remove(index);
    }

    public String getName(){
        return this.typeName;
    }

    public Item getMainWeapon(){
        return this.mainWeapon;
    }

    public ArrayList<Item> getIllegalItems(){
        return this.prohibitedWeapons;
    }

    public boolean isItemIllegal(Item item){
        boolean illegal = false;
        for(Item itm : this.prohibitedWeapons){
            if(item.getName() == itm.getName()){
                illegal = true;
                break;
            }
        }
        return illegal;
    }

    public BaseProperties getStatProps(){
        return this.baseStats;
    }

    public int baseHealth(){
        return this.baseStats.baseHealth();
    }

    public int baseMaxHealth(){
        return this.baseStats.baseMaxHealth();
    }
    
    public int baseSpeed(){
        return this.baseStats.baseSpeed();
    }

    public int baseMana(){
        return this.baseStats.baseMana();
    }

    public String toString(){
        return String.format("Type Name: %s, Base Props: %s", this.typeName, this.baseStats.toString());
    }

    public String toString(boolean player){
        return String.format("Type Name: %s, Base Props: %s", this.typeName, this.baseStats.toString(player));
    }

}


class BaseProperties {
    private int baseHealth;
    private int baseMaxHealth;
    private int baseSpeed;
    private int baseMana;

    public BaseProperties(int health, int maxHealth, int speed, int maxMana){
        this.baseHealth = health;
        this.baseMaxHealth = maxHealth;
        this.baseSpeed = speed;
        this.baseMana = maxMana;
    }

    public BaseProperties(int health, int speed, int mana){
        this.baseHealth = health;
        this.baseMaxHealth = health;
        this.baseSpeed = speed;
        this.baseMana = mana;
    }
    
    public void setHealth(int health){
        this.baseHealth = health;
        this.baseMaxHealth = this.baseHealth;
    }
    
    public void setHealth(int health, int maxHealth){
        this.baseHealth = health;
        this.baseMaxHealth = maxHealth;
    }

    public void setSpeed(int speed){
        this.baseSpeed = speed;
    }

    public void setMana(int mana){
        this.baseMana = mana;
    }
    
    public int baseHealth(){
        return this.baseHealth;
    }

    public int baseMaxHealth(){
        return this.baseMaxHealth;
    }

    public int baseSpeed(){
        return this.baseSpeed;
    }

    public int baseMana(){
        return this.baseMana;
    }

    public String toString(){
        return String.format("Health %s, Max Health %s, Speed %s, Mana %s",CusLib.colorText(this.baseHealth, "green"), CusLib.colorText(this.baseMaxHealth,"blue"), CusLib.colorText(this.baseSpeed,"yellow"), CusLib.colorText(this.baseMana,"cyan"));
    }
    
    public String toString(boolean player){
        int health = this.baseHealth;
        int maxHealth = this.baseMaxHealth;
        if(player){
            health *= 2;
            maxHealth *=2;
        }
        return String.format("Health %s, Max Health %s, Speed %s, Mana %s",CusLib.colorText(health, "green"), CusLib.colorText(maxHealth,"blue"), CusLib.colorText(this.baseSpeed,"yellow"), CusLib.colorText(this.baseMana,"cyan"));
    }

}