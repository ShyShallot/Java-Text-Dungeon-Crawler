public class Stomp extends Item{
    public Stomp(){
        super("Stomp", "A Big Stomp", 0, 14, 0, -1);
    }

    public void useItem(Player user, Player target){
        CusLib.queueText(new UIText(Main.gp,String.format("%s created an short groundquake by stomping their feet!",user.getName()),0,0,10));
        target.Damage(this.getDamage(), this.getDamageType(), user);
    }
}
