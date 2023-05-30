import java.awt.Color;
import java.awt.Font;
import java.util.*;

class Game implements Runnable{
	// Color Strings
	
	// Player
	public static int playersCreated = 0;
	public static Player mainPlayer;
	// Round Data 
	public static int currentRound = 1;
	public static int currentSubRound = 1;
	public static int lastRoundHealed;
	public static int lastMerchantRoom;
	public static int lastBossRoom;
	// AI
	double randomHealChance = 0.85;
	public static int difficulty = 0; // easy = 0, medium = 1, hard = 2
	double aiHealBias = 0.0;
	// Main
	boolean over = false;
	public static Scanner input;
	public static DOT DOTList = new DOT();
	public static StunList stunList = new StunList();
	public static Type[] playableTypeList;
	public static CastSpellList castList = new CastSpellList();
	public static boolean busy = false;
	public static boolean isSetup = false;

	// Input
	public void SetupInput(Scanner in) {
		input = in;
	}
	// Room
	Room currentRoom;

	// START OF MAIN GAME FUNCTIONS
	public void run() {
		if(isSetup){
			return;
		}
		addItemsToList();
		createTypes();
		createSkills();
		createSpells();
		playableTypeList = new Type[]{Type.getTypeFromName("Goblin"), Type.getTypeFromName("Knight"), Type.getTypeFromName("Mage")};
		//System.out.println("Welcome to Defend n Roll, A simple text fight game, where you can either defend for one turn and reduce dmg by 50% or roll. Damage is determined by the difference between the 2 rolls, who ever has the lowest roll, gets the damage.");
		int yHalf = Main.gp.screenHeight/2;
		int[][] animFrames = {{50,yHalf},{50,yHalf-5},{50,yHalf-10},{50,yHalf-15},{50,yHalf-20},{50,yHalf-15},{50,yHalf-10},{50,yHalf-5},{50,yHalf}};
		UIText welcome = new UIText(Main.gp, "Welcome to JDRC! Press Enter to Continue.", 50, Main.gp.screenHeight/2, 30);
		/*UISpriteSheet sheet = new UISpriteSheet("Art/Sprites/TestCube/spritesheet2.png", 202,202);
		int[] anim = new int[sheet.getSpriteChunks().length];
		for(int i=0;i<sheet.getSpriteChunks().length;i++){
			//System.out.println(i);
			anim[i] = i;
		}
		UISpriteAnim spriteAnim = new UISpriteAnim(anim, true, 1);
		UISprite testSquare = new UISprite(Main.gp, 200, 200, 0,sheet,1.1);
		testSquare.addAnimation("idle_00", spriteAnim);
		testSquare.playSpriteAnimation("idle_00");*/
		welcome.addAnimation("idle_00", new UIAnim(animFrames,true,false,16));
		welcome.playAnimation("idle_00");
		welcome.setCentered(true);
		waitForInput("Enter");
		System.out.println("Destorying Text");
		welcome.destory();
		//UIButton test = new UIButton(Main.gp, 300, 200, 200, 300, Color.white, "Yuh", 30);
		//waitForButtonPress(test.ID());
		System.out.println();
		setDifficulty();
		setPlayerType();
		System.out.println();
		isSetup = true;
		update();
	}

	public void update(){
		if(!isSetup){
			return;
		}
		if(over){
			return;
		}
		if(busy){
			return;
		}
		if(shouldBeMerchantRoom()){
			System.out.println("You come across a merchant sitting in a room.");
			MerchantRoom merchantRoom = new MerchantRoom();
			busy = true;
			merchantRoom.itemShop(false);
			busy = false;
			currentRound++;
			lastMerchantRoom = currentRound;
		} else if (shouldBeBossRoom()) {
			System.out.println("You walk into a massive chamber with a giant cage, You find a massive monster");
			BossRoom bossRoom = new BossRoom();
			BossCombat(bossRoom);
			lastBossRoom = currentRound;
			busy = false;
		} else {
			currentRoom = new Room();
			currentRoom.activeNPCS.renameDupes();
			TurnCombat();
			busy = false;
		}
	}

	public void TestWeights(){
		AI testNPC = new AI("Test", new Mage());
		testNPC.setHealth(60,100);
		aiDecideItem(testNPC);
	}

