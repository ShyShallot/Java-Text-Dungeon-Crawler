## Private Variables

```java
String name 

String description

int cost // The Amount of Coins needed to Purchase the item from a Merchant Room

int manaCost // Amount of Mana needed to use this weapon, sort of decaprecated as Mana Costs are now handled by indivdual spells in current uses

int dmg // Amount of Base Damage dealt by the Weapon

int dmgType // The Type of Damage the Weapon Deals, To Understand this more Read the Items class

int durability // Max Durability of an Item

int curDurability // Current Durability, if a constructer does not set curDurability it defaults to the max durability

boolean unUsable // If true an item cant be used, defaults to false in all constructers

boolean isArmor // Since Armor use Items a Base we need to know if its armor or not

boolean healthItem // true if it heals, false if not
```

## Constructers

```java
Item(String name, String description, int cost, int mana, int dmg, int dmgType, int durability, boolean isArmor, boolean isHeal)

Item(String name, String description, int cost, int dmg, int dmgType, int durability) // isArmor and healthItem set to false, and manaCost = 0

Item(String name, String description, int cost, int durability, boolean isArmor) // if isArmor is true, healthItem is set to false, manaCost is set to 0

```

## Getters

```java
String resolveDamageType() // Runs Items.getDamageTypeName(this.dmgType)

String getName() 

String getDescription()

int getCost()

int getManaCost()

int getDamage()

int getDamageType() // returns this.dmgType

int durability() // returns this.curDurability not this.durability

int getMaxDurability() // returns this.durability

boolean isArmor()

boolean isUseable()

boolean isHeal()

```

## Setters

```java
void setUseState(boolean unUseable)

void setDurability(int durability) // ads the durability, so if u want to remove use a negative
```

## Item Functions

```java
void useItem(Player player) // in the base item class this does nothing but all items have this function as the basic caller

void useItem(Player player, Player npc)
```