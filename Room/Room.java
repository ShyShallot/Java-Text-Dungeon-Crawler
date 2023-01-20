import java.util.*;
public class Room {
	AIList activeNPCS = new AIList();
	HashMap<String, int[]> typeHealthMap = new HashMap<String, int[]>();
	
    public Room(){
		typeHealthMap.put("Mage", new int[]{25, 35, 60});
		typeHealthMap.put("Knight", new int[]{35, 55, 90}); 
		typeHealthMap.put("Hog",new int[]{30, 50, 100});
		typeHealthMap.put("Goblin", new int[]{15, 25, 45});
		typeHealthMap.put("Spider", new int[]{10, 20, 40});
		typeHealthMap.put("Skeleton", new int[]{20,30,35});
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
		type.getStatProps().setSpeed(CusMath.randomNum(type.baseSpeed()/2, type.baseSpeed()*2));
    	AI npc = new AI(type.getName(), type);
		calculateHealthValues(npc);
		npc.setHealth(npc.getType().baseHealth(),npc.getType().baseHealth());
		npc.mana(npc.getType().baseMana());
		npc.addCoins(CusMath.randomNum(0,(8*Game.difficulty+1)));
    	return npc;
	}

	public void fillNPCInventory(AI npc){
		npc.addItemToInventory(npc.getType().getMainWeapon());
		npc.setArmor(npc.getType().getMainArmor());
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

	public void calculateHealthValues(AI npc){
		String typeName = npc.getType().getName();
		//System.out.println("NPC Type: " + typeName);
		int gameDiff = Game.difficulty;
		//System.out.println("Difficulty: " + gameDiff);
		if(gameDiff == 0){
			int maxHealth = typeHealthMap.get(typeName)[gameDiff];
			//System.out.println("Max Health: " + maxHealth);
			npc.getType().getStatProps().setHealth(CusMath.randomNum(0, maxHealth));
		} else {
			int maxHealth = typeHealthMap.get(typeName)[gameDiff];
			int minHealth = typeHealthMap.get(typeName)[gameDiff-1];
			//System.out.println("Max Health: " + maxHealth + " Min Health: " + minHealth);
			npc.getType().getStatProps().setHealth(CusMath.randomNum(minHealth, maxHealth));
		}
	}

}

