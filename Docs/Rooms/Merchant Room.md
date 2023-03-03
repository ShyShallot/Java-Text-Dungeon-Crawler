
Extends [[Room]] but doensn't use any of its functions, purely just so that we use one var for the Current Room

## Public Variables

```java
ArrayList<Item> availItems // A list of items that the Player is able to buy
```

## Constructers 

```java
// There is none
```

## Getters

``` java
// There is none
```

## Setters

```java
// There is none
```

## Merchant Room Functions

```java
void createAvailItems() // Goes through every item in Game.itemList and that item as a 45% chance of being added to the list, if no items get added a single Health Vial is added

void itemShop(boolean recall) // This handles listing the items that the Player can purches as well as the purchasing of the items, the boolean recall if true wont reprint all the items, only true if the player misstypes an item name and gives them a seccond chance. Player can type none to exit the item shop
```