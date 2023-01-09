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
		healthItem = false;
		heal = 0;
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
		healthItem = false;
		heal = 0;
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
		Game.mainRoll = this.dmg;
		System.out.println(player.name + " has used their " + this.name);
	}

	public void useItem(AI npc){
		if(unUsable){
			return;
		}
		if(curDurability <= 0){
			unUsable = true;
			return;
		}
		int durUsed = 1;
		this.curDurability -= durUsed;
		Game.aiRoll = this.dmg;
		System.out.println(npc.name + " has used their " + this.name);
	}
}