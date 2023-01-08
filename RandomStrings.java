class RandomStrings{
	public static final String RESET = "\u001B[0m";
	public static final String RED = "\u001B[31m";
	public static final String GREEN = "\u001B[32m";
	String[] rollStrings = {"%s poked %s with a stick and dealt " + RED + "%s" + RESET + " dmg", "%s had tripped %s and they took " + RED + "%s" + RESET + " dmg", "%s yelled in %s and dealt " + RED + "%s" + RESET + " dmg", "%s jabbed %s in the sides and dealt " + RED + "%s" + RESET + " dmg", "%s slapped %s and dealt " + RED + "%s" + RESET + " dmg", "%s struck %s with lightning and dealt " + RED + "%s" + RESET + " dmg"};
	public String generateRollString(String dealer, String reciever, int damage){
		return String.format(rollStrings[CusMath.randomNum(0, rollStrings.length-1)],dealer,reciever,damage);
	}

}