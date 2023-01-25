public class Dagger extends Bite{
    public Dagger(){
        super("Dagger", "A Small Dagger that deals Bleed", 6, 0, 3, 3, 0.4 , " Has Stabbed ");
    }

    public void useItem(Player user, Player target){
        if(user.getName() == "You"){
            this.setDamageString(" Stabbed ");
        }
        System.out.println(user.getName() + this.damageString() + target.getName() + "!");
        target.Damage(this.getDamage(), this.getDamageType(),user);
        Game.DOTList.addPlayer(target, this.poisonDamage(), 5, this.poisonLasts(), Game.currentSubRound);
    } 
}
