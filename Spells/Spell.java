public class Spell {
    private String name;
    private int castCost;
    private int damage;
    private int dmgType;
    private int heal;
    private int turnsToCast;
    private Skill skillReq;
    private Item requiredStaff;
    private int requiredLevel = 0;

    public Spell(String name, int cast, int DoH, int dmgType ,int turns, Skill skill, Item staff, boolean isHeal){
        this.name = name;
        this.castCost = cast;
        if(isHeal){
            this.heal = DoH;
        } else {
            this.damage = DoH;
            this.dmgType = dmgType;
        }
        this.turnsToCast = turns;
        this.skillReq = skill;
        this.requiredStaff = staff;
    }

    public Spell(String name, int cast, int DoH, int dmgType ,int turns, int requiredLevel, Item staff, boolean isHeal){
        this.name = name;
        this.castCost = cast;
        if(isHeal){
            this.heal = DoH;
        } else {
            this.damage = DoH;
            this.dmgType = dmgType;
        }
        this.turnsToCast = turns;
        this.skillReq = null;
        this.requiredLevel = requiredLevel;
        this.requiredStaff = staff;
    }

    public Spell(String name, int cast, int heal, int turns, Skill skill, Item staff){
        this.name = name;
        this.castCost = cast;
        this.heal = heal;
        this.turnsToCast = turns;
        this.skillReq = skill;
        this.requiredStaff = staff;
    }

    public Spell(String name, int cast, int heal, int turns, int levelReq, Item staff){
        this.name = name;
        this.castCost = cast;
        this.heal = heal;
        this.turnsToCast = turns;
        this.requiredLevel = levelReq;
        this.requiredStaff = staff;
    }

    public Spell(){

    }

    public int levelReq(){
        return this.requiredLevel;
    }

    public int castCost(){
        return this.castCost;
    }

    public int damage(){
        return this.damage;
    }

    public int damageType(){
        return this.dmgType;
    }

    public int heal(){
        return this.heal;
    }

    public int turnsToCast(){
        return this.turnsToCast;
    }

    public Skill skillReq(){
        return this.skillReq;
    }

    public Item staffReq(){
        return this.requiredStaff;
    }

    public String name(){
        return this.name;
    }

    public boolean canPlayerCast(Player player){
        if(this.skillReq == null){
            return true;
        }
        if(!player.hasSkill(skillReq)){
            return false;
        }
        if(player.level() <= this.requiredLevel){
            return false;
        }
        if(this.requiredStaff != null){
            if(!Items.doesPlayerHaveItem(player, requiredStaff)){
                return false;
            }
        }
        return true;
    }

    public void castSpell(Player user, Player Target, boolean initCast){
        if(this.skillReq != null){
            if(!user.hasSkill(skillReq)){
                if(user.getName() == "You"){
                    CusLib.queueText("You don't have knowladge on how to cast that spell.");
                }
                return;
            }
        }
        if(this.turnsToCast() > 0 && initCast){
            CusLib.queueText(String.format("%s started casting %s for %s turns",user.getName(),this.name,this.turnsToCast));
            Game.castList.castMultiTurnSpell(user, Target, Game.currentSubRound, this.turnsToCast);
            return;
        }
        if(this.heal != 0){
            CusLib.queueText(String.format("%s casted %s and healed for %s",user.getName(),this.name,this.heal));
            this.spellHeal(user);
            user.removeMana(castCost);
        } else {
            CusLib.queueText(String.format("%s casted %s at %s and dealt %s dmg!",user.getName(),this.name,Target.getName(),CusLib.colorText(this.damage, "red")));
            this.spellDamage(user, Target);
            user.removeMana(castCost);
        }
    }

    public void spellDamage(Player user, Player Target){
        Target.Damage(this.damage,this.dmgType,user);
    }

    public void spellHeal(Player user){
        user.Heal(this.heal);
    }

    public String toString(){
        if(this.heal > 0){
            return String.format("%s, Heal %s, Turns to Cast %s, Mana Cost %s", this.name, this.heal, this.turnsToCast, this.castCost);
        } else{
            return String.format("%s, Damage %s, Damage Type %s, Turns to Cast %s, Mana Cost %s", this.name, this.damage, this.dmgType, this.turnsToCast, this.castCost);
        }
        
    }

}
