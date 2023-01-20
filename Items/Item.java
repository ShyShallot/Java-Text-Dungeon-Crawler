

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
		}
		this.name = name;
		this.description = description;
		this.cost = cost;
		this.durability = durability;
		this.curDurability = durability;
		this.isArmor = isArmor;
		this.manaCost = 0;
	}

	public Item(String name, String description, int cost, int mana ,boolean isHeal, boolean isArmor){
		this.name = name;
		this.description = description;
		this.cost = cost;
		this.healthItem = isHeal;
		if(isHeal){
			this.isArmor = false;
		}
		this.durability = 1;
		this.curDurability = this.durability;
		this.manaCost = mana;
	}


	public void useItem(Player player){
		return;
	}

	public void useItem(Player player, Player npc){
		return;
	}

	public String resolveDamageType(){ 
		String type = "";
		switch (this.dmgType){ // PLEASE ADD ANY NEW TYPE AT THE END
			case 0:
				type = "Physical";
				break;
			case 1:
				type = "Magic";
				break;
			case 2:
				type = "Fire";
				break;
			case 3:
				type = "Water";
				break;
			case 4:
				type = "Poison";
				break;
			case 5:
				type = "Bleed";
				break;
			case 6:
				type = "Explosion";
				break;
			case 7:
				type = "Ranged";
				break;
		}
		return type;
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

}