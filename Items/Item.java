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
	public static TypeArray<Item> ItemList = new TypeArray<>();
	public static TypeArray<Armor> ArmorList = new TypeArray<>();
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
		addItemtoList(this);
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
		addItemtoList(this);
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
		addItemtoList(this);
	}

	public void addItemtoList(Item item){
		if(item.isArmor()){
			return;
		}
		ItemList.add(item);
	}

	public void addItemtoList(Armor armor){
		if(!armor.isArmor()){
			return;
		}
		ArmorList.add(armor);
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
		CusLib.queueText(text);
	}

	public static Item getItemFromName(String itemName){
		CusLib.DebugOutputLn("Finding weapon: " + itemName);
		for(int i=0;i<ItemList.size();i++){
			Item item = ItemList.get(i);
			CusLib.DebugOutputLn("Weapon: " + item.getName());
			if(item.getName().toLowerCase().equals(itemName.toLowerCase())){
				CusLib.DebugOutputLn("Found Item: " + item.getName());
				return item;
			}
		}
		return null;
	}

	public static Item getItemFromName(String itemName, Player player){
		CusLib.DebugOutputLn("Finding weapon: " + itemName);
		for(int i=0;i<player.getInventory().size();i++){
			Item item = player.getInventory().get(i);
			if(item.getName().toLowerCase().equals(itemName.toLowerCase())){
				CusLib.DebugOutputLn("Found Item: " + item.getName());
				return item;
			}
		}
		return null;
	}

	public static Armor getArmorFromName(String armorName){
		CusLib.DebugOutputLn("Finding Armor: " + armorName);
		for(int i=0;i<ArmorList.size();i++){
			Armor armor = ArmorList.get(i);
			if(armor.getName().toLowerCase().equals(armorName.toLowerCase())){
				CusLib.DebugOutputLn("Found Item: " + armor.getName());
				return armor;
			}
		}
		return null;
	}

	public boolean equals(Object object){
		Item item = (Item) object;
		CusLib.DebugOutputLn("Checking if " + item.getName() + " is equal to: " + this.name);
		if(item.getName().equals(this.name)){
			return true;
		}
		return false;
	}

	public String toString(){
		return String.format("Name: %s, Description: %s, Cost: %s, Mana Cost: %s, Damage: %s, Damage Type: %s, Durability: %s, Current Durability: %s, Unusable: %s, Is Armor: %s, Is Healing: %s", this.name, this.description, this.cost, this.manaCost, this.dmg, this.dmgType, this.durability, this.curDurability, this.unUsable, this.isArmor, this.healthItem);
	}

}