import java.util.*;

class Player {	
	String name;
	int id;
	int health = 100;
	int maxHealth = 100;
	int lastRoundDefended;
	int coins;
	boolean holdingGround = false;
	Armor Armor;
	boolean dead;
	ArrayList<Item> inventory = new ArrayList<Item>();
	public Player(String name){
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
			if(Armor.durability <= 0){
				Armor = null;
			}
			dmg -= Armor.block;
			Armor.durability--;
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
		if(playerKilled.coins <= 0){
			return;
		}
		this.coins += playerKilled.coins;
		System.out.println(this.name + " killed " + playerKilled.name + " and took all of their " + playerKilled.coins + " Coin(s)");
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
