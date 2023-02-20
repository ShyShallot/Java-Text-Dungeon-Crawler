This is the overarching Class that handles the Basic Player, this is what is used for the Human user of the program. Every Player Class required a [[Type]]

It also serves as the Base for the AI Class

## Private Variables 

```java
String name // Name of that player Object

int id // Id of the object, determined on creating based off of the Games class players Created value

int xp

int level

int health // If no health value is input to the constructer, the Health defined by the Type class will be used

int maxhealth // Same for Health

int mana 

int lastRoundDefended // This will store the Game.currentSubRound value when the Defend function is called

int coins

boolean holdingGround // Let us know if the Player is defending or not

Armor armor // Store what Armor the Player has equipped

boolean dead

ArrayList<Item> inventory 

Item inhand // Current Item in Hand

Spell spell // Store the spell the player wants to cast, if the player is not a mage, this is always null

Type type // The type that the player was set too on creation

HashMap<String, Skill> skills // The Key is the name of Skill, while the Value is the Skill class itsself
```

## Constructers

```java
Player(String name, Type type) // Health is set by the input Type

Player(String name, int health, Type type) // Health overrides health set by type and sets Max Health to Health

Player(String name, int health, int maxHealth, Type type)// Health overrides health set by type and sets Max Health to input
```


## Getters

```java
public int getItemInvIndex(Item item) // Returns the index of an Item in a Players inventory by using the Items Name, Returns -1 if nothing is found

public ArrayList<Item> getInventory() // Returns Whole Inventory

public int healthPercentage() // Returns the Health Percentage Rounded Down. Uses Health and MaxHealth

public int getHealth() // this.health

public int getMaxHealth() // this.maxHealth

public int getId() // this.id

public String getName() // this.name

public int getMana() // this.mana

public int lastRoundDefended() // this.lastRoundDefended

public int getCoins() // this.coins

public boolean isHoldingGround() // this.holdingGround

public Armor getArmor() // this.armor

public boolean isDead() // this.dead

public Item getHand() // this.inHand

public Type getType() // this.type

public int getXp() // this.xp

public int level() // this.level

public Spell getSpell() // Returns Spell "in hand"
``` 

## Setters

``` java
void addItemToInventory(Item item)

void removeItemFromInventory(int index)

void setHand(Item item)

void death() // sets this.dead to true

void setName(String newName)

void setId(int id)

void setHealth(int health) // Sets Health and Max Health to input Health

void setHealth(int health, int maxHealth)

void setMaxHealth(int maxHealth)

void removeMana(int amt) // subtractive function

void addMana(int amt) // adds Mana

void setlastRoundDefended(int round)

void addCoins(int amt)

void setArmor(Armor armor)

void giveXP() // Also handles leveling up

void addLevel(int lvl) // Generally this function doesnt need to be ran outside of giveXP 

void addSkill(SKill skill)

void setSpell(Spell spell)
```

## Player Functions

```java 
void Damage(int dmg, int dmgType, Player dealer) // if dealer is null this handled as the DOT function dealing damage. Armor and Defending Modification of Damage Values are all calculated here. Main String Output is also handled from this function

void Damage(int dmg) // When we want to just Deal Damage to a Player, No String Output

void Defend(int currentRound) // Just sets this.holdingGround to true and this.lastRoundDefended to the input

void unDefend() // sets this.holdingGround to false

void Heal(int hp) // Auto Clamps health if its greater than max

void killedPlayer(Player playerKilled) // Meant for String output as well as handleling xp giving as well as coins

void learnSkill(Skill skill) // Main Function for giving a Player a skill, runs checks if they meet the Reqs to learn that Skill

```