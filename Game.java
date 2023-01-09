import java.util.*;

class Game {
	// Color Strings
	public static final String RESET = "\u001B[0m";
	public static final String RED = "\u001B[31m";
	public static final String GREEN = "\u001B[32m";
	public static final String YELLOW = "\u001B[33m";
	// Player
	public static AIList npcList = new AIList();
	public static Player mainPlayer;
	// Round Data 
	public static int currentRound = 1;
	int lastRoundHealed;
	public static int lastMerchantRoom;
	// Roll
	public static int mainRoll;
	public static int aiRoll;
	// AI
	int roundAIDefended;
	double randomHealChance = 0.85;
	public static int difficulty = 0; // easy = 0, medium = 1, hard = 2
	double aiHealBias = 0.0;
	public static int aiRollBias = 0;
	// Main
	boolean over = false;
	public static Scanner input;
	public static ArrayList<Item> itemList = new ArrayList<Item>();
	// Input
	public void SetupInput(Scanner in) {
		input = in;
	}
	// Room
	Room currentRoom;
	// Strings
	RandomStrings randomString = new RandomStrings();

	// START OF MAIN GAME FUNCTIONS
	public void Start() {
		System.out.println("Welcome to Defend n Roll, A simple text fight game, where you can either defend for one turn and reduce dmg by 50% or roll. Damage is determined by the difference between the 2 rolls, who ever has the lowest roll, gets the damage.");
		System.out.println();
		
		setDifficulty();
		System.out.println();
		addItemsToList();
		mainPlayer = new Player("You");
		while (!over) {
			if(shouldBeMerchantRoom()){
				MerchantRoom merchantRoom = new MerchantRoom();
				merchantRoom.itemShop(false);
				currentRound++;
			} else {
				currentRoom = new Room();
				currentRoom.activeNPCS.renameDupes();
				TurnCombat();
			}
		}
	}

	public void TestWeights(){
		AI testNPC = new AI("Test");
		testNPC.health = 56;
		testNPC.maxHealth = 75;
		aiDecideItem(testNPC);
	}

	public void DecideDamage(AI npc) {
		int diff;
		if(!SpecialDamage()){
			if(!mainPlayer.holdingGround){
				System.out.println("You Rolled a " + mainRoll + ", The " + npc.name + " Rolled a " + aiRoll);
			} else {
				System.out.println("You Defended" + ", The " + npc.name + " Rolled a " + aiRoll);
			}
		}
		if (mainRoll > aiRoll && !mainPlayer.holdingGround) {
			diff = Math.abs(mainRoll - aiRoll);
			npc.Damage(diff);
			System.out.print(randomString.generateRollString(mainPlayer.name, "The " + npc.name, diff));
			if (npc.holdingGround) {
				System.out.print(", however they defended and it was reduced to: " + RED + (diff / 2) + RESET + " dmg");
			}
		} else if(mainRoll == aiRoll){
			System.out.println("You both rolled the same number! No Damage was dealt");
		} else {
			diff = Math.abs(aiRoll - mainRoll);
			mainPlayer.Damage(diff);
			System.out.print(randomString.generateRollString(("The " + npc.name), mainPlayer.name, diff));
			if (mainPlayer.holdingGround) {
				System.out.print(", however you defended and it was reduced to: " + RED + (diff / 2) + RESET + " dmg");
			}
		}
		System.out.println();
	}

	public void Action(Player enemy) {
		System.out.println();
		System.out.print("Current Round: " + currentRound + ", Your Current Health: " + GREEN + mainPlayer.health
				+ RESET + " The " + RED + enemy.name + "'s" + RESET + " Health: " + GREEN + enemy.health + RESET + ", Defend or roll?");
		if(mainPlayer.inventory.size() > 0){
			System.out.print(" Or Use an Item?");
		}
		System.out.println();
		String action = input.nextLine();
		if (action.toLowerCase().equals("roll")) {
			mainRoll = mainPlayer.Roll();
		}
		if (action.toLowerCase().equals("defend")) {
			mainPlayer.Defend(currentRound);
			mainRoll = 5;
		}
		if(action.toLowerCase().equals("use an item") || action.toLowerCase().equals("item") && mainPlayer.inventory.size() > 0){
			pickItem();
			System.out.println();
			return;
		}
		if (!action.toLowerCase().equals("roll") && !action.toLowerCase().equals("defend")) {
			System.out.println("Unknown Command");
			this.Action(enemy);
		}
		System.out.println();
	}

	public void healRandomPlayer(Player player) {
		if (Math.random() > randomHealChance && (currentRound - lastRoundHealed) >= 2) { // 10% chance to randomly heal
			int health = randomInt(5, 10);
			double healChance = Math.random() - aiHealBias;
			if (healChance > 0.5) { // heal player
				System.out.println("You were randomly healed " + GREEN + health + RESET + " hp");
				mainPlayer.Heal(health);
			} else {
				System.out.println(player.name + " was randomly healed and gained " + GREEN + health + RESET + " hp");
				player.Heal(health);
			}
			lastRoundHealed = currentRound;
		}
	}

	public int randomInt(int min, int max) {
		return (int) Math.floor(Math.random() * (max - min + 1) + min);
	}

	public boolean SpecialDamage(){
		if(mainRoll == 20){
			aiRoll = 0;
			System.out.println("You Rolled a 20 and dealt massive amounts of Damage to the opponent!");
			return true;
		}
		if(aiRoll == 20){
			mainRoll = 0;
			System.out.println("The Opponent Rolled a 20 and dealt massive amounts of Damage to you!");
			return true;
		}
		return false;
	}

