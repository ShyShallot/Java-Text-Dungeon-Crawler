public class Skill {
    private int level;
    private String name;
    private int powerIncrease = 0;
    private int speedIncrease = 0;
    private int lvlRequired;
    private Type typeRestrict = null;

    public Skill(int level, String name, int increase, int lvlRequired, Type restrict, boolean powerOrSpeed){
        this.level = level;
        this.name = name;
        if(powerOrSpeed){
            this.powerIncrease = increase;
        } else {
            this.speedIncrease = increase;
        }
        this.lvlRequired = lvlRequired;
        this.typeRestrict = restrict;
    }

    public Skill(int level, String name, int increase, int lvlRequired, boolean powerOrSpeed){
        this.level = level;
        this.name = name;
        if(powerOrSpeed){
            this.powerIncrease = increase;
        } else {
            this.speedIncrease = increase;
        }
        this.lvlRequired = lvlRequired;
    }

    public Skill(int level, String name, int lvlRequired, Type restrict){
        this.level = level;
        this.name = name;
        this.lvlRequired = lvlRequired;
        this.typeRestrict = restrict;
    }

    public Skill(int level, String name, int lvlRequired){
        this.level = level;
        this.name = name;
        this.lvlRequired = lvlRequired;
    }

    public boolean canLearnSkill(Player player){
        if(this.typeRestrict == null){
            return true;
        }
        if(player.getType().getName() == this.typeRestrict.getName()){
            if(player.level() >= this.lvlRequired){
                return true;
            }
        }
        return false;
    }

    public int getLevel(){
        return this.level;
    }

    public String name(){
        return this.name;
    }

    public int powerIncrease(){
        return this.powerIncrease;
    }

    public int speedIncrease(){
        return this.speedIncrease;
    }

    public int lvlRequired(){
        return this.lvlRequired;
    }

    public Type typeRequirement(){
        return this.typeRestrict;
    }
}
