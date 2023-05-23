import java.awt.Color;
import java.util.*;
class Player {	
	private String name;
	private int id;
	private int xp = 0;
	private int level = 1;
	private int health;
	private int maxHealth;
	private int mana;
	private int lastRoundDefended;
	private int coins;
	private boolean holdingGround = false;
	private Armor Armor;
	private boolean dead;
	private ArrayList<Item> inventory = new ArrayList<Item>();
	private Item inHand;
	private Spell spell;
	private Type type;
	private HashMap<String, Skill> skills = new HashMap<>();
	private UIAnimatable graphicElement;
	

	public Player(String name, Type type){
		this.name = name;
		this.type = type;
		this.id = Game.playersCreated;
		Game.playersCreated++;
		this.inHand = this.type.getMainWeapon();
		this.health = this.type.baseHealth();
		this.maxHealth = this.type.baseMaxHealth();
	}

	public Player(String name, int health, Type type){
		this.name = name;
		this.health = health;
		this.maxHealth = health;
		this.type = type;
		this.id = Game.playersCreated;
		Game.playersCreated++;
		this.inHand = this.type.getMainWeapon();
		this.type.getStatProps().setHealth(health, maxHealth);
	}

	public Player(String name, int health, int maxHealth, Type type){
		this.name = name;
		this.health = health;
		this.maxHealth = maxHealth;
		this.type = type;
		this.id = Game.playersCreated;
		Game.playersCreated++;
		this.inHand = this.type.getMainWeapon();
		this.type.getStatProps().setHealth(health, maxHealth);
	}

	public void setGraphicParent(UIAnimatable parent){
		this.graphicElement = parent;
	}

	public UIAnimatable getGraphicParent(){
		return this.graphicElement;
	}

	public void Defend(int currentRound){
		this.holdingGround = true;
		this.lastRoundDefended = currentRound;
	}
	public void unDefend(){
		this.holdingGround = false;
	}
	public void Heal(int hp){
		this.health += hp;
		if(this.health >= this.maxHealth){
			this.health = this.maxHealth;
			healEvent();
			return;
		}
		healEvent();
	}

	public void Damage(int dmg, int dmgType, Player dealer){
		if(Armor != null){
			int amountBlocked = Armor.damageBlocked(dmg, dmgType);
			if(this.getName().equals("You")){
				//CusLib.queueText(String.format("Your %s blocks %s of the %s Damage (%s -> %s)",CusLib.colorText(Armor.getName(),"blue"),CusLib.colorText(dmg-amountBlocked, "red"), Items.getDamageTypeName(dmgType), CusLib.colorText(dmg, "red"),CusLib.colorText(amountBlocked, "red")));
				CusLib.queueText(new UIText(Main.gp,"Your %c blocks %c of the %c Damage (%c -> %c)",0,0,10,Color.white,Armor.getName(),dmg-amountBlocked,Items.getDamageTypeName(0),dmg,amountBlocked));
			} else 
			if(!Armor.getName().equals("Skeleton Armor")){
				//CusLib.queueText(String.format("%s's %s blocks %s of the %s Damage (%s -> %s)",this.getName(),CusLib.colorText(Armor.getName(),"blue"),CusLib.colorText(dmg-amountBlocked, "red"), Items.getDamageTypeName(dmgType), CusLib.colorText(dmg, "red"),CusLib.colorText(amountBlocked, "red")));
				CusLib.queueText(new UIText(Main.gp,"%c %c blocks %c of the %c Damage (%c -> %c)",0,0,10,Color.white,this.getName(),Armor.getName(),dmg-amountBlocked,Items.getDamageTypeName(0),dmg,amountBlocked));
			}
			dmg = amountBlocked;
		}
		if(this.holdingGround){
			dmg /= 2;
		}
		this.health -= dmg;
		if(this.health <= 0 ){
			this.health = 0;
			this.dead = true;
		}
		if(dealer == null){
			if(dmg == 0){
				if(this.getName().equals("You")){
					//CusLib.queueText(String.format("%s are immune to the effects of %s and didn't take any damage.", this.getName(),Items.getDamageTypeName(dmgType)));
					CusLib.queueText(new UIText(Main.gp,"%c are immune to the effects of %c and didn't take any damage.",0,0,0,Color.white,this.getName(),Items.getDamageTypeName(dmgType)));
				} else {
					//CusLib.queueText(String.format("%s is immune to the effects of %s and didn't take any damage.", this.getName(),Items.getDamageTypeName(dmgType)));
					CusLib.queueText(new UIText(Main.gp,"%c is immune to the effects of %c and didn't take any damage.",0,0,0,Color.white,this.getName(),Items.getDamageTypeName(dmgType)));
				}
				return;
			}
			//CusLib.queueText(String.format("%s felt the effects of %s and took %s dmg! (%s --> %s)",CusLib.colorText(this.getName(),"red"),Items.getDamageTypeName(dmgType),CusLib.colorText(dmg, "red"),CusLib.colorText(this.health+dmg,"green"),CusLib.colorText(this.health,"green")));
			CusLib.queueText(new UIText(Main.gp,"%c felt the effects of %c and took %c dmg! (%c -> %c)",0,0,0,this.getName(),Items.getDamageTypeName(dmgType),dmg,health+dmg,this.health));
		} else {
			//CusLib.queueText(String.format("%s dealt %s damage to %s! (%s --> %s)",dealer.getName(),CusLib.colorText(dmg,"red"),this.getName(),CusLib.colorText(this.health+dmg,"green"),CusLib.colorText(this.health, "green")));
			CusLib.queueText(new UIText(Main.gp,"%c dealt %c damage to %c! (%c --> %c)",0,0,0,dealer.getName(),dmg,this.getName(),this.health+dmg,this.health));
		}

		this.damageEvent();
	}