	public void addItemsToList(){
		new Sword();
		new Vial();
		new LargeVial();
		new Armor();
		new Bow();
		new TitaniumArmor();
		new Club();
		new Dagger();
		new Bite();
		new GoblinArmor();
		new Stomp();
		new Staff();
		new Ram();
	}

	public void createSkills(){
		new Speed1();
		new Weather1();
		new Weather2();
		new Weather3();
	}

	public void createSpells(){
		new Enlightment();
		new Fireball();
		new Jet();
		new Lightning();
		new Orb();
		for(int i=0;i<Spell.spells.size();i++){
			CusLib.DebugOutputLn(Spell.spells.get(i));
		}
	}

	public void createTypes(){
		new Goblin();
		new Hog();
		new Knight();
		new Mage();
		new Skeleton();
		new Spider();
	}

	public void DecideDamage(AI npc) {
		CusLib.DebugOutputLn(String.format("AI: %s, Player %s", npc.getType().toString(), mainPlayer.getType().toString()));
		CusLib.DebugOutputLn(String.format("Player Hand: %s, AI Hand: %s",mainPlayer.getHand().getName(), npc.getHand().getName()));
		if(npc.getType().baseSpeed() > mainPlayer.getType().baseSpeed()){
			CusLib.DebugOutputLn("NPC Speed is faster than player");
			playerUseItem(npc,mainPlayer);
			if(mainPlayer.getHealth() <= 0){ // player isnt marked as dead just yet so we have to check for health instead
				return;
			}
			playerUseItem(mainPlayer,npc);
		} else {
			CusLib.DebugOutputLn("NPC Speed is slower than player");
			playerUseItem(mainPlayer,npc);
			if(npc.getHealth() <= 0){
				return;
			}
			CusLib.DebugOutputLn(mainPlayer.getType().baseSpeed());
			double speedBonus = (mainPlayer.getType().baseSpeed())*CusLib.randomNum(0.01, 0.02); // these were just so that i could debug
			double random = Math.random()+speedBonus;
			double celing = CusLib.randomNum(0.7, 0.9);
			CusLib.DebugOutputLn(speedBonus + ", " + random + ", " + celing);
			if(random > celing){
				CusLib.queueText("You were faster than " + npc.getName() + " and dodged their attack!");
				return;
			}
			playerUseItem(npc,mainPlayer);
		}	
	} 


	public void playerUseItem(Player player, Player target){
		CusLib.DebugOutputLn("Dealer: " + player.getName() + ", Hand: " + player.getHand().getName() + ", Target: " + target.getName());
		if(stunList.isPlayerStunned(player)){
			CusLib.DebugOutputLn("Dealer is Stunned");
			return;
		}
		if(castList.isPlayerCastingSpell(player)){
			CusLib.DebugOutputLn("Dealer is Casting a spell");
			return;
		}
		player.getHand().useItem(player, target);
	}


	public void Action(Player enemy) {
		System.out.println();
		if(stunList.isPlayerStunned(mainPlayer)){
			System.out.println("You're stunned, your turn was skipped");
			return;
		}
		if(castList.isPlayerCastingSpell(mainPlayer)){
			System.out.println("You're busy casting a spell, you were skipped.");
			return;
		}
		//System.out.print("Current Round: " + currentSubRound + ", Your Current Health: " + CusLib.colorText(mainPlayer.getHealth(), "green") + ", Your Current Level: " + CusLib.colorText(mainPlayer.level(),"blue") + " (" + CusLib.colorText(mainPlayer.getXp(),"yellow") + "/" + CusLib.colorText((1000*(mainPlayer.level())),"purple") + ")" + " The " + CusLib.colorText(enemy.getName() + "'s", "red") + " Health: " + CusLib.colorText(enemy.getHealth(), "green") + ", ");
		if(mainPlayer.getType().getName().equals("Mage")){
			ActionMage(enemy);
			return;
		}
		UIButton defendButton = new UIButton(Main.gp, 320, 450, 100, 50, Color.white, "Defend", 20, 1);
		UIButton itemButton = new UIButton(Main.gp, 430, 450, 150, 50, Color.white, "Inventory" , 20,1);
		int id = waitForEitherButton(defendButton,itemButton);
		defendButton.destory();
		itemButton.destory();
		if(id == defendButton.ID()){
			mainPlayer.Defend(currentSubRound);
			return;
		}
		if(id == itemButton.ID()){
			ArrayList<UIButton> buttons = createInventoryButtons();
			int itemID = waitForEitherButton(buttons);
			UIButton button = (UIButton)(CusLib.getElementByID(itemID));
			Item itemToUse = (Item)button.getAccObject();
			mainPlayer.setHand(itemToUse);
			for(int i=0;i<buttons.size();i++){
				buttons.get(i).destory();
			}
			return;
		}
		/*System.out.print("Defend or Use an Item?");
		System.out.println();
		String action = input.nextLine();
		if (action.toLowerCase().equals("defend")) {
			mainPlayer.Defend(currentSubRound);
			return;
		}
		if(action.toLowerCase().equals("use an item") || action.toLo werCase().equals("item") && mainPlayer.getInventory().size() > 0){
			pickItem();
			System.out.println();
			return;
		}
		System.out.println("Unknown Command");
		this.Action(enemy);*/
	}

