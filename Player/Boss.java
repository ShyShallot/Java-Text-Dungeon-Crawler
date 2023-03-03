public class Boss extends AI{
    private Item secondaryAttack;
    private int currAttackIndex = 0;
    private BossType bossType;

    public Boss(String name, BossType type, int health, int maxHealth){
        super(name, type);
        this.setHealth(health, maxHealth);
        this.bossType = type;
        this.secondaryAttack = this.bossType.getSecondaryAttack();
    }

    public Boss(String name, BossType type, int health){
        super(name, type);
        this.setHealth(health);
        this.bossType = type;
        this.secondaryAttack = this.bossType.getSecondaryAttack();
    }

    public Item getSecondaryAttack(){
        return this.secondaryAttack;
    }

    public void setAttackPattern(int[] arr){
        this.bossType.setAttackPattern(arr);
    }

    public int[] getAttackPattern(){
        return this.bossType.getAttackPattern();
    }

    public boolean shouldUseSecondary(){
        if(this.getAttackPattern()[this.currAttackIndex] == 1){
            CusLib.DebugOutputLn("Boss will use Second Attack");
            this.usedSecondary();
            return true;
        }
        return false;
    }

    public void usedSecondary(){
        this.currAttackIndex++;
        CusLib.DebugOutputLn(this.currAttackIndex);
        if(this.currAttackIndex > this.getAttackPattern().length){
            this.currAttackIndex = 0;
        }
        System.out.println("increased attack index");
    }

    public void prepareAttack(){
        this.shouldDefend(Game.mainPlayer.isHoldingGround());
        if(this.isHoldingGround()){
            return;
        }
        if(shouldUseSecondary()){
            this.setHand(this.secondaryAttack);
            CusLib.DebugOutputLn(this.getHand().getName());
        } else {
            this.setHand(this.getType().getMainWeapon());
            CusLib.DebugOutputLn(this.getHand().getName());
        }
    }
}