	public void Damage(int dmg){
		if(Armor != null){
			int amountBlocked = Armor.damageBlocked(dmg, 0);
			if(this.getName().equals("You")){
				//CusLib.queueText(String.format("Your %s blocks %s of the %s Damage (%s -> %s)",CusLib.colorText(Armor.getName(),"blue"),CusLib.colorText(dmg-amountBlocked, "red"), Items.getDamageTypeName(0), CusLib.colorText(dmg, "red"),CusLib.colorText(amountBlocked, "red")));
				CusLib.queueText(new UIText(Main.gp,"Your %c blocks %c of the %s %c Damage %s (%c -> %c)",0,0,10,Color.white,Color.RED,Armor.getName(),dmg-amountBlocked,Items.getDamageTypeName(0),dmg,amountBlocked));
			} else 
			if(!Armor.getName().equals("Skeleton Armor")){
				//CusLib.queueText(String.format("%s's %s blocks %s of the %s Damage (%s -> %s)",this.getName(),CusLib.colorText(Armor.getName(),"blue"),CusLib.colorText(dmg-amountBlocked, "red"), Items.getDamageTypeName(0), CusLib.colorText(dmg, "red"),CusLib.colorText(amountBlocked, "red")));
				CusLib.queueText(new UIText(Main.gp,"%c %c blocks %c of the %s %c Damage %s (%c -> %c)",0,0,10,Color.white,Color.RED,this.getName(),Armor.getName(),dmg-amountBlocked,Items.getDamageTypeName(0),dmg,amountBlocked));
			}
			dmg = amountBlocked;
		}
		if(this.holdingGround){
			dmg /= 2;
		}
		this.health -= dmg;
		if(this.health <= 0 ){
			this.health = 0;
			this.dead = true;

		}
		this.damageEvent();
	}

	public void damageEvent(){
		updateHealthBar();
	}

	public void healEvent(){
		updateHealthBar();
	}

	public void updateHealthBar(){
		if(this.graphicElement != null){
			if(this.graphicElement.getChild("health_bar") == null){
				return;
			}
			UISquare healthBar = (UISquare)this.graphicElement.getChild("health_bar");
			healthBar.setWidth((int)((double)healthBar.getWidth() * (double)(this.healthPercentage()/100.0)));
			if(healthBar.getChild("health_bar_text") != null){
				UIText healthBarText = (UIText)healthBar.getChild("health_bar_text");
				healthBarText.update(this.getHealth(),this.getMaxHealth());
			}
		}
	}

	public void addItemToInventory(Item item){
		inventory.add(item);
	}

	public void removeItemFromInventory(int index){
		inventory.remove(index);
	}

	public int getItemInvIndex(Item item){
		int index = -1;
		for(int i=0;i<this.inventory.size();i++){
			if(this.inventory.get(i).getName().equals(item.getName())){
				index = i;
			}
		}
		return index;
	}

	public void setHand(Item item){
		this.inHand = item;
	}

	public void killedPlayer(Player playerKilled){
		if(playerKilled.getCoins() > 0){
			this.coins += playerKilled.coins;
			System.out.println(this.name + " killed " + CusLib.colorText(playerKilled.getName(),"red") + " and took all of their " + CusLib.colorText(playerKilled.getCoins(),"yellow") + " Coin(s)");
		} else{
			System.out.println(this.name + " killed " + CusLib.colorText(playerKilled.getName(),"red"));
		}
		int xp = (int)((CusLib.randomNum(100, 250))*(CusLib.randomNum(1, 2.5)));
		xp += CusLib.randomNum(-100, 100);
		this.giveXp(xp);
		playerKilled.death();
	}

