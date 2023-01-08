import java.util.*;

class Player {	
	String name;
	int id;
	int health = 50;
	int maxHealth = 100;
	int lastRoundDefended;
	int coins;
	boolean holdingGround = false;
	Armor Armor;
	boolean dead;
	ArrayList<Item> inventory = new ArrayList<Item>();
	public Player(String name){
		id = Game.playerList.size();
		Game.playerList.addPlayer(this);
		this.name = name;
	}
	public int Roll(){
		int min = 1;
		int max = 20;
		int randomNum = CusMath.randomNum(min, max);
		return randomNum;
	}
	public void Defend(int currentRound){
		holdingGround = true;
		lastRoundDefended = currentRound;
	}
	public void unDefend(){
		holdingGround = false;
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
	public void Damage(int dmg){
		if(Armor != null){
			dmg -= Armor.block;
		}
		if(holdingGround){
			dmg /= 2;
		}
		this.health -= dmg;
		if(this.health < 0 ){
			this.health = 0;
		}
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
			if(this.inventory.get(i).name == item.name){
				index = i;
			}
		}
		return index;
	}

	

	public void killedPlayer(Player playerKilled){
		this.coins += playerKilled.coins;
	}

	public void death(Player killer){
		this.dead = true;
		killer.killedPlayer(this);
	}

	public String getPlayerType(){
		return this.getClass().getSimpleName();
	}

	public int healthPercentage(){
		return CusMath.percentage(this.health, this.maxHealth);
	}
}
