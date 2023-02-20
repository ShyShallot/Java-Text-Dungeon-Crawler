Extends [[Item]]
## Private Variables

```java
int block // Amount of base Damage the Armor Blocks
double[] armorEff // This Array has to match the amount of damage types there are, the dmgType on an item functions as the index for the mulitplier in the arrmorEff array, so if the Sword is Damage type 0 (Physical) the damage of the sword is multipled by armorEff[dmgType].
```

## Constructers

```java
Armor() // Default Armor, calls super("Iron Armor", "Blocks 3 Damage", 12, 2, true) and sets this.block = 3;

Armor(String name, String description, int cost, int durability, int block) // calls super(name, description,cost,durability,true) and sets this.block = block
```

## Getters

```java
int getBlock() // return this.block
```

## Setters

```java
setArmorEffectiveness(double[] armorEff) // overwrites the armorEff array and sets it to the input

setArmorEffectiveness(double typeEff, int dmgType) // sets the multiplier for a damage type to the new one.
```

## Armor Functions

```java
double armorEffectiveness(int dmgType) // returns the multiplier associated with the damage type

int damageBlocked(int incomingDamage, int dmgType) // Returns the incoming damage multplied by the Armor Effectiveness (usually less than one) which is then casted to a rounded up int, and then subtracted by a random number from 0 to the base block value, if the final value is less than 0 it gets set to 0
```