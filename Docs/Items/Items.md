A General Class that handles functionality with [[Item]]s

## Damage Types

0. Physical
1. Magic
2. Fire
3. Water
4. Poison
5. Bleed
6. Explosion
7. Ranged

## Static Functions

```java
boolean doesPlayerHaveItem(Player player, Item item) // Loops through a Players inventory and checks if a player has an item

boolean doesPlayerHaveItem(Player player, String item)

Item getItemFromName(String name) // returns an item from a name using the Game.itemList

Item getItemFromName(String name Player player) // same as above but looks through a players inventory instead

String getDamageTypeName(int dmgType) // Converts DmgType index to a String
```