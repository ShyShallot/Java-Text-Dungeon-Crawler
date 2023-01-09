public class Armor extends Item{
    int block;
    public Armor(){
        super("Iron Armor", "Blocks 8 Damage", 12, 0, 2, true, false);
		this.block = 8;
    }

    public void useItem(Player player){
        player.Armor = this;
        return;
    }
}
