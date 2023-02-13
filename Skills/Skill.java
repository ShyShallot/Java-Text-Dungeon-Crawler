public class Skill {
    private int level;
    private String name;
    private int powerIncrease = 0;
    private int speedIncrease = 0;
    private int lvlRequired;
    private Type typeRestrict = null;
    private String previousLevel;
    private String nextLevel;

    public Skill(int level, String name, int increase, int lvlRequired, Type restrict, boolean powerOrSpeed, String prevSkill, String nextSkill){
        this.level = level;
        this.name = name;
        if(powerOrSpeed){
            this.powerIncrease = increase;
        } else {
            this.speedIncrease = increase;
        }
        this.lvlRequired = lvlRequired;
        this.typeRestrict = restrict;
        this.previousLevel = prevSkill;
        this.nextLevel = nextSkill;
    }

    public Skill(int level, String name, int increase, int lvlRequired, boolean powerOrSpeed, String prevSkill, String nextSkill){
        this.level = level;
        this.name = name;
        if(powerOrSpeed){
            this.powerIncrease = increase;
        } else {
            this.speedIncrease = increase;
        }
        this.lvlRequired = lvlRequired;
        this.previousLevel = prevSkill;
        this.nextLevel = nextSkill;
    }

    public Skill(int level, String name, int lvlRequired, Type restrict, String prevSkill, String nextSkill){
        this.level = level;
        this.name = name;
        this.lvlRequired = lvlRequired;
        this.typeRestrict = restrict;
        this.previousLevel = prevSkill;
        this.nextLevel = nextSkill;
    }

    public Skill(int level, String name, int lvlRequired, String prevSkill, String nextSkill){
        this.level = level;
        this.name = name;
        this.lvlRequired = lvlRequired;
        this.previousLevel = prevSkill;
        this.nextLevel = nextSkill;
    }

    public Skill(){
        this.name = "";
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

    public String previousSkillLevel(){
        return this.previousLevel;
    }

    public void setPreviousSkillLevel(String skill){
        this.previousLevel = skill;
    }

    public String nextSkillLevel(){
        return this.nextLevel;
    }

    public void setNextSkillLevel(String skill){
        this.nextLevel = skill;
    }
    
    public boolean hasPrevLevel(){
        if(this.previousLevel == null){
            return false;
        }
        return true;
    }

    public boolean hasNextLevel(){
        if(this.nextLevel == null){
            return false;
        }
        return true;
    }

    public String toString(){
        return String.format("%s, Prev Skill %s, Next Skill %s, Power Increase: %s, Speed Increase: %s, Level Required: %s, Type Restrict: %s",CusLib.colorText(this.name,"yellow"),CusLib.colorText(this.previousLevel,"blue"),CusLib.colorText(this.nextLevel,"green"),CusLib.colorText(this.powerIncrease,"red"),CusLib.colorText(this.speedIncrease,"yellow"),CusLib.colorText(this.lvlRequired,"red"),CusLib.colorText(this.typeRestrict.getName(),"purple"));
    }

    public String toStringSimplified(){
        return String.format("%s, Power Increase: %s, Speed Increase: %s, Level Required: %s, Type Restrict: %s",CusLib.colorText(this.name,"yellow"),CusLib.colorText(this.powerIncrease,"red"),CusLib.colorText(this.speedIncrease,"yellow"),CusLib.colorText(this.lvlRequired,"red"),CusLib.colorText(this.typeRestrict.getName(),"purple"));
    }
}
