public class Bite extends Item {
    private int poisonDamage;
    private int poisonLasts;
    private double poisonChance;
    private String damageString;
    public Bite(){
        super("Bite", "A Poisonous Bite", 0, 6, 0, -1);
        this.poisonDamage = 4;
        this.poisonLasts = 2;
        this.poisonChance = 0.3;
        this.damageString = " has bitten ";
    }

    public Bite(String name, String description, int damage, int damageType, int poisonDamage, int poisonLasts, double poisonChance ,String damageString){
        super(name, description, 0, damage, damageType, -1);
        this.poisonDamage = poisonDamage;
        this.poisonLasts = poisonLasts;
        this.poisonChance = poisonChance;
        this.damageString = damageString;
    }

    public void useItem(Player user, Player target){
        System.out.println(user.getName() + damageString + target.getName() + "!");
        target.Damage(this.getDamage(), this.getDamageType(),user);
        if(Math.random() < poisonChance){
            Game.DOTList.addPlayer(target, this.poisonDamage, 4, this.poisonLasts, Game.currentSubRound);
        }
        
    } 

    public int poisonDamage(){
        return this.poisonDamage;
    }

    public int poisonLasts(){
        return this.poisonLasts;
    }

    public String damageString(){
        return this.damageString;
    }

    public void setDamageString(String dmgString){
        this.damageString = dmgString;
    }
    
}
