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
	}
	
	public AI createRandomNPC(){ 
    	String[] names = {"Goblin","Skeleton","Hog","Spider","Mage"};
    	String name = names[CusMath.randomNum(0, names.length-1)];
    	AI npc = new AI(name);
    	npc.health = CusMath.randomNum(10, 50) * (Game.currentRound*(Game.difficulty/10)+1);
		npc.maxHealth = npc.health;
		npc.coins = CusMath.randomNum(0,(5*Game.difficulty+1));
    	if(npc.health > 500){
        	npc.health = 500;
    	}
    	return npc;
	}

	public void fillNPCInventory(AI npc){
		if(npc.name == "Hog" || npc.name == "Spider"){
			return;
		}
		double[] weights = itemClassWeight(npc);
		double dmg = weights[0];
		double heal = weights[1];
		for(int i=0; i<Game.itemList.size();i++){
			Item itm = Game.itemList.get(i);
			if(itm.healthItem){
				if(Math.random() <= heal){
					npc.inventory.add(itm);
				}
			} else {
				if(Math.random() <= dmg){
					npc.inventory.add(itm);
				}
			}
		}
	}

	public double[] itemClassWeight(AI npc){	
							//DMG  HEAL
		double[] itmWeight = {0.0,0.0};
		switch (npc.name){
			case "Goblin":
				itmWeight[0] = 0.8;
				itmWeight[1] = 0.2;
				break;
			case "Skeleton":
				itmWeight[0] = 1.0;
				itmWeight[1] = 0.0;
				break;
			case "Mage":
				itmWeight[0] = 0.45;
				itmWeight[1] = 0.55;
				break;
		}
		return itmWeight;
	}

}

