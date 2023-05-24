public class Ram extends Item {
    public Ram(){
        super("Ram", "A terrifying Move by the Hog using its horns.", 0, 9, 0, -1);
    }

    public void useItem(Player user, Player target){
        //CusLib.queueText(user.getName() + " has rammed into " + target.getName() + "!");
        CusLib.queueText(new UIText(Main.gp,(user.getName() + " has rammed into " + target.getName() + "!"),0,0,10));
        target.Damage(this.getDamage(), this.getDamageType(), user);

    } 
}
