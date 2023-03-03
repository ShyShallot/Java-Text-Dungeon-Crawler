Extends [[Room]]

## Private Variables

```java
Boss boss; // The Boss the Player will fight
ArrayList<Item> rewards = new ArrayList<>(); // An array of items to give to the player when defeating the boss
```

## Constructers 

```java
BossRoom();
```

## Getters

``` java
Boss boss(); // Returns the boss
```

## Setters

```java
// There is none
```

## Boss Room Functions

```java
private void createRandomBoss(); // Created a Random boss of the available Boss Types provided in an Array, gives him health based off his type multiplied by the game difficulty capped.

private void treasure(); // Populates the rewards ArrayList with items from the Game.itemList at random

public void rewardPlayer(); // Gives the player the items in the rewards array when they defeat the boss
```