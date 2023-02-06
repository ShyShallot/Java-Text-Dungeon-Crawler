import java.util.*;
public class StunList {
    private ArrayList<Stun> stuns = new ArrayList<>();

    public void checkStuns(){
        for(int i=0;i<stuns.size();i++){
            Stun stunnedPlayer = stuns.get(i);
            if(stunnedPlayer.shouldEnd() >= Game.currentSubRound){
                stuns.remove(i);
            }
        }
    }

    public boolean isPlayerStunned(Player player){
        if(stuns.size() == 0){
            return false;
        }
        boolean isTrue = false;
        for(Stun stunned : stuns){
            if(stunned.stunnedPlayer().getID() == player.getID()){
                isTrue = true;
            }
        }
        return isTrue;
    }

    public void reset(){
        stuns.clear();
    }

    public void addPlayer(Player target, int roundStarted, int lasts){
        stuns.add(new Stun(target, roundStarted, lasts));
    }

    public void removePlayer(Player player){
        for(int i=0;i<stuns.size();i++){
            if(stuns.get(i).stunnedPlayer().getID() == player.getID()){
                stuns.remove(i);
            }
        }
    }
}

class Stun {
    private Player stunnedPlayer;
    private int started;
    private int lasts;

    public Stun(Player stunTarget, int roundStarted, int lasts){
        this.stunnedPlayer = stunTarget;
        this.started = roundStarted;
        this.lasts = lasts;

    }

    public Player stunnedPlayer(){
        return this.stunnedPlayer;
    }

    public int started(){
        return this.started;
    }

    public int lasts(){
        return this.lasts;
    }

    public int shouldEnd(){
        return this.started+this.lasts;
    }

}