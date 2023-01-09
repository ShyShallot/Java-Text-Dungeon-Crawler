import java.util.*;
class AIList {
	ArrayList<AI> npcs = new ArrayList<>();

	public void GarbageCleanup(){
		for(int i=0;i<npcs.size();i++){
				if(npcs.get(i).dead){
					npcs.remove(i);
				}
		}
	}

	public void addPlayer(AI npc){
		npc.id = npcs.size();
		npcs.add(npc);
	}

	public void renameDupes(){
		for(int i=0;i<npcs.size();i++){
			AI curNPC = npcs.get(i);
			//System.out.println("Current NPC: " + curNPC.name + " ID: " + curNPC.id);
			int dupeNPCs = 1;
			for(int y=0;y<npcs.size();y++){
				AI npcToChange = npcs.get(y);
				if(npcToChange.id == curNPC.id){
					continue;
				}
				//System.out.println("NPC to Change: " + curNPC.name + " ID: " + curNPC.id);
				if(npcToChange.name == curNPC.name){
					npcToChange.name += (" "+ dupeNPCs);
					dupeNPCs++;
					//System.out.println("NPC "+ npcToChange.id + " New Name " + npcToChange.name);
				}
			}
		}
	}

	public int size(){
		return npcs.size();
	}

	public AI get(int index){
		return npcs.get(index);
	}

}