	public ArrayList<UIButton> createInventoryButtons(){
		ArrayList<UIButton> buttons = new ArrayList<>();
		int startX = 300;
		int xOffset = startX;
		int startY = 450;
		for(int i=0;i<mainPlayer.getInventory().size();i++){
			if(startY >= Main.gp.screenHeight-50){
				continue;
			}
			Item curItem = mainPlayer.getInventory().get(i);
			UIButton item = new UIButton(Main.gp, xOffset, startY, 50, 50, Color.white, "" + curItem.getName(), 10, 1);
			item.setAccObject(curItem);
			xOffset += item.width() + 20;
			if((i+1)%5 == 0){
				startY += item.height() + 10;
				xOffset = startX;
			}
			buttons.add(item);
		}
		return buttons;
	}

	public ArrayList<UIButton> createSpellButtons(){
		ArrayList<UIButton> buttons = new ArrayList<>();
		int startX = 300;
		int xOffset = startX;
		int startY = 450;
		for(int i=0;i<Spell.spells.size();i++){
			if(startY >= Main.gp.screenHeight-50){
				continue;
			}
			Spell spell = Spell.spells.get(i);
			if(!spell.canPlayerCast(mainPlayer)){
				continue;
			}
			UIButton spellButton = new UIButton(Main.gp, xOffset, startY, 50, 50, Color.white, "" + spell.name(), 10, 1);
			spellButton.setAccObject(spell);
			xOffset += spellButton.width() + 20;
			if((i+1)%5 == 0){
				startY += spellButton.height() + 10;
				xOffset = startX;
			}
			System.out.println(spellButton.ID() + "-->" + spellButton.message());
			buttons.add(spellButton);
		}
		return buttons;
	}

	public void ActionMage(Player enemy){
		UIButton defendButton = new UIButton(Main.gp, 380, 450, 100, 50, Color.white, "Defend", 20, 1);
		UIButton itemButton = new UIButton(Main.gp, 490, 450, 150, 50, Color.white, "Inventory" , 20,1);
		UIButton spellButton = new UIButton(Main.gp,490,510,150,50,Color.white,"Spell To Cast",15,1.0);
		int id = waitForEitherButton(defendButton,itemButton,spellButton);
		defendButton.destory();
		itemButton.destory();
		spellButton.destory();
		if(id == defendButton.ID()){
			mainPlayer.Defend(currentSubRound);
			return;
		}
		if(id == spellButton.ID()){
			ArrayList<UIButton> buttons = createSpellButtons();
			int spellID = waitForEitherButton(buttons);
			System.out.println(spellID + "/" + Main.gp.UIButtons.size());
			Spell spell = (Spell)Main.gp.UIButtons.get(spellID).getAccObject();
			mainPlayer.setSpell(spell);
			for(int i=0;i<buttons.size();i++){
				buttons.get(i).destory();
			}
			return;
		}
		if(id == itemButton.ID()){
			ArrayList<UIButton> buttons = createInventoryButtons();
			int itemID = waitForEitherButton(buttons);
			Item itemToUse = (Item)Main.gp.UIButtons.get(itemID).getAccObject();
			mainPlayer.setHand(itemToUse);
			for(int i=0;i<buttons.size();i++){
				buttons.get(i).destory();
			}
			if(mainPlayer.getSpell() == null){
				mainPlayer.setSpell(Spell.spellFromString("Fireball"));
			}
			return;
		}
		return;
	}

