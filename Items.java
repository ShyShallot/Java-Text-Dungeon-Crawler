class Items {
    public static boolean doesPlayerHaveItem(Player player, Item item){
		for(int i = 0; i<player.getInventory().size();i++){
			Item tmpItem = player.getInventory().get(i);
			if(tmpItem.getName().equals(item.getName().toLowerCase())){
				return true;
			}
		}
		return false;
	}

    public static boolean doesPlayerHaveItem(Player player, String item){
		for(int i = 0; i<player.getInventory().size();i++){
			Item tmpItem = player.getInventory().get(i);
			if(tmpItem.getName().toLowerCase().equals(item.toLowerCase())){
				return true;
			}
		}
		return false;
	}

    public static Item getItemFromName(String name){
		Item searchItem = new Item();
		for(Item item : Game.itemList){
			if(item.getName().toLowerCase().equals(name)){
				searchItem = item;
			}
		}
		return searchItem;
	}

	public static String getDamageTypeName(int dmgType){
		String type = "";
		switch (dmgType){ // PLEASE ADD ANY NEW TYPE AT THE END
			case 0:
				type = "Physical";
				break;
			case 1:
				type = "Magic";
				break;
			case 2:
				type = "Fire";
				break;
			case 3:
				type = "Water";
				break;
			case 4:
				type = "Poison";
				break;
			case 5:
				type = "Bleed";
				break;
			case 6:
				type = "Explosion";
				break;
			case 7:
				type = "Ranged";
				break;
		}
		return type;
	}
}
