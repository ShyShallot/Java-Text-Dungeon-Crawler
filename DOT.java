import java.util.*;
public class DOT {
    private ArrayList<DOTMatrix> players = new ArrayList<>();

    public void dealOutDamage(int currentRound){
        if(players.size() == 0){
            return;
        }
        for(int i=0;i<players.size();i++){
            DOTMatrix currentMatrix = players.get(i);
            if((currentMatrix.started()+currentMatrix.time()) >= currentRound){
                players.remove(i);
                continue;
            }
            currentMatrix.target().Damage(currentMatrix.damage(), currentMatrix.damageType(),null);
        }
    }

    public void addPlayer(Player target, int damage, int damageType, int rounds, int currentRound){
        if(isPlayerInForDamageType(target, damageType)){
            return;
        }
        DOTMatrix playerDOT = new DOTMatrix(target, damage, damageType, rounds, currentRound);
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

}