import java.util.*;
public class Room {
	PlayerList activePlayers = new PlayerList();
	
    public Room(){
		activePlayers.addPlayer(Game.mainPlayer);
		
	}

	public void fillRoom(){
		int max = 3 + Game.difficulty;
		int size = randomInt(1,max);
		for(int i=0; i<=size;i++){
			AI npc = createRandomNPC();
			activePlayers.addPlayer(npc);
		}
	}
	
	public AI createRandomNPC(){
    	String[] names = {"Goblin","Skeleton","Hog","Spider","Mage"};
    	String name = names[randomInt(0, names.length-1)];
    	AI npc = new AI(name);
    	npc.health *= Game.currentRound*(1+((Game.difficulty+1)/10));
		npc.coins = randomInt(0,(5*Game.difficulty+1));
    	if(npc.health > 500){
        	npc.health = 500;
    	}
    	return npc;
	}

	public int randomInt(int min, int max) {
    	return (int) Math.floor(Math.random() * (max - min + 1) + min);
	}

}

