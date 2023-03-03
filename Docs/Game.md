## Public Static Variables

```java

// Players

int playersCreated // The amount of players that are created, this is incremented by 1 everytime the player constructer is called

Player mainPlayer // the Main Player object

// Round Data

int currentRound // The Current Room the player is in

int currentSubRound // the Current Turn the player is in, increases every full turn cycle

int lastRoundHealed // last subround that random healing happend

int lastMerchantRoom // the last round that a merchant room happened

int lastBossRoom // the last round that a Boss happened

// AI

double randomHealChance // the chance that random healing happens

int difficulty // the difficulty of the ai, easy = 0, medium = 1, hard = 2

double aiHealBias // When random healing happens what is the chance that it will be the AI (on top of a 50/50)

// Main

boolean over // whether or not the player died

Scanner input // Text input from terminal/command line

ArrayList<Item> itemList // global item list, these should be items that the player should get, dont include AI specific items (bite, ram and so on)

DOT DOTList // the Global DOT List

StunList stunList // Global List of Stunned Players

Type[] playableTypeList // The List containing all the Types the player is able to select

CastSpellList castList // The list for Players casting a multi turn spell

SkillsTree skillTree // The Global Skills Tree
```

## Functions

```java
void Start() // Main overarching Function that handles the initlization of important variables while also being responible for the creation of the current room.

void setDifficulty() // This is responible for telling the player the available difficulties and letting them selected which one

void setPlayerType() // Outputs the available types the player can select from, and lets them select which type

void addItemsToList() // This adds item to the ArrayList itemList of the items that the player is able to get, DO NOT INCLUDE TYPE SPECFIC ITEMS

void TestWeights() // This is a debug function that creates a Dummy NPC and runs the aiDecideItem function to calibrate as well as debug the itemWeight matrix

void SetupInput(Scanner in ) // Sets the Scanner variable to the one input into the function, makes it so that we have a global input, instead of creating one for each time we want the players input

void DecideDamage(AI npc) // This is the function that decides who deals the damage first, and actually dealing the damage, if the player is faster than the NPC their attacking there is a chance the player can dodge the NPCs attack

void playerUseItem(Player player, Player target) // This just checks if the Player is stunned or casting a spell before using an item

void Action(Player enemy) // This is the main action input function for the Player, it allows them to decide if they want to attack, use an item or defend, if the Player is a mage, the function will then call ActionMage(Player enemy)

void ActionMage(Player enemy) // This is the child function to Action, this just allows the Player to pick a spell instead of a normal attack.

void HealRandomPlayer(Player player) // Picks the between the Main Player and the input Player and on a small chance deicdes if it should randomly heal either one of them

void pickItem() // Outputs the Players inventory and allows them which item they would like to use, If the size of their inventory is 1 and the Item's name is equal to their Type's Main weapon (mainPlayer.getType().getMainWeapon()) then that Item is automaticly Selected

void pickSpell() // Allows the player to select a spell to cast, outputs the list of spells they are able to cast

void aiDecideItem(AI npc) // This is a function that decides what Item the AI should use, this is decided by the npc.itemDecideWeightTable() which decides if it should use a damage or healing item, then the item is sorted by whichever heals the most or deals the most damage, the armor effectiveness of the main player is also taken into account, however DOT damge is not.

boolean doesPlayerHaveHeal(Player player) // checks if the input player has any heal items

boolean shouldBeMerchantRoom() // Returns true if the next room the player will enter should be a merchant room, this is decided by a random chance and if the time since the last merchant room has been anywhere from 1-6 rounds (default)

boolean shouldBeBossRoom() // Similar implentation as the Merchant room

void TurnCombat() // This is the main function that handles combat for the current room, This runs via a While statement that runs until the room has no enemies or the player dies, every turn it loops through the npcs in the room sorted by speed and runs the various functions asscoitated

void BossCombat() // This for when the Player encounters a Boss, specialized function meant for just 1 on 1 combat between the Player and the Boss.

void roundOver() // Shared Variable setting and called functions between BossCombat and TurnCombat when the player clears a room/kills the boss

void printOpponents() // Prints each of the enemies in the currentRoom and their health

int DifficultyMod(boolean unCapped) // Used when we want to modify something with difficulty, since it starts a 0, if Uncapped is true, 2 will become 3, if not, 2 will stay 2.

int DifficultyMod() // Calls DifficultyMod(false).
```