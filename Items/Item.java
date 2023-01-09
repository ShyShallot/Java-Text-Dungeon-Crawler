class Item {
	String name;
	String description;
	int cost;
	int dmg;
	int durability;
	int curDurability;
	boolean unUsable;
	boolean isArmor;
	boolean healthItem;
	public Item(){
		
	}

	public Item(String name, String description, int cost, int dmg, int durability, boolean isArmor, boolean isHeal){
		this.name = name;
		this.description = description;
		this.cost = cost;
		this.dmg = dmg;
		this.durability = durability;
		this.curDurability = durability;
		this.isArmor = isArmor;
		this.unUsable = false;
		this.healthItem = isHeal;
	}

	public void useItem(Player player){
		return;
	}
}