import java.util.*;

class Game {
	// Color Strings
	public static final String RESET = "\u001B[0m";
	public static final String RED = "\u001B[31m";
	public static final String GREEN = "\u001B[32m";
	public static final String YELLOW = "\u001B[33m";
	// Player
	public static int playersCreated = 0;
	public static Player mainPlayer;
	// Round Data 
	public static int currentRound = 1;
	public static int currentSubRound = 1;
	int lastRoundHealed;
	public static int lastMerchantRoom;
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
	public static DOT DOTList = new DOT();
	public static Type[] playableTypeList = {new Goblin(), new Knight(), new Mage()};

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
		Types.createTypeLib();
		System.out.println("Welcome to Defend n Roll, A simple text fight game, where you can either defend for one turn and reduce dmg by 50% or roll. Damage is determined by the difference between the 2 rolls, who ever has the lowest roll, gets the damage.");
		System.out.println();
		setDifficulty();
		setPlayerType();
		System.out.println();
		addItemsToList();
		while (!over) {
			if(shouldBeMerchantRoom()){
				System.out.println("You come across a merchant sitting in a room.");
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
		AI testNPC = new AI("Test", new Mage());
		testNPC.setHealth(56,75);
		aiDecideItem(testNPC);
	}

	public void DecideDamage(AI npc) {
		//System.out.println(String.format("AI: %s, Player %s", npc.getType().toString(), mainPlayer.getType().toString()));
		//System.out.println(String.format("Player Hand: %s, AI Hand: %s",mainPlayer.getHand().getName(), npc.getHand().getName()));
		if(npc.getType().baseSpeed() > mainPlayer.getType().baseSpeed()){
			//System.out.println("NPC Speed is faster than player");
			npc.getHand().useItem(npc,mainPlayer);
			mainPlayer.getHand().useItem(mainPlayer,npc);
		} else {
			//System.out.println("NPC Speed is slower than player");
			mainPlayer.getHand().useItem(mainPlayer,npc);
			npc.getHand().useItem(npc, mainPlayer);
		}	
	} 

	public void Action(Player enemy) {
		System.out.println();
		System.out.print("Current Round: " + currentRound + ", Your Current Health: " + GREEN + mainPlayer.getHealth()
				+ RESET + " The " + RED + enemy.getName() + "'s" + RESET + " Health: " + GREEN + enemy.getHealth() + RESET + ", Defend or use an Item?");
		System.out.println();
		String action = input.nextLine();
		if (action.toLowerCase().equals("defend")) {
			mainPlayer.Defend(currentRound);
		}
		if(action.toLowerCase().equals("use an item") || action.toLowerCase().equals("item") && mainPlayer.getInventory().size() > 0){
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
				System.out.println(player.getName() + " was randomly healed and gained " + GREEN + health + RESET + " hp");
				player.Heal(health);
			}
			lastRoundHealed = currentRound;
		}
	}

