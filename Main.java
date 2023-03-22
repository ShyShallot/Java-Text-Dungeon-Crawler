import java.util.*;
class Main {
	public static boolean debugMode = false;
	
  	public static void main(String[] args) {
		Game();
		//Testing();
  	}

	public static void Game(){
		Scanner input = new Scanner(System.in);
    	Game game = new Game();
		game.SetupInput(input);
		game.Start();
		if(game.over){
			System.out.println("Would you like to go again? y/n");
			String goAgain = input.nextLine();
			if(goAgain.toLowerCase().equals("y")){
				Game();
				return;
			} else {
				System.out.println("Thanks for playing!");
				return;
			}
		}
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