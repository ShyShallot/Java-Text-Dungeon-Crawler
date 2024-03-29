----

Functions as the basis for the [[Player]] class and uses the [[Base Properties]] Class as storage for base stats for the type. Purely just for information the base health and max health, what item and armor the player/npc should have by default. 

## Private Variables

```java
String typeName

Item mainWeapon

Armor mainArmor

ArrayList<Item> prohibitedWeapons

BaseProperties baseStats
```

## Public Static Variables

```java
ArrayList<Type> types = new ArrayList<>(); // contains the list of created types
```
---

## Constructers

```java
Type(String name, int health, int maxHealth, int speed, int mana, Item mainWeapon)

Type(String name, int health, int speed, int mana, Item mainWeapon) // MaxHealth is set to the health

Type(String name, int health, int maxHealth, int speed, int mana, Item mainWeapon, Armor mainArmor)

Type(String name, int health, int speed, int mana, Item mainWeapon, Armor, mainArmor)

Type(String name, int health, int maxHealth, int speed, int mana)

Type(String name, int health, int speed, int mana)

Type()
```

---

## Getters

```java
Armor getMainArmor()

String getName()

Item getMainWeapon()

ArrayList<Item> getIllegalItems()

BaseProperties getStatProps()

int baseHealth()

int baseMaxHealth()

int baseSpeed()

int baseMana()

```

---

## Setters

```java
void setMainWeapon(Item weap) // does not get set if input weapon is a heal item

void setMainWeapon() // sets Main Weapon to null

void setMainArmor(Armor amor)

void setMainArmor() // sets Main Armor to null

void setBaseHealth(int health, int maxHealth)

void setBaseHealth(int health) // sets both health and max health to input

void setBaseSpeed(int speed)

void setBaseMana(int mana)
```

---

## Type Functions

```java
void addType(Type type) throws DuplicateTypeException // Only called in the constructer for the type class, throws an error if a duplicate type is constructed

void addIllegalWeapon(Item weap)

void removeIllegalWeapon(Item weap) // loops through till it finds an item with the same name and removed it

void removeIllegalWepaon(String weap) 

void removeIllegalWeapon(int index)

boolean isItemIllegal(Item item) // returns true if the input is in the prohibited weapons list
```