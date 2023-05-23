
public class Sword extends Item {

	public Sword(){
		super("Sword", "Deals 12 dmg points", 7,0, 12,0,3, false, false);
	}

	public Sword(String name, String description, int dmg, int cost, int durability, int dmgType){
		super(name,description,cost,0,dmg,dmgType,durability,false,false);
	}

	public void useItem(Player player, Player target){
		String message = "";
		if(player.getName().equals("You")){
			message = (player.getName() + " used your " + this.getName());
		} else {
			message = (player.getName() + " has used their " + this.getName());
		}
		CusLib.queueText(new UIText(Main.gp,message,0,0,10));
		damagePlayer(player,target);
	}

	public void damagePlayer(Player user, Player target){
        int damage = CusLib.randomNum(this.getDamage()-5, this.getDamage());
        target.Damage(damage, this.getDamageType(),user);
    }
}