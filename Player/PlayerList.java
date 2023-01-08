import java.util.*;
class PlayerList {
	ArrayList<Player> players = new ArrayList<>();

	public void GarbageCleanup(){
		for(int i=0;i<players.size();i++){
				if(players.get(i).dead){
					players.remove(i);
				}
		}
	}

	public void addPlayer(Player player){
		players.add(player);
	}

	public int size(){
		return players.size();
	}

}