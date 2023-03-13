public class Stomp extends Item{
    public Stomp(){
        super("Stomp", "A Big Stomp", 0, 14, 0, -1);
    }

    public void useItem(Player user, Player target){
        CusLib.queueText(String.format("%s created an short groundquake by stomping their feet!",user.getName()));
        target.Damage(this.getDamage(), this.getDamageType(), user);
    }
}
