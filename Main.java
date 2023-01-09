import java.util.*;
class Main {
  	public static void main(String[] args) {
		Game();
  	}

	public static void Game(){
		Scanner input = new Scanner(System.in);
    	Game game = new Game();
		//game.TestWeights();
		game.SetupInput(input);
		game.Start();
		if(game.over){
			System.out.println("Would you like to go again? y/n");
			String goAgain = input.nextLine();
			if(goAgain.toLowerCase().equals("y")){
				Game();
			} else {
				System.out.println("Thanks for playing!");
			}
		}
	}
}