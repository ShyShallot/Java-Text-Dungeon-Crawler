public class Armor extends Item{
    int block;
    public Armor(){
        block = 8;
        name = "Iron Armor";
        dmg = 0;
        description = "Blocks " + this.block + " Damage points";
        cost = 12;
        durability = 1;
        curDurability = durability;
        isArmor = true;
        unUsable = false;
        healthItem = false;
        heal = 0;
    }

    public void useItem(Player player){
        return;
    }
}
