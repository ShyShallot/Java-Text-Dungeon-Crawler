class Vial extends Item {
	public Vial(){
		name = "Health Vial";
		dmg = 0;
		description = "Heals you for " + heal + " hp";
		heal = 8;
		cost = 6;
		durability = 1;
		curDurability = durability;
		isArmor = false;
		unUsable = false;
		healthItem = true;
	}

	public Vial(int heal, String name, int cost){
		this.heal = heal;
		this.name = name;
		this.cost = cost;
	}


	public void useItem(Player player){
		//if(unUsable){
		//	return;
		//}
		//if(curDurability <= 0){
		//	unUsable = true;
		//	return;
		//}
		player.health += this.heal;
		System.out.println(player.name + " has used their " + name + " and Gained " + this.heal + " HP");
		return;
	}
}