	public int randomInt(int min, int max) {
		return (int) Math.floor(Math.random() * (max - min + 1) + min);
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

	public static void setPlayerType(){
		System.out.println("What class would you like to pick?");
		String pickList = "";
		for(int i=0;i<playableTypeList.length;i++){
			if(i == playableTypeList.length-1){
				pickList += playableTypeList[i].getName() + ", Stats: " + playableTypeList[i].getStatProps().toString(true) + ", Main Weapon: " + playableTypeList[i].getMainWeapon().getName() + ".";
				continue;
			}
			pickList += playableTypeList[i].getName() + ", Stats: " + playableTypeList[i].getStatProps().toString(true) + ", Main Weapon: " + playableTypeList[i].getMainWeapon().getName() +", \n";
		}
		System.out.println(pickList);
		String pick = input.nextLine();
		Type selectedType = Types.getTypeFromName(pick);
		if(selectedType == null){
			System.out.println("Was unable to find that type.");
			setPlayerType();
		}
		System.out.println("You selected the " + selectedType.getName());
		mainPlayer = new Player("You", selectedType.baseHealth()*2 ,selectedType);
		mainPlayer.addItemToInventory(selectedType.getMainWeapon());
		mainPlayer.setArmor(mainPlayer.getType().getMainArmor());
	}

	public void addItemsToList(){
		itemList.add(new Sword());
		itemList.add(new Vial());
		itemList.add(new LargeVial());
		itemList.add(new Armor());
		itemList.add(new Bow());
		itemList.add(new TitaniumArmor());
	}

	public void pickItem(){
		if(mainPlayer.getInventory().size() == 0){
			return;
		}
		System.out.println("Which item would you like to use?");
		String itemList = "";
		for(int i=0;i<mainPlayer.getInventory().size();i++){
			Item itm = mainPlayer.getInventory().get(i);
			if(itm.isUseable() || itm.isArmor() || (itm.isArmor() && itm.isArmor())){
				continue;
			}
			itemList += itm.getName() + ": Description: " + itm.getDescription() + ". \n";
		}
		System.out.println(itemList); 
		String itemPicked = input.nextLine();
		Item item = Items.getItemFromName(itemPicked, mainPlayer);
		//System.out.println(item.name);
		if(item == null){
			System.out.println("You do not have that item.");
			pickItem();
		}
		mainPlayer.setHand(item);
	}

	public void aiDecideItem(AI npc){
		if(npc.getInventory().size() == 0){
			return;
		}
		double[] weights = npc.itemDecideWeightTable();
		//System.out.println(String.format("Npc Health: %s/%s, Percentage: %s", npc.getMaxHealth(),npc.getHealth(),npc.healthPercentage()));
		//System.out.println("NPC Health Percentage: "+ npc.healthPercentage() + ", Damage Weight: " + weights[0] + ", Heal Weight: " + weights[1]);
		double damageItemWeight = weights[0]; // from 0 to 1 (For Percentage Multiply by 100)
		double healthItemWeight = weights[1]; // from 0 to 1
		if(npc.isHoldingGround()){
			return;
		}
		int maxDamage = 0;
		int maxHeal = 0;
		Item itemToUse = new Item();
		boolean shouldUseDamage = false;
		//System.out.println("Damage Weight: " + damageItemWeight + ", Heal item Weight: " + healthItemWeight);
		if(damageItemWeight > healthItemWeight){
			shouldUseDamage = true;
			//System.out.println("AI wants to use Damage Item");
		}
		if(!doesPlayerHaveHeal(npc)){
			shouldUseDamage = true;
		}
		for(int i=0;i<npc.getInventory().size();i++){
			Item itm = npc.getInventory().get(i);
			//System.out.println("Calculating use for " + itm.getName());
			if(itm.isArmor()){
				//System.out.println("Item is Armor");
				continue;
			}
			if(shouldUseDamage && (!itm.isHeal())){
				//System.out.println("Item isnt Heal");
				if(itm.getDamage() > maxDamage){
					//System.out.println("Item does max Damage");
					itemToUse = itm;
					maxDamage = itm.getDamage();
				}
			}
			if(!shouldUseDamage && (itm.isHeal())){
				//System.out.println("Item heals");
				Vial healItem = (Vial) itm;
				if(healItem.getHealAmt() >= maxHeal){
					itemToUse = healItem;
					maxHeal = healItem.getHealAmt();
				}
			}
		}
		npc.setHand(itemToUse);
		//System.out.println("Set hand to: " + itemToUse.getName());
	}

	public boolean doesPlayerHaveHeal(Player player){
		int heals = 0;
		for(int i=0;i<player.getInventory().size();i++){
			Item itm = player.getInventory().get(i);
			if(itm.isHeal()){
				heals++;
			}
		}
		if(heals > 0 ){
			return true;
		} else {
			return false;
		}
	}


	public boolean shouldBeMerchantRoom(){
		if(Game.lastMerchantRoom >= CusMath.randomNum(1, 6)){
			if(Math.random() > 0.4){ // 40% chance for it to be a merchant room afterwards
				return true;
			}
		}
		return false;
	}

	public void TurnCombat(){
		String opponents = "You enter a new Room and find ";
		for(int i=0;i<currentRoom.activeNPCS.size();i++){
			if(i < currentRoom.activeNPCS.size()-1){
				opponents += "A " + RED + currentRoom.activeNPCS.npcs.get(i).getName() + RESET + ", ";
			} else {
				opponents += "and A " + RED + currentRoom.activeNPCS.npcs.get(i).getName() + RESET;
			}
		}
		System.out.println(opponents);
		while((!mainPlayer.isDead()) && currentRoom.activeNPCS.size() > 0){ 
			for(int i=0;i<currentRoom.activeNPCS.size();i++){
				System.out.println();
				printOpponents();
				AI currentNPC = currentRoom.activeNPCS.npcs.get(i);
				System.out.println("Current Opponent: " + RED + currentNPC.getName() + RESET);
				currentNPC.AIAction();
				aiDecideItem(currentNPC);
				Action(currentNPC);
				DecideDamage(currentNPC);
				if(currentNPC.getHealth() <= 0){
					//System.out.println(currentNPC.getName() + " has " + currentNPC.getCoins() + " Coins");
					mainPlayer.killedPlayer(currentNPC);
					currentNPC.death(mainPlayer);
				}
				if(mainPlayer.getHealth() <= 0){
					mainPlayer.death(currentNPC);
					over = true;
					System.out.println("You died and made it through " + Game.GREEN + currentRound + Game.RESET + " Rooms, Congrats and Better Luck next time!");
					currentRound--;
					break;
				}
				DOTList.dealOutDamage(currentSubRound);
				currentRoom.activeNPCS.GarbageCleanup();
				healRandomPlayer(currentNPC);
				currentSubRound++;
			}	
		}
		if(over){
			return;
		}
		System.out.println("Congrats on clearing Room " + currentRound + "!");
		mainPlayer.Heal((mainPlayer.getMaxHealth()/2));
		DOTList.reset();
		currentSubRound = 1;
		currentRound++;
		if(currentRound > 5 && difficulty < 1){
			difficulty = 1;
			System.out.println("You have progressed through enough rooms that the Game Difficulty changed to Medium");
		}
		if(currentRound > 10 && difficulty < 2){
			difficulty = 2;
			System.out.println("You have progressed through enough rooms that the Game Difficulty changed to Hard");
		}
	}

	public void printOpponents(){
		String totalRoomHealth = "";
		for(int i=0;i<currentRoom.activeNPCS.size();i++){
			AI curNpc = currentRoom.activeNPCS.get(i);
			if(i<currentRoom.activeNPCS.size()-1){
				totalRoomHealth += RED + curNpc.getName() + "'s" + RESET + " Health: " + GREEN + curNpc.getHealth() + RESET + " HP, ";
			} else {
				totalRoomHealth += RED + curNpc.getName() + "'s" + RESET + " Health: " + GREEN + curNpc.getHealth() + RESET + " HP.";
			}
				
		}
		System.out.println(totalRoomHealth);
	}

}