public class Staff extends Item{
    public Staff(){
        super("Magic Staff", "Deals Magic Damage",9,5,12,1,-1,false,false);
    }   

    public Staff(String name, String description, int dmg,int cost){
        super(name,description,cost,dmg,1,-1);
    }

    public void useItem(Player player, Player target, boolean initCast){
        player.getSpell().castSpell(player, target, initCast);
    }

    public void useItem(Player player, Player target){
        player.getSpell().castSpell(player, target, true);
    }

}
