this is similar to the DOT List but is used for Multi Turn Spells. Do note that every turn that a Player is casting a spell Mana is removed based on a spell cost, and mana is also removed when player finally casts the spell

## Private Variables

```java
ArrayList<SpellCast> casters // An ArrayList contains every player casting a multiturn spell
```

## Constructers

```java
// No Constructers but has the same setup as the DOT
```

## Functions

```java
void castMultiTurnSpell(Player player, Player target, int curRound, int lasts) // Creates a SpellCast object for a player, this is the function for a damage spell

void castMultiTurnSpell(Player player, int curRound, int lasts) // Same as above but used for heal spells

void castSpells() // Handles the Main Casting of the Spells, it loops through each SpellCast and checks if its ready to cast the spell, and if so it casts it other wise it doesnt.

boolean isPlayerCastingSpell(Player player) // Checks if a player is casting a spell
```


# SpellCast Class

## Private Variables

```java
Player caster // The Player casting the spell

Player target // The Target of the spell

int started // The Round that the player started casting the spell on

int turns // The Amount of turns it takes to cast the spell
```

## Constructers

```java
SpellCast(Player caster, Player target, int curRound, int turns) // Used for damage spells

SpellCast(Player caster, int curRound, int turns) // Only use this for healing spells or ones without a target, otherwise the program will explode
```

## Getters

```java
Player playerCasting() // returns the Player casting the spell

Player spellTarget() // the Target player of the spell

int started() // Returns the round the casting was started

int turns() // Returns how long it will take to Cast
```