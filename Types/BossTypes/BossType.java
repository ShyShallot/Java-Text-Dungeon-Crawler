public class BossType extends Type{
    private Item secondaryAttack;
    private int[] attackPattern;
    

    public BossType(String name, int health, int maxHealth, int speed, int mana, Item mainWeapon, Item secondaryAttack){
        super(name, health, maxHealth, speed, mana,mainWeapon);
        this.secondaryAttack = secondaryAttack;
    }

    public BossType(String name, int health, int maxHealth, int speed, int mana){
        super(name, health, maxHealth, speed, mana);
    }
    

    public BossType(String name, int health, int speed, int mana, Item mainWeapon, Item secondaryAttack){
        super(name, health, speed, mana, mainWeapon);
        this.secondaryAttack = secondaryAttack; 
    }

    public BossType(String name, int health, int speed, int mana){
        super(name, health, speed, mana);
    }

    public Item getSecondaryAttack(){
        return this.secondaryAttack;
    }

    public void setSecondaryAttack(Item secondary){
        this.secondaryAttack = secondary;
    }

    public int[] getAttackPattern(){
        return this.attackPattern;
    }

    public void setAttackPattern(int[] newPattern){
        this.attackPattern = newPattern;
    }

    public void modifyAttackPattern(int index, int newAttack){
        if(index > this.attackPattern.length-1){
            return;
        }
        if(newAttack > 1 || newAttack < 0){
            return;
        }
        this.attackPattern[index] = newAttack;
    }
}
