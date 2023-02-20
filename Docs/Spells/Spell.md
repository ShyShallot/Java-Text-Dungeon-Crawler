
## Private Variables

```java
String name // The Name of the spell

int castCost // The amount of mana required to cast the spell

int damage // The amount of damage the spell does

int dmgType // the Damage Type of the Spell

int heal // How much the spell heals for

int turnsToCast // How many turns it takes to cast

Skill skillReq // The Skill the player needs to cast the spell

Item requiresStaff // The Staff required to cast the spell

int requiredLevel // The Level the player has to be to cast it
```

## Constructers

```java
Spell(String name, int cast, int DoH, int dmgType, int turns, Skill skill, Item staff, boolean isHeal) // If isHeal is false, the DoH is used as the Damage value otherwise its the heal

Spell(String name, int cast, int DoH, int dmgType, int turns, int requiredLevel, Item staff, boolean isHeal)

Spell(String name, int cast, int heal, int turns, Skill skill, Item Staff)

Spell(String name, int cast, int heal, int turns, int levelReq, Item Staff)

Spell()
```

## Getters

```java
String name() // Returns the name of the spell

int levelReq() // returns the Level Requirement

int castCost() // returns the Mana Cost

int damage() // Returns amt of damage

int damageType() // Returns Damage Type

int heal() // Returns amount of healing spell does

int turnsToCast() // Returns how long it takes to cast

Skill skillReq() // Returns the Skill Requirement

Item staffReq() // Returns the staff Required for the spell
```

## Setters

```java
// There are none
```

## Spell Functions
```java
boolean canPlayerCast(Player player) // Checks if the player meets the various requirements that spell has set

void castSpell(Player user, Player target, boolean initCast) // This is the main function of the spell, which handles the interfacing with CastSpellList, if initCast is false, it means that the CastSpellList casted the spell thus the multi turn cast is done

void spellDamage(Player user, Player Target) // this actually deals the damage to the target

void spellHeal(Player user) // calls the heal function for the player
```