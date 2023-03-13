import java.util.*;
class AIList {
	ArrayList<AI> npcs = new ArrayList<>();

	public void GarbageCleanup(){
		for(int i=0;i<npcs.size();i++){
				if(npcs.get(i).isDead()){
					npcs.remove(i);
				}
		}
	}

	public void addPlayer(AI npc){
		npcs.add(npc);
	}

	public void renameDupes(){
		for(int i=0;i<npcs.size();i++){
			AI curNPC = npcs.get(i);
			CusLib.DebugOutputLn(("Current NPC: " + curNPC.getName() + " ID: " + curNPC.getID()));
			int dupeNPCs = 1;
			for(int y=0;y<npcs.size();y++){
				AI npcToChange = npcs.get(y);
				if(npcToChange.getID() == curNPC.getID()){
					continue;
				}
				CusLib.DebugOutputLn("NPC to Change: " + curNPC.getName() + " ID: " + curNPC.getID());
				if(npcToChange.getName() == curNPC.getName()){
					String curName = npcToChange.getName();
					npcToChange.setName(curName += " "+ dupeNPCs);
					dupeNPCs++;
					CusLib.DebugOutputLn("NPC "+ npcToChange.getID() + " New Name " + npcToChange.getName());
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
 
	public void sortBySpeed(){
		Collections.sort(npcs, new Comparator<AI>() {
			public int compare(AI lhs, AI rhs){
				return lhs.getType().baseSpeed() > rhs.getType().baseSpeed() ? -1 : (lhs.getType().baseSpeed() < rhs.getType().baseSpeed()) ? 1 : 0;
			}
		});
	}
	

}