import java.util.*;
public class CastSpellList {
    private ArrayList<SpellCast> casters = new ArrayList<>();

    public void castMultiTurnSpell(Player player, Player target, int curRound, int lasts){
        if(player.getType().getName() != "Mage"){
            return;
        }
        casters.add(new SpellCast(player, target, curRound, lasts));
    }

    public void castMultiTurnSpell(Player player, int curRound, int lasts){
        if(player.getType().getName() != "Mage"){
            return;
        }
        casters.add(new SpellCast(player, curRound, lasts));
    }

    public void castSpells(){
        for(int i=0;i<casters.size();i++){
            SpellCast spellCasts = casters.get(i);
            if(spellCasts.playerCasting().getMana() < spellCasts.playerCasting().getSpell().castCost()){
                casters.remove(i);
                System.out.println(String.format("%s ran out of mana while casting %s", spellCasts.playerCasting().getName(), spellCasts.playerCasting().getSpell().castCost()));
            }
            //System.out.println(spellCasts.started() + spellCasts.turns() + ", " + Game.currentSubRound);
            if(spellCasts.started()+spellCasts.turns() <= Game.currentSubRound){
                Staff hand = (Staff)spellCasts.playerCasting().getHand();
                hand.useItem(spellCasts.playerCasting(), spellCasts.spellTarget(),false);
                casters.remove(i);
            } else {
                spellCasts.playerCasting().removeMana(spellCasts.playerCasting().getSpell().castCost());
            }
        }
    }

    public boolean isPlayerCastingSpell(Player player){
        boolean isTrue = false;
        for(int i=0;i<casters.size();i++){
            SpellCast cast = casters.get(i);
            if(cast.playerCasting().getID() == player.getID()){
                isTrue = true;
            }
        }
        return isTrue;
    }

}

class SpellCast {
    private Player caster;
    private Player target;
    private int started;
    private int turns;

    public SpellCast(Player caster, Player target, int curRound, int turns){
        this.caster = caster;
        this.target = target;
        this.started = curRound;
        this.turns = turns; 
    }

    public SpellCast(Player caster, int curRound, int turns){
        this.caster = caster;
        this.target = null;
        this.started = curRound;
        this.turns = turns; 
    }

    public Player playerCasting(){
        return this.caster;
    }

    public Player spellTarget(){
        return this.target;
    }

    public int started(){
        return this.started;
    }

    public int turns(){
        return this.turns;
    }
}