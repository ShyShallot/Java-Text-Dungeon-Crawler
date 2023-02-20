Extends [[Item]]

This is mainly an example file, but uses the Bite.java as a base, anything that calls DOT (Damage Over Time) extends Bite. There are 2 Damage Types, the damage type used when the User uses the item, and the Damage type when adding a Player to the DOT list

## Private Variables

```java
int poisonDamage // how much damage the poison does, this is not the amount of base damage the weapon does

int poisonLasts // How many turns the poison lasts

double poisonChance // the chance that the poison has to affect the target

String damageString // the string used when outputting on weapon use
```

## Constructers

```java
Bite() // super("Bite", "A Poisonous Bite", 0, 6, 0, -1), this.poisonDamage = 4; this.poisonLasts = 2; this.poisonChance = 0.3; this.damageString = " has bittin ";

Bite(String name, String description, int damage, int damageType, int poisonDamage, int poisonLasts, double poisonChance, String damageString) 
```

## Getters

```java
int poisonDamage() // returns this.poisonDamage

int poisonLasts() // returns this.poisonLasts

String damageString() // returns this.damageString
```

## Setters

```java
void setDamageString(String dmgString)
```

## DOT Functions

```java
useItem(Player user, Player target) // Deals normal damage to player but if Math.random() < the poison chance, the Target is added the Games DOT List. Game.DOTList.addPlayer(target, <the damage to deal each turn>, <the damage type, 4 in this case, with that being poison>, <the amount of turns it lasts>, <The Current Round>)
```