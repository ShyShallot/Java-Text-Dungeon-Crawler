Damage Over Time handles damage that happens over multiple turns, aka poison, bleeding and fire damage and so on. All Players affected by DOT are contained in a DOTMatrix which contains all the variables needed

## Private Variables

```java
ArrayList<DOTMatrix> players
```

## Constructers

```java
// There are no Constructers however the DOT still needs to be created before it will work
```

## Getters

```java
int size() // Gets size of the players array
```

## DOT Functions

```java
void dealOutDamage(int currentRound) // This handles the Damage part, it loops through all the DOTMatrixs assuming there is any, and checks if the damage is to no longer be applied it removes the player, otherwise will deal the damage defined in the DOTMatrix

void addPlayer(Player target, int damage, int damageType, int rounds, int currentRound) // rounds is the amount of time that damage is to last, if the Player is already in a DOTMatrix with that damage type the function stops and the player isnt added however a player can have a DOTMatrix for Poison and Bleed but not Poison and Poison

boolean isPlayerInForDamageType(Player player, int damageType) // returns true if it could find a Player in a DOTMatrix with that damageType

void reset() // clears the players array
```


# DOTMatrix

## Private Variables

```java
Player target // The Player receving the damage
int damage // the amount of damage to deal
int damageType // the damage type
int roundsDOTApplies // how many turns the damage should last
int started // what round the damage started
```

## Constructers

```java
DOTMatrix(Player target, int damage, int damageType, int rounds, int started)
```

## Getters

```java
Player target() // returns the target of the damage

int damage() // returns the amount of damage to deal

int damageType() // returns the damage type

int time() // returns the amount of turns the damage lasts

int started() // returns what round the damage started on

int shouldEnd() // returns the Turn the damage should end on (started+lasts)
```