	public void healRandomPlayer(Player player) {
		if(player.isDead()){
			return;
		}
		if (Math.random() > randomHealChance && (currentSubRound - lastRoundHealed) >= 2) { // 10% chance to randomly heal
			int health = CusLib.randomNum(5, 10);
			double healChance = Math.random() - aiHealBias;
			if (healChance > 0.5) { // heal player
				System.out.println("You were randomly healed " + CusLib.colorText(health, "green") + " hp");
				mainPlayer.Heal(health);
			} else {
				System.out.println(player.getName() + " was randomly healed and gained " + CusLib.colorText(health, "green") + " hp");
				player.Heal(health);
			}
			lastRoundHealed = currentSubRound;
		}
	}

	public void setDifficulty(){
		String diff = "";
		UIText selectDiff = new UIText(Main.gp, "Start by selecting your Difficulty with one of the buttons", 0, 200, 30);
		selectDiff.setCentered(true);
		UIButton easy = new UIButton(Main.gp, 150, Main.gp.screenHeight/2, 250, 100, Color.white, "Easy", 20,0.5);
		UIButton medium = new UIButton(Main.gp, 325, Main.gp.screenHeight/2, 250, 100, Color.white, "Medium", 20,0.5);
		UIButton hard = new UIButton(Main.gp, 500, Main.gp.screenHeight/2, 250, 100, Color.white, "Hard", 20,0.5);
		int id = waitForEitherButton(easy,medium,hard);
		//System.out.println();
		if(id == medium.ID()){
			//System.out.println("You Selected: Medium");
			difficulty = 1;
			aiHealBias = 0.1;
			diff = "Medium";
		} else if(id == hard.ID()){
			//System.out.println("You Selected: Hard");
			difficulty = 2;
			aiHealBias = 0.2;
			randomHealChance = 0.9;
			diff = "Hard";
		} else {
			//System.out.println("You Selected: Easy");
			randomHealChance = 0.75;
			diff = "Easy";
		}
		selectDiff.destory();
		easy.destory();
		medium.destory();
		hard.destory();
		int notifTime = 2;
		UINotifcation diffSelect = new UINotifcation(Main.gp, String.format("You selected %s Difficulty",diff), 100, 200, 30, notifTime);
		diffSelect.setCentered(true);
		diffSelect.show();
		try{
			Thread.sleep(notifTime*1000);
		} catch (InterruptedException ex){
			ex.printStackTrace();
		}
	}

	public void setPlayerType(){
		UIText selectClass = new UIText(Main.gp, "Which class do you want to be?", 0, 200, 30);
		selectClass.setCentered(true);
		UIButton[] buttons = new UIButton[playableTypeList.length];
		int baseXPos = 0;
		switch(playableTypeList.length){
			case 1:
				baseXPos = 310;
				break;
			case 2: 
				baseXPos = 240;
				break;
			case 3:
				baseXPos = 165;
				break;
			case 4:
				baseXPos = 85;
				break;
		}
		for(int i=0;i<playableTypeList.length;i++){
			//System.out.println("Art/Covers/"+playableTypeList[i].getName());
			UIGraphicButton button = new UIGraphicButton(Main.gp, baseXPos, Main.gp.screenHeight/2, "Art/Covers/"+playableTypeList[i].getName()+".png", 0.7);
			button.setAccObject(playableTypeList[i]);
			buttons[i] = button;
			baseXPos = (button.xPos()+(button.getImage().getIconWidth()/2))+50;
		}
		//UIGraphicButton knightButton = new UIGraphicButton(Main.gp, 100, Main.gp.screenHeight/2, "Art/Covers/Knight.png", 0.7);
		//UIGraphicButton mageButton = new UIGraphicButton(Main.gp, knightButton.xPos()+(knightButton.getImage().getIconWidth()/2)+50, Main.gp.screenHeight/2, "Art/Covers/Mage.png", 0.7);
		int id = waitForEitherButton(buttons);
		UIGraphicButton buttonPressed = (UIGraphicButton)Main.gp.AllUIElems.get(id);
		for(UIButton button : buttons){
			button.destory();
		}
		selectClass.destory();
		/*
		System.out.println();
		String pickList = "";
		for(int i=0;i<playableTypeList.length;i++){
			if(i == playableTypeList.length-1){
				pickList += CusLib.colorText(playableTypeList[i].getName(), "green") + ", Stats: " + playableTypeList[i].getStatProps().toString(true) + ", Main Weapon: " + playableTypeList[i].getMainWeapon().getName() + ".";
				continue;
			}
			pickList += CusLib.colorText(playableTypeList[i].getName(), "green") + ", Stats: " + playableTypeList[i].getStatProps().toString(true) + ", Main Weapon: " + playableTypeList[i].getMainWeapon().getName() +", \n";
		}
		System.out.println(pickList);
		String pick = input.nextLine();
		*/
		Type selectedType = (Type)buttonPressed.getAccObject();
		if(selectedType == null){
			System.out.println("Was unable to find that type.");
			setPlayerType();
			return;
		}
		//System.out.println("You selected the " + selectedType.getName());
		UINotifcation diffSelect = new UINotifcation(Main.gp, String.format("You selected %s",selectedType.getName()), 100, 200, 30, 3);
		diffSelect.setCentered(true);
		diffSelect.show();
		try{
			Thread.sleep(3000);
		} catch (InterruptedException ex){
			ex.printStackTrace();
		}
		mainPlayer = new Player("You", selectedType.baseHealth()*2 ,selectedType);
		mainPlayer.addItemToInventory(selectedType.getMainWeapon());
		for(int i=0; i<5;i++){
			mainPlayer.addItemToInventory(Item.getItemFromName("Health Vial"));
		}
		mainPlayer.setArmor(mainPlayer.getType().getMainArmor());
		mainPlayer.addMana(selectedType.baseMana());
	}

