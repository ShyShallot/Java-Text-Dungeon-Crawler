## Private Variables

```java
ArrayList<Stun> stuns = new ArrayList<>()
```

## Functions

```java
void checkStuns() // This goes through all the Stuns in the stuns array list and checks if their stun should be over.

boolean isPlayerStunned(Player player) // Checks if that player is currently stunned

void reset() // Resets the Stuns array list

void addPlayer(Player target, int roundStarted, int lasts) // Creates a Stun Object for the input player for the lasts amount of turns

void removePlayer(Player player) // Remvoes a player from a Stun
```

# Stun Class

## Private Variables

```java
Player stunnedPlayer

int started // the round that the player was stunned

int lasts // how many turns the stun lasts
```

## Constructers

```java
Stun(Player stunTarget, int roundStarted, int lasts)
```

## Getters

```java
Player stunnedPlayer() // Returns the Player is stunned

int started() // Returns when the stun was started

int lasts() // Returns how long the stun lasts

int shouldEnd() // Returns just this.started() + this.lasts()
```

## Setters

```java
// There are none
```
