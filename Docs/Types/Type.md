
----

Functions as the basis for the [[Player]] class and uses the [[Base Properties]] Class as storage for base stats for the type

## Private Variables

```java
String typeName

Item mainWeapon

Armor mainArmor

ArrayList<Item> prohibitedWeapons

BaseProperties baseStats
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
void addIllegalWeapon(Item weap)

void removeIllegalWeapon(Item weap) // loops through till it finds an item with the same name and removed it

void removeIllegalWepaon(String weap) 

void removeIllegalWeapon(int index)

boolean isItemIllegal(Item item) // returns true if the input is in the prohibited weapons list
```