	public void setDifficulty(){
		System.out.println("Start by typing to select a difficulty: Easy, Medium, Hard");
		String selectDiff = input.nextLine();
		selectDiff = selectDiff.toLowerCase();
		if(selectDiff.equals("medium")){
			System.out.println("You Selected: Medium");
			difficulty = 1;
			aiHealBias = 0.1;
			aiRollBias = 3;
		} else if(selectDiff.equals("hard")){
			System.out.println("You Selected: Hard");
			difficulty = 2;
			aiHealBias = 0.2;
			aiRollBias = 5;
			randomHealChance = 0.9;
		} else {
			System.out.println("You Selected: Easy");
			randomHealChance = 0.75;
		}
	}

	public void addItemsToList(){
		itemList.add(new Sword());
		itemList.add(new Vial());
		itemList.add(new LargeVial());
		itemList.add(new Armor());
	}


	public void pickItem(){
		if(mainPlayer.inventory.size() == 0){
			return;
		}
		System.out.println("Which item would you like to use?");
		String itemList = "";
		for(int i=0;i<mainPlayer.inventory.size();i++){
			Item itm = mainPlayer.inventory.get(i);
			if(itm.unUsable || itm.isArmor || (itm.isArmor && itm.isArmor)){
				continue;
			}
			itemList += itm.name + ": Description: " + itm.description + ". \n";
		}
		System.out.println(itemList); 
		String itemPicked = input.nextLine();
		Item item = Items.getItemFromName(itemPicked);
		if(item == null){
			System.out.println("You do not have that item.");
			pickItem();
		}
		if(Items.doesPlayerHaveItem(mainPlayer, item)){
			item.useItem(mainPlayer);
		}
	}

	public void aiDecideItem(AI npc){
		if(npc.inventory.size() == 0){
			return;
		}
		double[] weights = npc.itemDecideWeightTable();
		//System.out.println("NPC Health Percentage: "+ npc.healthPercentage() + ", Damage Weight: " + weights[0] + ", Heal Weight: " + weights[1]);
		double damageItemWeight = weights[0]; // from 0 to 1 (For Percentage Multiply by 100)
		double healthItemWeight = weights[1]; // from 0 to 1
		if(npc.holdingGround){
			return;
		}
		int maxDamage = 0;
		int maxHeal = 0;
		Item itemToUse = new Item();
		boolean shouldUseDamage = false;
		if(damageItemWeight >= (healthItemWeight + CusMath.random(0.3))){
			shouldUseDamage = true;
		}
		for(int i=0;i<npc.inventory.size();i++){
			Item itm = npc.inventory.get(i);
			if(itm.unUsable || itm.isArmor){
				continue;
			}
			if(shouldUseDamage && (!itm.healthItem)){
				if(itm.dmg > maxDamage){
					itemToUse = itm;
					maxDamage = itm.dmg;
				}
			}
			if(!shouldUseDamage && (itm.healthItem)){
				if(itm.heal >= maxHeal){
					itemToUse = itm;
					maxHeal = itm.heal;
				}
			}
		}
		itemToUse.useItem(npc);
	}


	public boolean shouldBeMerchantRoom(){
		if(Game.lastMerchantRoom >= CusMath.randomNum(3, 6)){
			if(mainPlayer.inventory.size() == 0 || (mainPlayer.inventory.size() < itemList.size())){
				if(Math.random() > 0.6){ // 40% chance for it to be a merchant room afterwards
					return true;
				}
			}
		}
		return false;
	}

	public void TurnCombat(){
		String opponents = "You enter a new Room and find ";
		for(int i=0;i<currentRoom.activeNPCS.size();i++){
			if(i < currentRoom.activeNPCS.size()-1){
				opponents += "A " + RED + currentRoom.activeNPCS.npcs.get(i).name + RESET + ", ";
			} else {
				opponents += "and A " + RED + currentRoom.activeNPCS.npcs.get(i).name + RESET;
			}
		}
		System.out.println(opponents);
		while((!mainPlayer.dead) && currentRoom.activeNPCS.size() > 0){ 
			for(int i=0;i<currentRoom.activeNPCS.size();i++){
				System.out.println();
				printOpponents();
				AI currentNPC = currentRoom.activeNPCS.npcs.get(i);
				System.out.println("Current Opponent: " + RED + currentNPC.name + RESET);
				currentNPC.AIAction();
				aiDecideItem(currentNPC);
				Action(currentNPC);
				DecideDamage(currentNPC);
				if(currentNPC.health <= 0){
					currentNPC.death(mainPlayer);
				}
				if(mainPlayer.health <= 0){
					mainPlayer.death(currentNPC);
					over = true;
					System.out.println("You died and made it through " + currentRound + " Rooms, Congrats and Better Luck next time!");
					currentRound--;
					break;
				}
				currentRoom.activeNPCS.GarbageCleanup();
				healRandomPlayer(currentNPC);
			}	
		}
		currentRound++;
	}

	public void printOpponents(){
		String totalRoomHealth = "";
			for(int i=0;i<currentRoom.activeNPCS.size();i++){
				AI curNpc = currentRoom.activeNPCS.get(i);
				if(i<currentRoom.activeNPCS.size()-1){
					totalRoomHealth += RED + curNpc.name + "'s" + RESET + " Health: " + GREEN + curNpc.health + RESET + " HP, ";
				} else {
					totalRoomHealth += RED + curNpc.name + "'s" + RESET + " Health: " + GREEN + curNpc.health + RESET + " HP.";
				}
				
			}
			System.out.println(totalRoomHealth);
	}
}