	public void killedBoss(Boss bossKilled){
		int coinsToGive = CusLib.randomNum(5, 25*Game.DifficultyMod());
		System.out.println(this.name + " killed " + CusLib.colorText(bossKilled.getName(),"red") + " and took all of their " + CusLib.colorText(coinsToGive,"yellow") + " Coin(s)");
		int xp = (int)((CusLib.randomNum(400, 850))*((CusLib.randomNum(1, 2)) + Game.difficulty));
		this.giveXp(xp);

	}

	public void death(){
		this.dead = true;
		if(this.graphicElement != null){
			this.graphicElement.destory();
		}
	}

	public int healthPercentage(){
		return CusLib.percentage(this.health, this.maxHealth);
	}

	public String getName(){
		return this.name;
	}

	public void setName(String newName){
		this.name = newName;
	}

	public int getID(){
		return this.id;
	}

	public void setID(int id){
		this.id = id;
	}

	public int getHealth(){
		return this.health;
	}

	public void setHealth(int health){
		this.health = health;
	}

	public void setHealth(int health, int maxHealth){
		this.health = health;
		this.maxHealth = maxHealth;
	}

	public int getMaxHealth(){
		return this.maxHealth;
	}

	public void setMaxHealth(int maxHealth){
		this.maxHealth = maxHealth;
	}

	public int getMana(){
		return this.mana;
	}

	public void removeMana(int amt){
		this.mana -= amt;
	}

	public void addMana(int amt){
		this.mana += amt;
	}

	public int lastRoundDefended(){
		return this.lastRoundDefended;
	}

	public void setlastRoundDefended(int round){
		this.lastRoundDefended = round;
	}

	public int getCoins(){
		return this.coins;
	}

	public void addCoins(int amt){
		this.coins += amt;
	}

	public boolean isHoldingGround(){
		return this.holdingGround;
	}

	public Armor getArmor(){
		return this.Armor;
	}

	public void setArmor(Armor armor){
		this.Armor = armor;
	}

	public boolean isDead(){
		return this.dead;
	}

	public ArrayList<Item> getInventory(){
		return this.inventory;
	}

	public Item getHand(){
		return this.inHand;
	}

	public Type getType(){
		return this.type;
	}

	public int getXp(){
		return this.xp;
	}

	public void giveXp(int amt){
		this.xp += amt;
		if(this.xp >= 1000*(this.level)){
			this.addLevel(1);
		}
		System.out.println(String.format("%s gained %s xp", this.name, CusLib.colorText(amt,"purple")));
	}

	public int level(){
		return this.level;
	}

	public void addLevel(int lvl){
		this.level += lvl;
		System.out.println(String.format("%s leveled up to %s", this.name, this.level));
		learnSkill();
	}

	public void learnSkill(){
		String learnableSkills = "";
		for(int i=0;i<skills.size();i++){
			if(!skills.get(i).canLearnSkill(this)){
				continue;
			}
			if(i == skills.size()-1){
				learnableSkills += skills.get(i).toStringSimplified() + ".";
			} else {
				learnableSkills += skills.get(i).toStringSimplified() + "\n";
			}
		}
		if(learnableSkills.equals("")){
			return;
		}
		System.out.println("Choose a skill to learn.");
		System.out.println(learnableSkills);
		String skillToLearn = Game.input.nextLine();
		Skill skill = SkillsTree.getSkillFromName(skillToLearn);
		if(skill == null){
			System.out.println("Invalid Skill");
			learnSkill();
			return;
		}
		if(skills.containsKey(skill.name())){
			System.out.println("You already know that skill.");
			return;
		}
		if(this.level >= skill.getLevel()){
			if(skill.canLearnSkill(this)){
				this.skills.put(skill.name(),skill);
				System.out.println("You learned " + CusLib.colorText(skill.name(), "yellow") + "!");
			}
		} else {
			System.out.println("You aren't high enough level for that skill.");
		}
	}

	public void addSkill(Skill skill){
		this.skills.put(skill.name(),skill);
	}

	public boolean hasSkill(Skill skill){
		return this.skills.containsKey(skill.name());
	}

	public Spell getSpell(){
		return this.spell;
	}

	public void setSpell(Spell spell){
		this.spell = spell;
	}

	public boolean equals(Object object){
		Player player = (Player) object;
		if(player.getID() == this.id){
			return true;
		}
		return false;
	}
}
