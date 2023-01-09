public class Armor extends Item{
    int block;
    public Armor(){
        block = 8;
        name = "Iron Armor";
        description = "Blocks " + this.block + " Damage points";
        cost = 12;
        durability = 3;
        curDurability = durability;
        isArmor = true;
        unUsable = false;
        healthItem = false;
    }

    public void useItem(Player player){
        player.Armor = this;
        return;
    }
}
