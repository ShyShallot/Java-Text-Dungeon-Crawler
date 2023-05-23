import java.awt.Color;

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
		String message = "";
		if(player.getName() == "You"){
			//CusLib.queueText(player.getName() + " has used your " + this.getName() + " and Gained " + this.heal + " HP");
			message = player.getName() + " has used your " + this.getName() + " and Gained " + "%s" + this.heal + " HP" + "%s";
		} else {
			//CusLib.queueText(player.getName() + " has used their " + this.getName() + " and Gained " + this.heal + " HP");
			message = player.getName() + " has used their " + this.getName() + " and Gained " + "%s" + this.heal + " HP" + "%s";
		}
		CusLib.queueText(new UIText(Main.gp,message,0,0,10,Color.white,Color.green));
		player.removeItemFromInventory(player.getItemInvIndex(this));
		return;
	}

	public int getHealAmt(){
		return this.heal;
	}
}


