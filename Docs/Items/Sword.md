Extends [[Item]]
## Private Variables

```java
// There are none
```

## Constructers

```java
Sword() // calls super("Sword","Deals 12 dmg points", 7, 0, 12, 0, 3, false, false)

Sword(String name, String description, int dmg ,int cost, int durability, int dmgType) // Used for classes extending sword
```

## Getters
```java
// There are none
```

## Setters
```java
// There are None
```

## Sword Functions

```java
void useItem([[Player]] player, Player target) // Basic String output, then calls damagePlayer(player, target)

void damagePlayer(Player, user, Player target) // creates damage variable that is a random value between 5 below the base sword damage and the sword damage, then calls target.Damage(damage, this.getDamageType, user)
```
