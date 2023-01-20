public class Staff extends Item{
    public Staff(){
        super("Magic Staff", "Deals Magic Damage",9,5,12,1,-1,false,false);
    }   

    public Staff(String name, String description, int dmg,int cost){
        super(name,description,cost,dmg,1,-1);
    }

    public void useItem(Player player, Player target){
        System.out.println(player.getName() + " Casted a Physical Spell on " + target.getName());
        player.mana(-this.getManaCost());
        damagePlayer(player,target);
    }

    public void damagePlayer(Player user, Player target){
        int damage = CusMath.randomNum(this.getDamage()-5, this.getDamage());
        target.Damage(damage, this.getDamageType(),user);
    }
}
