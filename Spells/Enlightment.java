public class Enlightment extends Spell{
    int healsFor = 2;
    public Enlightment(){
        super("Enlightment", 12, 5, 0, null, null);
    }
    
    @Override
    public void spellHeal(Player user){
        user.Heal(this.heal());
        Game.DOTList.addHealPlayer(user, this.heal(), this.healsFor, Game.currentSubRound);
        System.out.println(String.format("%s reached Enlightment and heals for %s for %s rounds",user.getName(),this.heal(),this.healsFor));
    }
}
