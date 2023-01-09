class Sword extends Item {

	public Sword(){
		super("Sword", "Deals 12 dmg points", 7, 12, 3, false, false);
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
		System.out.println(npc.name + " has used their " + this.name);
	}
}