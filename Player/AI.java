import java.text.DecimalFormat;
import java.util.*;
class AI extends Player {
	
	public AI(String name){
		super(name);
	}

	public int Roll(int bias){
		int biasRoll = CusMath.randomNum(1,bias);
		int roll = CusMath.randomNum(1,20) + biasRoll;
		if(roll > 20){
			roll = 20;
		}
		return roll;
	}

	public void shouldDefend(boolean isPlayerHoldingGround){ // AI FUNC only
		if(Game.currentRound-this.lastRoundDefended < 2){
			return;
		}
		if(this.holdingGround){
			return;
		}
		double chance = 0.0;
		if(health <= 35){
			chance = 0.25;
		}
		if(health <= 25){
			chance = 0.5;
		}
		if(health <= 10){
			chance = 0.75;
		}
		if(chance > Math.random() && !isPlayerHoldingGround){
			this.Defend(Game.currentRound);
		}
	}

	public void AIAction(){
		shouldDefend(Game.mainPlayer.holdingGround);
		if(!this.holdingGround){
			Game.aiRoll = Roll(Game.aiRollBias);
		}
	}

	public double[] itemDecideWeightTable(){
		double[][] weightMatrix = {
			// HP,  DMG   HEAL
			{100.0, 1.0,  0.0}, 
			{75.0,  0.8,  0.1}, 
			{50.0,  0.65, 0.45},
			{35.0,  0.4,  0.65},
			{15,    0.1,  0.85},
			{0,  	0.0,  1.0}
		};
		int healthPercent = this.healthPercentage(); 
		System.out.println("Health Percent: " + healthPercent);
		double dmg = 0.0;
		double heal = 0.0;
		double frac = -0.5; // 0 is no interp, 1 is max interp, negatives will bias downwards, which is what we want is this case
		DecimalFormat df = new DecimalFormat("#.###");
		for(int i=0;i<weightMatrix.length;i++){
			if(healthPercent == weightMatrix[i][0]){
				dmg = weightMatrix[i][1];
				heal = weightMatrix[i][2];
				break;
			}
			if(i == weightMatrix.length-1 && (healthPercent <= weightMatrix[i][0] && healthPercent >= weightMatrix[i-1][0])){ // If we are at the bottom of the matrix, reverse our inputs and go one back
				dmg = CusMath.linearInterp(frac, weightMatrix[i-1][1], weightMatrix[i][1],false); 
				heal = CusMath.linearInterp(frac, weightMatrix[i-1][2], weightMatrix[i][2], false);
				continue;
			}
			if (healthPercent <= weightMatrix[i][0] && healthPercent >= weightMatrix[i+1][0]){ // if the health percentage is within a range
				dmg = CusMath.linearInterp(frac, weightMatrix[i][1], weightMatrix[i+1][1], false); // Interpolate our damage based on the position of our range by health
				heal = CusMath.linearInterp(frac, weightMatrix[i][2], weightMatrix[i+1][2], false);
			}
		}
		double[] decide = {dmg,heal};
		for(int i=0;i<decide.length;i++){
			decide[i] = Double.parseDouble(df.format(decide[i]));
		}
		return decide;
	}
}