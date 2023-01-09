import java.text.DecimalFormat;
import java.util.*;

class AI extends Player {
	int id;

	public AI(String name) {
		super(name);
	}

	public int Roll(int bias) {
		int biasRoll = CusMath.randomNum(1, bias);
		int roll = CusMath.randomNum(1, 20) + biasRoll;
		if (roll > 20) {
			roll = 20;
		}
		return roll;
	}

	public void shouldDefend(boolean isPlayerHoldingGround) { // AI FUNC only
		if (Game.currentRound - this.lastRoundDefended < 2) {
			return;
		}
		if (this.holdingGround) {
			return;
		}
		double chance = 0.0;
		double frac = -0.5;
		int healthPercentage = this.healthPercentage();
		double[][] weightMatrix = {
			{100.0, 0.0},
			{50.0, 0.15},
			{35.0, 0.25},
			{25.0, 0.5},
			{10.0, 0.75},
			{0.0, 1.0}
		};
		for(int i=0;i<weightMatrix.length;i++){
			if(healthPercentage == weightMatrix[i][0]){
				chance = weightMatrix[i][0];
			}
			if(i == weightMatrix.length-1 && (healthPercentage <= weightMatrix[i][0] && healthPercentage >= weightMatrix[i-1][0])){
				chance = CusMath.linearInterp(frac, weightMatrix[i-1][1], weightMatrix[i][1], false);
			}
			if(healthPercentage <= weightMatrix[i][0] && healthPercentage >= weightMatrix[i+1][0]){
				chance = CusMath.linearInterp(frac, weightMatrix[i][1], weightMatrix[i+1][1],false);
			}
		}
		if(chance > Math.random() && !isPlayerHoldingGround){
			this.Defend(Game.currentRound);
		}
	}

	public void AIAction() {
		shouldDefend(Game.mainPlayer.holdingGround);
		if (!this.holdingGround) {
			Game.aiRoll = Roll(Game.aiRollBias);
		}
	}

	public double[] itemDecideWeightTable() {
		double[][] weightMatrix = { // This is a matrix to define the weighting of using a Damage or Heal Based Item
									// based off of health, Values will be interpolated if the health percentage is
									// in the range
				// HP, DMG HEAL USE ONLY 0 to 1 Values
				{ 100.0, 1.0, 0.0 },
				{ 75.0, 0.8, 0.1 },
				{ 50.0, 0.65, 0.45 },
				{ 35.0, 0.4, 0.65 },
				{ 15, 0.1, 0.85 },
				{ 0, 0.0, 1.0 }
		};
		int healthPercent = this.healthPercentage();
		// System.out.println("Health Percent: " + healthPercent);
		double dmg = 0.0;
		double heal = 0.0;
		double frac = -0.5; // Only go from -1 to 1, -1 will bias past the min, 0 is no bias, 1 will bias
							// past the max
		DecimalFormat df = new DecimalFormat("#.###");
		for (int i = 0; i < weightMatrix.length; i++) {
			if (healthPercent == weightMatrix[i][0]) {
				dmg = weightMatrix[i][1];
				heal = weightMatrix[i][2];
				break;
			}
			if(i == weightMatrix.length-1 && (healthPercent <= weightMatrix[i][0] && healthPercent >= weightMatrix[i-1][0])){ // If we are at the bottom of the matrix, reverse our inputs and go one back
				dmg = CusMath.linearInterp(frac, weightMatrix[i-1][1], weightMatrix[i][1],false); 
				heal = CusMath.linearInterp(frac, weightMatrix[i-1][2], weightMatrix[i][2], false);
				continue;
			}
			if (healthPercent <= weightMatrix[i][0] && healthPercent >= weightMatrix[i + 1][0]) { // if the health
																									// percentage is
																									// within a range
				dmg = CusMath.linearInterp(frac, weightMatrix[i][1], weightMatrix[i + 1][1], false); // Interpolate our
																										// damage based
																										// on the
																										// position of
																										// our range by
																										// health
				heal = CusMath.linearInterp(frac, weightMatrix[i][2], weightMatrix[i + 1][2], false);
			}
		}
		double[] decide = { dmg, heal };
		for (int i = 0; i < decide.length; i++) {
			decide[i] = Double.parseDouble(df.format(decide[i]));
		}
		return decide;
	}
}