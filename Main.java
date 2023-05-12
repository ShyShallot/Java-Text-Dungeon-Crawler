import java.util.*;
class Main {
	public static boolean debugMode = false;
	public static GamePanel gp;
	public static Input keyHandler = new Input();
	
  	public static void main(String[] args) {
		BaseFrame ui = new BaseFrame();
		gp = ui.gamePanel;
		ui.frame();
  	}

	public static void Testing(){
		Game game = new Game();
		game.createSkills();
		game.createTypes();
		game.createSpells();
		//SkillsTree.printSkillsTree(SkillsTree.getSkillFromName("Weather 2")); 
		//game.TestWeights();
	}
}