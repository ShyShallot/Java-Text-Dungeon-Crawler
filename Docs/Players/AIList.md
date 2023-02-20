
This is to keep track of a set of [[AI]], purpose is mainly for the Room Class

## Private Variables
```java
ArrayList<AI> npcs
```

## Constructers
```java
// There is None
```

## Getters
```java
int size() // returns this.npcs.size()

AI get(int index) // Gets an AI Object at the current index
```

## Setters
```java
void addPlayer(AI npc)// adds an AI to this.npcs
```

## AIList Functions
```java
void GarbageCleanup() // Runs through all the npcs in this.npcs and removes them if they are dead

void renameDupes() // Loops through and if any AI share the same name, the dupe is changes to NPC 1, NPC 2 and so on.

void soryBySpeed() // Sorts this.npcs by Speed so that the fastest Npc gets its turn before next fastest.
```