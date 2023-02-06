import java.util.*;;
public class Spells {
    public static Spell spellFromString(String name){
        Spell returnSpell = null;
        ArrayList<Spell> spells = SpellsList.getSpells();
        for(int i=0;i<spells.size();i++){
            if(spells.get(i).name().toLowerCase().equals(name.toLowerCase())){
                returnSpell = spells.get(i);
            }
        }
        return returnSpell;
    }
}
