import java.util.*;
class Player {	
	private String name;
	private int id;
	private int health;
	private int maxHealth;
	private int mana;
	private int lastRoundDefended;
	private int coins;
	private boolean holdingGround = false;
	private Armor Armor;
	private boolean dead;
	private ArrayList<Item> inventory = new ArrayList<Item>();
	private Item inHand;
	private Type type;

	public Player(String name, Type type){
		this.name = name;
		this.type = type;
		this.id = Game.playersCreated;
		Game.playersCreated++;
		this.inHand = this.type.getMainWeapon();
	}

	public Player(String name, int health, Type type){
		this.name = name;
		this.health = health;
		this.maxHealth = health;
		this.type = type;
		this.id = Game.playersCreated;
		Game.playersCreated++;
		this.inHand = this.type.getMainWeapon();
		this.type.getStatProps().setHealth(health, maxHealth);
	}

	public Player(String name, int health, int maxHealth, Type type){
		this.name = name;
		this.health = health;
		this.maxHealth = maxHealth;
		this.type = type;
		this.id = Game.playersCreated;
		Game.playersCreated++;
		this.inHand = this.type.getMainWeapon();
		this.type.getStatProps().setHealth(health, maxHealth);
	}

	public void Defend(int currentRound){
		this.holdingGround = true;
		this.lastRoundDefended = currentRound;
	}
	public void unDefend(){
		this.holdingGround = false;
	}
	public void Heal(int hp){
		if(this.health >= this.maxHealth){
			return;
		}
		if(this.health + hp >= this.maxHealth){
			hp = (this.health + hp) - this.maxHealth;
		}
		this.health += hp;
	}

	public void Damage(int dmg, int dmgType, Player dealer){
		if(Armor != null){
			int amountBlocked = Armor.damageBlocked(dmg, dmgType);
			if(this.getName() == "You"){
				System.out.println(String.format("Your %s blocks %s%s%s of the %s Damage (%s%s%s -> %s%s%s)",Armor.getName(),Game.RED,dmg-amountBlocked,Game.RESET, Items.getDamageTypeName(dmgType), Game.RED,dmg,Game.RESET,Game.RED,amountBlocked,Game.RESET));
			} else 
			if(Armor.getName() != "Skeleton Armor"){
				System.out.println(String.format("%s's %s blocks %s%s%s of the %s Damage (%s%s%s -> %s%s%s)",this.getName(),Armor.getName(),Game.RED,dmg-amountBlocked,Game.RESET, Items.getDamageTypeName(dmgType), Game.RED,dmg,Game.RESET,Game.RED,amountBlocked,Game.RESET));
			}
			dmg = amountBlocked;
		}
		if(this.holdingGround){
			dmg /= 2;
		}
		this.health -= dmg;
		if(this.health < 0 ){
			this.health = 0;
		}
		if(dealer == null){
			if(dmg == 0){
				System.out.println(String.format("%s is immune to the effects of %s and didn't take any damage.", this.getName(),Items.getDamageTypeName(dmgType)));
			}
			System.out.println(String.format("%s felt the effects of %s and took%s %s dmg%s! (%s%s%s --> %s%s%s)",this.getName(),Items.getDamageTypeName(dmgType),Game.RED,dmg,Game.RESET,Game.GREEN,this.health+dmg,Game.RESET,Game.GREEN,this.health,Game.RESET));
		} else {
			System.out.println(String.format("%s dealt %s%s%s damage to %s! (%s%s%s --> %s%s%s)",dealer.getName(),Game.RED,dmg,Game.RESET,this.getName(),Game.GREEN,this.health+dmg,Game.RESET,Game.GREEN,this.health,Game.RESET));
		}
	}

	public void mana(int mana){
		this.mana += mana;
	}
	
	public void addItemToInventory(Item item){
		inventory.add(item);
	}

	public void removeItemFromInventory(int index){
		inventory.remove(index);
	}

	public int getItemInvIndex(Item item){
		int index = 0;
		for(int i=0;i<this.inventory.size();i++){
			if(this.inventory.get(i).getName().equals(item.getName())){
				index = i;
			}
		}
		return index;
	}

	public void setHand(Item item){
		if(item.isHeal() || item.isArmor()){
			return;
		}
		this.inHand = item;
	}

	public void killedPlayer(Player playerKilled){
		if(playerKilled.getCoins() > 0){
			this.coins += playerKilled.coins;
			System.out.println(this.name + " killed " + Game.RED + playerKilled.getName() + Game.RESET + " and took all of their " + Game.YELLOW + playerKilled.getCoins() + Game.RESET + " Coin(s)");
		} else{
			System.out.println(this.name + " killed " + Game.RED + playerKilled.getName() + Game.RESET);
		}
	}

	public void death(Player killer){
		this.dead = true;
	}

	public int healthPercentage(){
		return CusMath.percentage(this.health, this.maxHealth);
	}

	public String getName(){
		return this.name;
	}

	public void setName(String newName){
		this.name = newName;
	}

	public int getID(){
		return this.id;
	}

	public void setID(int id){
		this.id = id;
	}

	public int getHealth(){
		return this.health;
	}

	public void setHealth(int health){
		this.health = health;
	}

	public void setHealth(int health, int maxHealth){
		this.health = health;
		this.maxHealth = maxHealth;
	}

	public int getMaxHealth(){
		return this.maxHealth;
	}

	public void setMaxHealth(int maxHealth){
		this.maxHealth = maxHealth;
	}

	public int getMana(){
		return this.mana;
	}

	public int lastRoundDefended(){
		return this.lastRoundDefended;
	}

	public void setlastRoundDefended(int round){
		this.lastRoundDefended = round;
	}

	public int getCoins(){
		return this.coins;
	}

	public void addCoins(int amt){
		this.coins += amt;
	}

	public boolean isHoldingGround(){
		return this.holdingGround;
	}

	public Armor getArmor(){
		return this.Armor;
	}

	public void setArmor(Armor armor){
		this.Armor = armor;
	}

	public boolean isDead(){
		return this.dead;
	}

	public ArrayList<Item> getInventory(){
		return this.inventory;
	}

	public Item getHand(){
		return this.inHand;
	}

	public Type getType(){
		return this.type;
	}

}
