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
		int size = CusLib.randomNum(1,max);
		for(int i=0; i<=size;i++){
			AI npc = createRandomNPC();
			fillNPCInventory(npc);
			activeNPCS.addPlayer(npc);
		}
		activeNPCS.sortBySpeed();
	}
	
	public AI createRandomNPC(){ 
    	Type type = Type.types.get(CusLib.randomNum(0, Type.types.size()-1));
		type.getStatProps().setSpeed(CusLib.randomNum(type.baseSpeed()/2, type.baseSpeed()*2));
    	AI npc = new AI(type.getName(), type);
		calculateHealthValues(npc);
		npc.setHealth(npc.getType().baseHealth(),npc.getType().baseHealth());
		npc.addMana(npc.getType().baseMana());
		int coins = CusLib.randomNum(0,(8*Game.difficulty+1));
		//System.out.println("Coins: " + coins);
		npc.addCoins(coins);
		//System.out.println("Coins: " + npc.getCoins());
    	return npc;
	}

	public void fillNPCInventory(AI npc){
		npc.addItemToInventory(npc.getType().getMainWeapon());
		npc.setArmor(npc.getType().getMainArmor());
		if(npc.getType().getName().equals("Hog") || npc.getType().getName().equals("Spider")){
			return;
		}
		double heal = healItemChance(npc);
		for(int i=0; i<Item.ItemList.size();i++){
			Item itm = Item.ItemList.get(i);
			if(itm.isHeal()){
				if(Math.random() <= heal){
					int amt = CusLib.randomNum(1, 5);
					for(int y=0;y<amt;y++){
						npc.addItemToInventory(itm);
					}
				}
			}
		}
	}

	public double healItemChance(AI npc){	
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
			npc.getType().getStatProps().setHealth(CusLib.randomNum(5, maxHealth));
		} else {
			int maxHealth = typeHealthMap.get(typeName)[gameDiff];
			int minHealth = typeHealthMap.get(typeName)[gameDiff-1];
			//System.out.println("Max Health: " + maxHealth + " Min Health: " + minHealth);
			npc.getType().getStatProps().setHealth(CusLib.randomNum(minHealth, maxHealth));
		}
	}

	public void treasureChest(){
		System.out.println("You found a treasure chest, would you like to open it? y/n");
		String open = Game.input.nextLine();
		if(!open.equals("y") || !open.equals("yes")){
			System.out.println("You don't open the chest and you continue on your way");
			return;
		}
		int diff = Game.difficulty;
		double mimicChance = 0.35;
		if(diff == 1){
			mimicChance = .4;
		} else if(diff == 2){
			mimicChance = .55;
		}
		if(Math.random() > mimicChance){
			int dmg = 8;
			System.out.println("You open the chest and it turns out to be a " + CusLib.colorText("Mimic Chest", "red") + "! it deals " + CusLib.colorText(dmg, "red") + " to you!");
			Game.mainPlayer.Damage(dmg);
			return;
		}
		ArrayList<Item> items = new ArrayList<>();
		int maxSize = CusLib.randomNum(0, 4-diff);
		while(items.size() < maxSize){
			for(Item item : Item.ItemList){
				if(items.size() >= maxSize){
					break;
				}
				if(Game.mainPlayer.getType().isItemIllegal(item)){
					continue;
				}
				if(item.isArmor()){
					continue;
				}
				if(Math.random() > 0.4){
					items.add(item);
				}
			}
		}

		String itemsFound = "You found: ";
		for(Item item: items){
			itemsFound += ("a " + item.getName() +", ");
			Game.mainPlayer.addItemToInventory(item);
		}
		itemsFound.substring(0,(itemsFound.length()-2));
		itemsFound += ".";
		System.out.println(itemsFound);
	}

}