	public void pickItem(){
		if(mainPlayer.getInventory().size() == 0){
			return;
		}
		if(mainPlayer.getInventory().size() == 1){
			if(mainPlayer.getInventory().get(0).getName().equals(mainPlayer.getType().getMainWeapon().getName())){
				mainPlayer.setHand(mainPlayer.getInventory().get(0));
				return;
			}
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
		Item item = Item.getItemFromName(itemPicked, mainPlayer);
		//System.out.println(item.name);
		if(item == null){
			System.out.println("You do not have that item.");
			pickItem();
			return;
		}
		mainPlayer.setHand(item);
	}

	public void pickSpell(){
		System.out.println("Which spell would you like to cast?");
		String spells = "";
		for(int i=0;i<Spell.spells.size();i++){
			Spell spell = Spell.spells.get(i);
			if(spell.canPlayerCast(mainPlayer)){
				if( i == Spell.spells.size()-1){
					spells += spell + ".";
				} else {
					spells += spell + ", \n";
				}
			}
		}
		System.out.println(spells);
		String selectedSpellString = input.nextLine();
		Spell selectedSpell = Spell.spellFromString(selectedSpellString);
		if(selectedSpell == null){
			System.out.println("Couldn't find that spell!");
			pickSpell();
			return;
		}
		mainPlayer.setSpell(selectedSpell);
		System.out.println();
		System.out.println("You selected to cast " + selectedSpell.name() + " It will take " + selectedSpell.turnsToCast() + " turns to cast.");
		return;
	}

	public void aiDecideItem(AI npc){
		//if(npc.getInventory().size() == 0){
		//	return;
		//}
		if(stunList.isPlayerStunned(npc)){
			return;
		}
		double[] weights = npc.itemDecideWeightTable();
		CusLib.DebugOutputLn(String.format("Npc Health: %s/%s, Percentage: %s", npc.getMaxHealth(),npc.getHealth(),npc.healthPercentage()));
		CusLib.DebugOutputLn("NPC Health Percentage: "+ npc.healthPercentage() + ", Damage Weight: " + weights[0] + ", Heal Weight: " + weights[1]);
		double damageItemWeight = weights[0]; // from 0 to 1 (For Percentage Multiply by 100)
		double healthItemWeight = weights[1]; // from 0 to 1
		if(npc.isHoldingGround()){
			return;
		}
		int maxDamage = 0;
		int maxHeal = 0;
		Item itemToUse = new Item();
		boolean shouldUseDamage = false;
		CusLib.DebugOutputLn("Damage Weight: " + damageItemWeight + ", Heal item Weight: " + healthItemWeight);
		if(damageItemWeight > healthItemWeight){
			shouldUseDamage = true;
			//System.out.println("AI wants to use Damage Item");
		}
		if(!doesPlayerHaveHeal(npc)){
			shouldUseDamage = true;
		}
		if(npc.getType().getName().equals("Mage")){
			npc.setSpell(npc.decideSpell(mainPlayer, shouldUseDamage));
			return;
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
					if(mainPlayer.getArmor() != null){
						maxDamage = (int)((double)itm.getDamage() * (double)mainPlayer.getArmor().armorEffectiveness(itm.getDamageType()));
					} else {
						maxDamage = itm.getDamage();
					}
					
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
		}
		return false;
	}


	public boolean shouldBeMerchantRoom(){
		if(Game.lastMerchantRoom >= CusLib.randomNum(1, 6)){
			if(Math.random() > 0.4){ // 60% chance for it to be a merchant room afterwards
				return true;
			}
		}
		return false;
	}

	public boolean shouldBeBossRoom(){
		if((Game.lastBossRoom - Game.currentRound) > 2 && Game.currentRound > 4){
			if(Math.random() > (0.65 - ((Game.lastBossRoom - Game.currentRound)/10))){
				return true;
			}
		}
		return false;
	}

	public void TurnCombat(){
		busy = true;
		/*String opponents = "You enter a new Room and find ";
		for(int i=0;i<currentRoom.activeNPCS.size();i++){
			if(i < currentRoom.activeNPCS.size()-1){
				opponents += "A " + CusLib.colorText(currentRoom.activeNPCS.npcs.get(i).getName(), "red") + ", ";
			} else {
				opponents += "and A " + CusLib.colorText(currentRoom.activeNPCS.npcs.get(i).getName(), "red") + ".";
			}
		}*/
		//CusLib.advanceText(opponents);
		int startX = 150;
		for(int i=0;i<currentRoom.activeNPCS.size();i++){
			UISquare opp = new UISquare(Main.gp, startX, 150, 70, Color.white);
			UISquare oppChild = new UISquare(Main.gp, startX, 225,opp.getWidth(), 8, Color.RED);
			UIText healthbarText = new UIText(Main.gp, ("%s" + "%c" + "%s" + "/" + "%s" + "%c" + "%s"), oppChild.xPos(), oppChild.yPos()+oppChild.getHeight(), 12,Color.white, Color.GREEN,currentRoom.activeNPCS.get(i).getHealth(),currentRoom.activeNPCS.get(i).getMaxHealth());
			UIText nameTag = new UIText(Main.gp,"%c",oppChild.xPos(),healthbarText.yPos()+15,12,Color.white,currentRoom.activeNPCS.get(i).getName());
			nameTag.setParent(opp, "name_tag");
			oppChild.setParent(opp, "health_bar");
			healthbarText.setParent(oppChild, "health_bar_text");
			startX += opp.getWidth()/2 + (10+opp.getWidth()); 
			int[][] idleAnim = {{-5,2},{-5,2},{-5,2},{5,-2},{5,-2},{5,-2}};
			opp.addAnimation("idle_00", new UIAnim(idleAnim,13,true,1.0,0.8));
			opp.playAnimation("idle_00");
			currentRoom.activeNPCS.get(i).setGraphicParent(opp);
		}
		while((!mainPlayer.isDead()) && currentRoom.activeNPCS.size() > 0){ 
			UIText roundDisplay = new UIText(Main.gp,"Current Round: " + "%c", 50, 490, 20, currentSubRound);
			UIText healthDisplay = new UIText(Main.gp, "Current Health:%s" + "%c" + "%s", 50, 470, 12, Color.white, Color.GREEN, mainPlayer.getHealth());
			UIText handText = new UIText(Main.gp,"Current Item in Hand: " + "%c", 50, 510, 20,mainPlayer.getHand().getName());
			for(int i=0;i<currentRoom.activeNPCS.size();i++){
				stunList.checkStuns();
				AI currentNPC = currentRoom.activeNPCS.get(i);
				if(currentSubRound > mainPlayer.lastRoundDefended()+1){
					mainPlayer.unDefend();
				}
				if(currentSubRound > currentNPC.lastRoundDefended()+1){
					currentNPC.unDefend();
				}
				//System.out.println();
				//printOpponents();
				//System.out.println("Current Opponent: " + CusLib.colorText(currentNPC.getName(), "red"));
				currentNPC.AIAction();
				aiDecideItem(currentNPC);
				Action(currentNPC);
				handText.update(mainPlayer.getHand().getName());
				currentNPC.getGraphicParent().stopAnimation();
				DecideDamage(currentNPC);
				healthDisplay.update(mainPlayer.getHealth());
				castList.castSpells();
				healthDisplay.update(mainPlayer.getHealth());
				DOTList.dealOutDamage(currentSubRound);
				healthDisplay.update(mainPlayer.getHealth());
				//CusLib.callQueue(true,false);
				if(currentNPC.isDead()){
					//System.out.println(currentNPC.getName() + " has " + currentNPC.getCoins() + " Coins");
					mainPlayer.killedPlayer(currentNPC);
					i--;
				}
				if(mainPlayer.isDead()){
					over = true;
					System.out.println("You died and made it through " + CusLib.colorText(currentRound, "green") + " Rooms, Congrats and Better Luck next time!");
					currentRound--;
					break;
				}
				currentRoom.activeNPCS.GarbageCleanup();
				currentNPC.getGraphicParent().playAnimation("idle_00");
				healRandomPlayer(currentNPC);
				currentSubRound++;
				roundDisplay.update(currentSubRound);
			}	
		}
		if(over){
			return;
		}
		System.out.println("Congrats on clearing Room " + currentRound + "!");
		if(Math.random() > 0.75){
			currentRoom.treasureChest();
		}
		roundOver();
		System.out.println();
	}

	public void BossCombat(BossRoom bossRoom){
		busy = true;
		currentSubRound = 1;
		while(!mainPlayer.isDead() && !bossRoom.boss().isDead()){
			Boss boss = bossRoom.boss();
			stunList.checkStuns();
			if(currentSubRound > mainPlayer.lastRoundDefended()+1){
				mainPlayer.unDefend();
			}
			if(currentSubRound > boss.lastRoundDefended()+1){
				boss.unDefend();
			}
			System.out.println("Current Boss Health: " + CusLib.colorText(boss.getHealth(), "red"));
			boss.prepareAttack();
			Action(boss);
			DecideDamage(boss);
			castList.castSpells();
			DOTList.dealOutDamage(currentSubRound);
			CusLib.callQueue(true,false);
			if(boss.isDead()){
				System.out.println("You successfully killed " + boss.getName() + " Boss, Congrats!!");
				mainPlayer.killedBoss(boss);
				bossRoom.rewardPlayer();
			}
			if(mainPlayer.isDead()){
				over = true;
				currentRound--;
				System.out.println("You died a true soldier while trying to defeat the " + boss.getName() + " Boss, Better luck next time.");
				break;
			}
		}
		if(over){
			return;
		}
		roundOver();

	}

	public void roundOver(){
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
				totalRoomHealth += CusLib.colorText(curNpc.getName() + "'s", "red") + " Health: " + CusLib.colorText(curNpc.getHealth(), "green") + " HP, ";
			} else {
				totalRoomHealth += CusLib.colorText(curNpc.getName() + "'s", "red") + " Health: " + CusLib.colorText(curNpc.getHealth(), "green") + " HP.";
			}
				
		}
		System.out.println(totalRoomHealth);
	}

	public static int DifficultyMod(boolean unCapped){
		if(difficulty == 0){
			return 1;
		}
		if(unCapped){
			int diff = difficulty+1;
			return diff;
		}
		return difficulty;
	}

	public static int DifficultyMod(){
		return DifficultyMod(false);
	}

	public static void buttonPressed(UIButton button){
		System.out.println(String.format("Button %s was pressed", button.ID()));
	}

	public static void waitForButtonPress(int id){
		UIButton buttonToWaitFor = null;
		for(UIButton button : Main.gp.getCreatedButtons()){
			if(button.ID() == id && !button.isDead()){
				buttonToWaitFor = button;
			}
		}
		if(buttonToWaitFor == null){
			CusLib.DebugOutputLn("No Button found with that ID canceling");
			return;
		}
		while(!buttonToWaitFor.activated){
			//System.out.println("Waiting for Button " + id + " To be pressed");
		}
	}

	public int waitForEitherButton(UIButton ... buttons){
		boolean check = true;
		while(check){
			for(UIButton button : buttons){
				if(button.activated){
					check = false;
					return button.ID(); 
				}
			}
		}
		return 0; // This will never be returned but its to appease java
	}

	public int waitForEitherButton(ArrayList<UIButton> buttons){
		UIButton[] buttonArray = new UIButton[buttons.size()];
		for(int i=0;i<buttons.size();i++){
			buttonArray[i] = buttons.get(i);
		}
		return waitForEitherButton(buttonArray);
	}

	public void waitForInput(String key){
		while(!Input.wasKeyPressed(key)){

		}
		CusLib.DebugOutputLn(key + " was pressed");
	}


}