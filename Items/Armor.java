
public class Armor extends Item{
    private int block;
    double[] armorEff = {0.5, 0.8, 0.7, 0.6,1.0,1.0,0.55,0.65}; // Lower Numbers are better protection, higher numbers are worse as this is practically a multiplication table
    public Armor(){
        super("Iron Armor", "Blocks 3 Damage", 12, 2, true);
		this.block = 3;
        addItemtoList(this);
    }

    public Armor(String name,String description,int cost, int durability, int block){
        super(name,description,cost,durability,true);
        this.block = block;
        addItemtoList(this);
    }

    public void useItem(Player player){
        player.setArmor(this);
        return;
    }

    public double armorEffectiveness(int dmgType){
        double dmgMulti = 1.0;
        if(dmgType > this.armorEff.length-1){
            return 1.0;
        }
        dmgMulti = this.armorEff[dmgType];
        return dmgMulti;
    }

    public int damageBlocked(int incomingDamage, int dmgType){
        int damageBlocked = ((int)((double)incomingDamage*(armorEffectiveness(dmgType))+0.5)) - CusLib.randomNum(0,(int)(this.block*armorEffectiveness(dmgType)));
        if(damageBlocked < 0){
            damageBlocked = 0;
        }
        return damageBlocked;
    }

    public int getBlock(){
        return this.block;
    }

    public void setArmorEffectiveness(double[] armorEff){
        this.armorEff = armorEff;
    }

    public void setArmorEffectiveness(double typeEff, int dmgType){
        if(dmgType > this.armorEff.length){
            System.out.println("Armor Effectiveness Index is out of bounds");
            return;
        }
        this.armorEff[dmgType] = typeEff;
    }
}
