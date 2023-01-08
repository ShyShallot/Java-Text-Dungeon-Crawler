class Vial extends Item {
	int heal;
	public Vial(){
		heal = 8;
		name = "Health Vial";
		dmg = 0;
		description = "Heals you for " + heal + " hp";
		cost = 6;
		durability = 1;
		curDurability = durability;
		isArmor = false;
		unUsable = false;
	}

	public void useItem(Player player){
		if(unUsable){
			return;
		}
		if(curDurability <= 0){
			unUsable = true;
			return;
		}
		player.health += this.heal;
		System.out.println(player.name + " has used their " + this.name);
		return;
	}
}