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