Extends [[Item]]
## Private Variables

```java
int heal // how much the health item heals
```

## Constructers

```java
Vial() // calls super("Health Vial", "Heals you for 8 hp", 5, 4, false)

Vial(int heal, String name, int cost, int manaCost) // calls super(name "heals you for " + heal + " hp",cost,manaCost,false) and sets   this.heal = heal
```

## Getters 

```java
int getHealAmt() // returns this.heal
```

## Setters

```java
// There are none
```

## Vial Functions

```java
void useItem(Player player, Player target) // Target is never really used, but just calls a heal function and removes the Vial from the inventory
```