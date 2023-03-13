Extends [[AI]]

This is just so that we can introduce secondary attacks and Attack Patterns

## Private Variables

```java
item secondaryAttack // The Item to use as a secondary
int curAttackIndex // keep track of which attack we should use
BossType bossType;
```

## Constructers

```java
Boss(String name, BossType type, int health, int maxHealth, Item secondaryAttack) // calls super(name, type)

Boss(String name, BossType type, int health, Item Secondary Attack) // same as the first constructer but maxHealth is set to Health
```

## Getters 

```java
Item getSecondaryAttack();

int[] getAttackPattern(); // returns this.bossType.getAttackPattern();
```

## Setters
```java
void setAttackPattern(int[] arr) // Calls this.bossType.setAttackPattern(arr);

```

## Boss Functions
```java
boolean shouldUseSecondary() // look at attackPattern in the Private Variables Section but also if it is true, it runs this.usedSecondary()

void usedSecondary() // Increases this.curAttackIndex and if this.curAttackIndex > this.attackPattern.length it resets to 0

void prepareAttack() // Basic function that either allows the Boss to defend or figure out if he is supposed to use his secondary or primary attack
```
``