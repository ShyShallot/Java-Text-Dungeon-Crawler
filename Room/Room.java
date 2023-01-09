import java.util.*;
public class Room {
	AIList activeNPCS = new AIList();
	
    public Room(){
		fillRoom();
	}

	public void fillRoom(){
		int max = 3 + Game.difficulty;
		int size = randomInt(1,max);
		for(int i=0; i<=size;i++){
			AI npc = createRandomNPC();
			activeNPCS.addPlayer(npc);
		}
	}
	
	public AI createRandomNPC(){
    	String[] names = {"Goblin","Skeleton","Hog","Spider","Mage"};
    	String name = names[randomInt(0, names.length-1)];
    	AI npc = new AI(name);
    	npc.health = CusMath.randomNum(10, 50) * (Game.currentRound*(Game.difficulty/10)+1);
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

