import java.text.DecimalFormat;
import java.util.*;
class AI extends Player {

	public AI(String name, Type type) {
		super(name, type);
	}

	public void shouldDefend(boolean isPlayerHoldingGround) { // AI FUNC only
		if (Game.currentRound - this.lastRoundDefended() < 2) {
			return;
		}
		if (this.isHoldingGround()) {
			return;
		}
		double chance = 0.0;
		double frac = -0.5;
		int healthPercentage = this.healthPercentage();
		double[][] weightMatrix = {
			// HP   Chance to Defend 
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
				chance = CusLib.linearInterp(frac, weightMatrix[i-1][1], weightMatrix[i][1], false);
			}
			if(healthPercentage <= weightMatrix[i][0] && healthPercentage >= weightMatrix[i+1][0]){
				chance = CusLib.linearInterp(frac, weightMatrix[i][1], weightMatrix[i+1][1],false);
			}
		}
		if(chance > Math.random() && !isPlayerHoldingGround){
			this.Defend(Game.currentRound);
		}
	}

	public void AIAction() {
		shouldDefend(Game.mainPlayer.isHoldingGround());
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
		//System.out.println("Health Percent: " + healthPercent);
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
			}// If we are at the bottom of the matrix, reverse our inputs and go one back
			if(i == weightMatrix.length-1 && (healthPercent <= weightMatrix[i][0] && healthPercent >= weightMatrix[i-1][0])){ 
				dmg = CusLib.linearInterp(frac, weightMatrix[i-1][1], weightMatrix[i][1],false); 
				heal = CusLib.linearInterp(frac, weightMatrix[i-1][2], weightMatrix[i][2], false);
			}
			if (healthPercent <= weightMatrix[i][0] && healthPercent >= weightMatrix[i + 1][0]) { // if the health percentage is within a range
				dmg = CusLib.linearInterp(frac, weightMatrix[i][1], weightMatrix[i + 1][1], false); // Interpolate our damage based on the position of our range by health
				heal = CusLib.linearInterp(frac, weightMatrix[i][2], weightMatrix[i + 1][2], false);
			}
		}
		double[] decide = { dmg, heal };
		for (int i = 0; i < decide.length; i++) {
			decide[i] = Double.parseDouble(df.format(decide[i]));
		}
		return decide;
	}

	public Spell decideSpell(Player opponent, boolean shouldHeal){
		if(this.getType().getName() != "Mage"){
			return null;
		}
		ArrayList<Spell> spells = SpellsList.getSpells();
		Spell betterSpell = new Orb();
		for(Spell spell : spells){
			if(!spell.canPlayerCast(this)){
				continue;
			}
			if(shouldHeal){
				if(spell.heal() > betterSpell.heal()){
					if(spell.turnsToCast() < betterSpell.turnsToCast() && Math.random() > 0.5){
						betterSpell = spell;
					}
				}
			} else {
				if(spell.damage() * opponent.getArmor().armorEffectiveness(spell.damageType()) > betterSpell.damage() * opponent.getArmor().armorEffectiveness(spell.damageType())){
					if(spell.turnsToCast() < spell.turnsToCast() && Math.random() > 0.5){
						betterSpell = spell;
					}
				}
			}
		}
		return betterSpell;
	}

}