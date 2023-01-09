class Vial extends Item {
	boolean healthTime;
	int heal;
	public Vial(){
		super("Health Vial", "Heals you for 8 hp",6,0,1,false,true);
		this.healthItem = true;
		this.heal = 8;
	}

	public Vial(int heal, String name, int cost){
		super(name, "Heals you for " + heal + " hp", cost,0,1,false,true);
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


