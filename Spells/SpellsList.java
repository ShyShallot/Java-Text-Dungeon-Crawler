import java.util.ArrayList;

import java.util.*;
public class SpellsList {
    private static ArrayList<Spell> spells = new ArrayList<>(Arrays.asList(new Jet(), new Lightning(), new Enlightment(), new Orb()));

    public static ArrayList<Spell> getSpells(){
        return spells;
    }
}
