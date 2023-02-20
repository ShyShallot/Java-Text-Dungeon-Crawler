Extends [[Player]]
This is the Class that we use to make AI players, this just extends the Player Class and includes AI specific functionality

## Constructers

```java
public AI(String name, Type type) // Only runs super(name, type)
```

## Private Vars

```java
// There are none
```

## Getters

```java
// There are none
```

## Setters

```java
// There are none
```

## AI Functions

```java
void shouldDefend(boolean isPlayerHoldingGround) // This checks if the AI should defend, it looks at its health percentage and by using a double[][] "Matrix" it decides the probability it would want to defend, it also interpolates that probability between a min max. If the isPlayerHoldingGround == true, the AI wont defend. It will also not defend if it last Defended less than 2 Rounds Ago

void AIAction() // this only calls the shouldDefend function, however could be expanded if other functions want to be called

double[] itemDecideWeightTable() // This returns a 2 length double array that contains the probability of the AI using a Damage Item or a Heal Item by using its Health percentage and comparing to a double[][] Matrix and interpolating those percentages

Spell decideSpell(Player opponent, boolean shouldHeal) // Decides which spell it should cast based off of multiple checks, but the first one is if shouldHeal is true then it will use a Heal spell. It checks for which one Heals/Deals more Damage (Including Armor Effectiveness), Which one takes the shortest to cast and also has a 50% of choosing that spell


```

# Interpolation Explanation

Let's say that the AI's Health Percentage is 45%, and we want to see if it should defend.

## Our Table
| Health Percent | Chance |
| -------------- | ------ |
| 100            | 0.0    |
| 50             | 0.15   |
| 35             | 0.25   |
| 25             | 0.5    |
| 10             | 0.75   |
| 0.0            | 1.0       |

45% would be between 50% and 35%, So our Defend chance Minimum is 15% and the Maximum is 25%, and 45% is nearly in-between 50% and 35% the Final Interpolated Defend Chance is about 20%. 

But if it was not nearly in between and lets say our Health Percentage is 60% than it would be around 5-10%.