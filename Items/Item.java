public class Item {
	private String name;
	private String description;
	private int cost;
	private int manaCost;
	private int dmg;
	private int dmgType;
	private int durability;
	private int curDurability;
	private boolean unUsable;
	private boolean isArmor;
	private boolean healthItem;
	public Item(){
		
	}

	public Item(String name, String description, int cost, int mana ,int dmg, int dmgType ,int durability, boolean isArmor, boolean isHeal){
		this.name = name;
		this.description = description;
		this.cost = cost;
		this.dmg = dmg;
		this.durability = durability;
		this.curDurability = durability;
		this.isArmor = isArmor;
		this.unUsable = false;
		this.healthItem = isHeal;
		this.dmgType = dmgType;
		this.manaCost = mana;
	}

	public Item(String name, String description, int cost, int dmg, int dmgType, int durability){
		this.name = name;
		this.description = description;
		this.cost = cost;
		this.dmg = dmg;
		this.dmgType = dmgType;
		this.durability = durability;
		this.curDurability = durability;
		this.isArmor = false;
		this.healthItem = false;
		this.manaCost = 0;
	}

	public Item(String name, String description, int cost, int durability, boolean isArmor){
		if(isArmor){
			this.healthItem = false;
			this.isArmor = true;
		} else {
			this.healthItem = true;
			this.isArmor = false;
		}
		this.name = name;
		this.description = description;
		this.cost = cost;
		this.durability = durability;
		this.curDurability = durability;
		this.manaCost = 0;
	}


	public void useItem(Player player){
		return;
	}

	public void useItem(Player player, Player npc){
		return;
	}

	public String resolveDamageType(){ 
		return Items.getDamageTypeName(this.dmgType);
	}

	public String getName(){
		return this.name;
	}

	public String getDescription(){
		return this.description;
	}
	
	public int getCost(){
		return this.cost;
	}

	public int getManaCost(){
		return this.manaCost;
	}

	public int getDamage(){
		return this.dmg;
	}

	public int getDamageType(){
		return this.dmgType;
	}

	public int getMaxDurability(){
		return this.durability;
	}

	public int durability(){
		return this.curDurability;
	}

	public boolean isArmor(){
		return this.isArmor;
	}

	public boolean isUseable(){
		return this.unUsable;
	}

	public boolean isHeal(){
		return this.healthItem;
	}

	public void setUseState(boolean unUseable){
		this.unUsable = unUseable;
	}

	public void setDurability(int dur){
		this.curDurability += dur;
	}

	public <T> void outputText(T text){
		CusLib.advanceText(text);
	}

}