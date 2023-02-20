
## Private Variables

```java
int level // The Level of the Skill, Ex: Weather II is Level 2

String name

int powerIncrease // Increases Players damage

int speedIncrease // Increases Players Speed

int lvlRequired // the level the Player has to be to be able to earn the skill

Type typeRestrict // The Type the player has to be

String previousLevel // The Name of the Previous Skill

String nextLevel // The Name of the Next Skill
```

## Constructers 

```java
Skill(int level, String name, int powerOrSpeedIncrease, int lvlRequired, Type restrict, boolean powerOrSpeed, String prevSkill, String nextSkill) // if true its power that gets set, false is speed

Skill(int level, String name, int powerOrSpeedIncrease, boolean powerOrSpeed, String prevSkill, String nextSkill)

Skill(int level, String name, int lvlRequired, Type restrict, String prevSkill, String nextSkill) // For when we dont want to increase power or speed with this skill

Skill(int level, String name, int lvlRequired, String prevSkill, String nextSkill)

Skill()
```

## Getters

```java
int getLevel() // returns the Skill Level, Not The Level Requirement

String name() 

int powerIncrease()

int speedIncrease()

int lvlRequired()

Type typeRequirement()

String previousSkillLevel()

String nextSkillLevel()
```

## Setters

```java
void setPreviousSkillLevel(String skill)

void setNextSkillLevel(String kill)
```

## Skill Functions

```java
boolean hasPrevSkilllevel()

boolean hasNextSkillLevel()
```