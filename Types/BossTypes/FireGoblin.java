public class FireGoblin extends BossType{
    public FireGoblin(int health, int maxHealth, int speed, int mana){
        super("Fire Guard Goblin",health,maxHealth,speed,mana);
        this.setMainWeapon(new Club());
        this.setMainArmor(new GoblinArmor());
        this.setSecondaryAttack(new Stomp());
        this.setAttackPattern(new int[]{0,0,1,0,1,0,1});
    }

    public FireGoblin(int health, int speed, int mana){
        super("Fire Guard Goblin",health,speed,mana);
        this.setMainWeapon(new Club());
        this.setMainArmor(new GoblinArmor());
        this.setSecondaryAttack(new Stomp());
        this.setAttackPattern(new int[]{0,0,1,0,1,0,1});
    }

    public FireGoblin(){
        super("Fire Guard Goblin",65,9,30);
        this.setMainWeapon(new Club());
        this.setMainArmor(new GoblinArmor());
        this.setSecondaryAttack(new Stomp());
        this.setAttackPattern(new int[]{0,0,1,0,1,0,1});
    }
}
