public class Vial extends Item {
	private int heal;
	public Vial(){
		super("Health Vial", "Heals you for 8 hp",5,4,false);
		this.heal = 8;
	}

	public Vial(int heal, String name, int cost, int manaCost){
		super(name, "Heals you for " + heal + " hp",cost,manaCost,false);
		this.heal = heal;
	}


	public void useItem(Player player, Player target){
		//if(unUsable){
		//	return;
		//}
		//if(curDurability <= 0){
		//	unUsable = true;
		//	return;
		//}
		player.Heal(this.heal);
		System.out.println(player.getName() + " has used their " + this.getName() + " and Gained " + this.heal + " HP");
		player.removeItemFromInventory(player.getItemInvIndex(this));
		return;
	}

	public int getHealAmt(){
		return this.heal;
	}
}


