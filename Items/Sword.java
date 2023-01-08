class Sword extends Item {

	public Sword(int foundDurability){
		name = "Sword";
		dmg = 4;
		description = "Deals " + dmg + " dmg points";
		cost = 5;
		durability = 3;
		curDurability = foundDurability;
		isArmor = false;
		unUsable = false;
	}

	public Sword(){
		name = "Sword";
		dmg = 4;
		description = "Deals " + dmg + " dmg points";
		cost = 5;
		durability = 3;
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
		int durUsed = 1;
		this.curDurability -= durUsed;
		if(player.getPlayerType() == "AI"){
			Game.aiRoll = this.dmg;
		} else {
			Game.mainRoll = this.dmg;
		}
		System.out.println(player.name + " has used their " + this.name);
	}
}