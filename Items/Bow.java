public class Bow extends Item{
    public Bow(){
        super("Wooden Bow", "A somewhat weak bow", 8, 12, 7, 5);
    }

    public void useItem(Player player, Player target){
        if(this.durability() == 0){
            this.setUseState(true);
            return;
        }
        String message = String.format("%s has shot an arrow at %s",player.getName(),target.getName());
        target.Damage(this.getDamage(), this.getDamageType(),player);
        CusLib.queueText(new UIText(Main.gp,message,0,0,10));
    }
}
