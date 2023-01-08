import java.util.*;

class Game {
	// Color Strings
	public static final String RESET = "\u001B[0m";
	public static final String RED = "\u001B[31m";
	public static final String GREEN = "\u001B[32m";
	public static final String YELLOW = "\u001B[33m";
	// Player
	public static PlayerList playerList = new PlayerList();
	public static Player mainPlayer;
	// Round Data 
	public static int currentRound = 1;
	int lastRoundHealed;
	public static int lastMerchanRoom;
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
			if ((currentRound > mainPlayer.lastRoundDefended) && mainPlayer.holdingGround) {
				mainPlayer.unDefend();
			}
			if ((currentRound > aiPlayer.lastRoundDefended) && aiPlayer.holdingGround) {
				aiPlayer.unDefend();
			}
			if (!aiPlayer.shouldAiDefend(mainPlayer.holdingGround, currentRound)) {
				aiRoll = aiPlayer.aiRoll(aiRollBias);
			} else {
				aiRoll = 5;
			}
			Action();
			AIuseItem(aiPlayer);
			useItem(mainPlayer);
			DecideDamage();
			if (mainPlayer.health <= 0) {
				over = true;
				System.out.println("You Lost after " + currentRound++ + " rounds, better luck next time");
				return;
			}
			if (aiPlayer.health <= 0) {
				over = true;
				System.out.println("You Beat the opponent after " + currentRound++ + " rounds. Congrats!");
				return;
			}
			healRandomPlayer();
			giveCoins();
			currentRound++;
		}
	}

	public void TestWeights(){
		AI testNPC = new AI("Test");
		testNPC.health = 56;
		testNPC.maxHealth = 75;
		aiDecideItem(testNPC);
	}

	public void DecideDamage() {
		int diff;
		if(!SpecialDamage()){
			if(!mainPlayer.holdingGround){
				System.out.println("You Rolled a " + mainRoll + ", The Ai Rolled a " + aiRoll);
			} else {
				System.out.println("You Defended" + ", The Ai Rolled a " + aiRoll);
			}
		}
		if (mainRoll > aiRoll && !mainPlayer.holdingGround) {
			diff = Math.abs(mainRoll - aiRoll);
			aiPlayer.Damage(diff);
			System.out.print(randomString.generateRollString("You", "The Opponent", diff));
			if (aiPlayer.holdingGround) {
				System.out.print(", however they defended and it was reduced to: " + RED + (diff / 2) + RESET + " dmg");
			}
		} else if(mainRoll == aiRoll){
			System.out.println("You both rolled the same number! No Damage was dealt");
		} else {
			diff = Math.abs(aiRoll - mainRoll);
			mainPlayer.Damage(diff);
			System.out.print(randomString.generateRollString("Opponent", "You", diff));
			if (mainPlayer.holdingGround) {
				System.out.print(", however you defended and it was reduced to: " + RED + (diff / 2) + RESET + " dmg");
			}
		}
		System.out.println();
	}

	public void Action(Player enemy) {
		System.out.println();
		System.out.println("Current Round: " + currentRound + ", Your Current Health: " + GREEN + mainPlayer.health
				+ RESET + ", Opponents Health: " + GREEN + enemy.health + RESET + ", Defend or roll or Use an Item?");
		String action = input.nextLine();
		if (action.toLowerCase().equals("roll")) {
			this.mainRoll = mainPlayer.Roll();
		}
		if (action.toLowerCase().equals("defend")) {
			mainPlayer.Defend(currentRound);
			mainRoll = 5;
		}
		if(action.toLowerCase().equals("use an item") || action.toLowerCase().equals("item")){
			useItem();
			System.out.println();
			return;
		}
		if (!action.toLowerCase().equals("roll") && !action.toLowerCase().equals("defend")) {
			System.out.println("Unknown Command");
			this.Action(enemy);
		}
		System.out.println();
	}

	public void healRandomPlayer() {
		if (Math.random() > randomHealChance && (currentRound - lastRoundHealed) >= 2) { // 10% chance to randomly heal
			int health = randomInt(5, 10);
			double healChance = Math.random() - aiHealBias;
			if (healChance > 0.5) { // heal player
				System.out.println("You were randomly healed " + GREEN + health + RESET + " hp");
				mainPlayer.Heal(health);
			} else {
				System.out.println("The AI was randomly healed and gained " + GREEN + health + RESET + " hp");
				aiPlayer.Heal(health);
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
		Item Sword = new Sword();
		itemList.add(Sword);
	}


	public void AIuseItem(Player player){
		if(player.inventory.size() <= 0){
			return;
		}	
		if(Items.doesPlayerHaveItem(player, "Sword")){
			if(aiRoll <= 10){
				Item sword = Items.getItemFromName("Sword", player);
				if(sword.useItem(player)){
					aiRoll += sword.dmg;
					System.out.println("The Opponent used their Sword, They have " + sword.curDurability + " turns left until it breaks");
				} else {
					int swordIndex = player.getItemInvIndex(sword);
					player.removeItemFromInventory(swordIndex);
				}
				
			}
		}
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
			useItem(mainPlayer, item);
		}
	}

	public void aiDecideItem(AI npc){
		double[] weights = npc.itemDecideWeightTable();
		System.out.println("NPC Health Percentage: "+ npc.healthPercentage() + ", Damage Weight: " + weights[0] + ", Heal Weight: " + weights[1]);
		double damageItemWeight = weights[0]; // from 0 to 1 (For Percentage Multiply by 100)
		double healthItemWeight = weights[1]; // from 0 to 1
		if(npc.holdingGround){
			return;
		}
	}

	public void useItem(Player player, Item item){
		if(Items.doesPlayerHaveItem(player, item)){
			item.useItem(player);
		}
	}


	public boolean shouldBeMerchantRoom(){
		if(Game.lastMerchanRoom >= 6){
			if(mainPlayer.inventory.size() == 0 || (mainPlayer.inventory.size() < itemList.size())){
				if(Math.random() > 0.6){ // 40% chance for it to be a merchant room afterwards
					return true;
				}
			}
		}
		return false;
	}

	public void TurnCombat(){
		while((!mainPlayer.dead) || currentRoom.activePlayers.size() > 1){ // if currentRoom.size() returns anything above 1 we know that there are still NPCs left
			for(int i=0;i<currentRoom.activePlayers.size();i++){
				Player currentPlayer = currentRoom.activePlayers.players.get(i);
			}
		}
	}

	public void PlayerTurn(){
		for(int i=0;i<currentRoom.activePlayers.size();i++){
			Player enemy = currentRoom.activePlayers.players.get(i);
			Action(enemy);
		}
	}

}