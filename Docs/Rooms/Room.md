The Room Class makes use of the [[AIList]] Class

## Public Variables

```java
AIList activeNPCS

HashMap<String, int[]> typeHealthMap // this is an Empty hashmap until the Room Constructer is called, this holds the max Health for Each type based off of difficulty
```

## Constructers

```java
Room() // Creates Entries for the typeHealthMap HashMap, and then calls FillRoom()
```

## Getters

```java
// There are none
```

## Setters 

```java
// There are none
```

## Room Functions

```java
void fillRoom() // Creates a random amount of NPCs to put into the activeNPCS list by calling creatRandomNPC and then sorting by speed when the room is filled

AI createRandomNPC() // Creates an NPC of a random type with health based off of the typeHealthMap by callling calculateHealthValues(npc) and random Mana and Coins and gives it a random inventory

void fillNPCInventory(AI npc) // A function that gives an npc various items assuming that their type allows that item

double healItemChance(AI npc) // returns a value between 0 and 1 that decides if an NPC will recieve a heal item based off of type, called by fillNPCInventory

void calculateHealthValues(AI npc) // Uses the typeHealthMap and using its type it gets the difficulty (0-2) and uses that as the index of the max value to use when calling CusLib.randomNum and by default uses 5 as the min for easy, but if its medium or harder the previous difficulty max is used as the min

void treasureChest() // When the Player clears the room there is a chance that a treasure chest will spawn (this is determined in the Game class) and this handles on what is in there as well as if its a mimic or not
```