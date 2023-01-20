import java.util.*;
public class Room {
	AIList activeNPCS = new AIList();
	
    public Room(){
		fillRoom();
	}

	public void fillRoom(){
		int max = 3 + Game.difficulty;
		int size = CusMath.randomNum(1,max);
		for(int i=0; i<=size;i++){
			AI npc = createRandomNPC();
			fillNPCInventory(npc);
			activeNPCS.addPlayer(npc);
		}
		activeNPCS.sortBySpeed();
	}
	
	public AI createRandomNPC(){ 
    	Type[] types = {new Goblin(), new Hog(), new Skeleton(), new Mage(), new Skeleton(), new Spider(), new Knight()};
    	Type type = types[CusMath.randomNum(0, types.length-1)];
		int health = CusMath.randomNum(type.baseHealth()/2, type.baseHealth()*2);
		int maxHealth = CusMath.randomNum(type.baseMaxHealth()/2,type.baseMaxHealth()*2);
		if(maxHealth < health){
			maxHealth = health;
		}
		type.getStatProps().setHealth(health, maxHealth);
		type.getStatProps().setSpeed(CusMath.randomNum(type.baseSpeed()/2, type.baseSpeed()*2));
    	AI npc = new AI(type.getName(), type);
		npc.setHealth(npc.getType().baseHealth(),npc.getType().baseMaxHealth());
		npc.mana(npc.getType().baseMana());
		uniqueItems(npc);
		npc.addCoins(CusMath.randomNum(0,(5*Game.difficulty+1)));
    	return npc;
	}

	public void fillNPCInventory(AI npc){
		npc.addItemToInventory(npc.getType().getMainWeapon());
		if(npc.getType().getName() == "Hog" || npc.getType().getName() == "Spider"){
			return;
		}
		double heal = healItemChance(npc);
		for(int i=0; i<Game.itemList.size();i++){
			Item itm = Game.itemList.get(i);
			if(itm.isHeal()){
				if(Math.random() <= heal){
					int amt = CusMath.randomNum(1, 5);
					for(int y=0;y<amt;y++){
						npc.addItemToInventory(itm);
					}
				}
			}
		}
	}

	public double healItemChance(AI npc){	
							//DMG  HEAL
		double heal = 0.0;
		switch (npc.getType().getName()){
			case "Goblin":
				heal = 0.35;
				break;
			case "Mage":
				heal = 0.65;
				break;
		}
		return heal;
	}

	public void uniqueItems(AI npc){
		String npcType = npc.getType().getName();
		switch(npcType){
			case "Goblin":
				npc.setArmor(new GoblinArmor());
				break;
			case "Knight":
				npc.setArmor(new Armor());
				break;
		}
		
	}

}

