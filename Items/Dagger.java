public class Dagger extends Bite{
    public Dagger(){
        super("Dagger", "A Small Dagger that deals Bleed", 3, 5, 2, 3, " Has Stabbed ");
    }

    public void useItem(Player user, Player target){
        System.out.println(user.getName() + this.damageString() + target.getName() + "!");
        target.Damage(this.getDamage(), this.getDamageType(),user);
        Game.DOTList.addPlayer(target, this.poisonDamage(), this.getDamageType(), this.poisonLasts(), Game.currentSubRound);
    } 
}
