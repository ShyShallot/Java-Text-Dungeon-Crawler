public class Club extends Sword{
    double missChance = 0.85;

    public Club(){
        super("Heavy Club", "A slow yet powerful weapon, however if you miss it will hit you instead", 12, 15, -1, 0);
    }

    public void useItem(Player user, Player Target){
        if(Math.random() > missChance){
            if(user.getName() == "You"){
                CusLib.queueText(String.format("%s swung your %s and missed and dealt %s damage to yourself",user.getName(), this.getName(),CusLib.colorText(this.getDamage(), "red")));
            } else {
                CusLib.queueText(String.format("%s swung their %s and missed and dealt %s damage to themselves",user.getName(), this.getName(),CusLib.colorText(this.getDamage(), "red")));
            }
            user.Damage(this.getDamage(), 0, user);
        } else {
            CusLib.queueText(String.format("%s swung and missed!", user.getName()));
        }
        
    }
    
}
