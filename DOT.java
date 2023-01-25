import java.util.*;
public class DOT {
    private ArrayList<DOTMatrix> players = new ArrayList<>();

    public void dealOutDamage(int currentRound){
        //System.out.println("Dealing out DOT Damage for Round " + currentRound);
        if(players.size() == 0){
            //System.out.println("Array is Empty");
            return;
        }
        for(int i=0;i<players.size();i++){
            DOTMatrix currentMatrix = players.get(i);
            //System.out.println(currentMatrix.toString());
            if((currentRound > currentMatrix.shouldEnd())){
                System.out.println(String.format("%s no longer feel the effects of %s", currentMatrix.target().getName(), Items.getDamageTypeName(currentMatrix.damageType())));
                players.remove(i);
                continue;
            }
            currentMatrix.target().Damage(currentMatrix.damage(), currentMatrix.damageType(),null);
        }
    }

    public void addPlayer(Player target, int damage, int damageType, int rounds, int currentRound){
        //System.out.println("Adding Player " + target.getName() + " to Damage Over Time");
        if(target.getName() == "You"){
            System.out.println(String.format("%s are now under the affects of %s", target.getName(),Items.getDamageTypeName(damageType)));
        } else {
            System.out.println(String.format("%s is now under the affects of %s", target.getName(),Items.getDamageTypeName(damageType)));
        } 
        if(isPlayerInForDamageType(target, damageType)){
            return;
        }
        DOTMatrix playerDOT = new DOTMatrix(target, damage, damageType, rounds, currentRound);
        //System.out.println(playerDOT.toString());
        players.add(playerDOT);
        return;
    }

    public int size(){
        return players.size();
    }

    public boolean isPlayerInForDamageType(Player player, int damageType){
        if(players.size() == 0){
            return false;
        }
        boolean isTrue = false;
        for(int i=0;i<players.size();i++){
            DOTMatrix cur = players.get(i);
            if(cur.target().getID() == player.getID()){
                if(cur.damageType() == damageType){
                    isTrue = true;
                }
            }
        }
        return isTrue;
    }

    public void reset(){
        players.clear();
    }
}


class DOTMatrix{
    private Player target;
    private int damage;
    private int damageType;
    private int roundsDOTApplies;
    private int started;

    DOTMatrix(Player target, int damage, int damageType, int rounds, int started){
        this.target = target;
        this.damage = damage;
        this.damageType = damageType;
        this.roundsDOTApplies = rounds;
        this.started = started;
    }

    public Player target(){
        return this.target;
    }

    public int damage(){
        return this.damage;
    }

    public int damageType(){
        return this.damageType;
    }

    public int time(){
        return this.roundsDOTApplies;
    }

    public int started(){
        return this.started;
    }

    public int shouldEnd(){
        return this.started+this.roundsDOTApplies;
    }

    public String toString(){
        return String.format("Target: %s, Damage: %s, DamageType %s, Time: %s, Started: %s", target.getName(), this.damage, Items.getDamageTypeName(this.damageType),this.roundsDOTApplies,this.started);
    }

}