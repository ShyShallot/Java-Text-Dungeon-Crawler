class Items {
    public static boolean doesPlayerHaveItem(Player player, Item item){
		for(int i = 0; i<player.inventory.size();i++){
			Item tmpItem = player.inventory.get(i);
			if(tmpItem.name.equals(item.name.toLowerCase())){
				return true;
			}
		}
		return false;
	}

    public static boolean doesPlayerHaveItem(Player player, String item){
		for(int i = 0; i<player.inventory.size();i++){
			Item tmpItem = player.inventory.get(i);
			if(tmpItem.name.toLowerCase().equals(item.toLowerCase())){
				return true;
			}
		}
		return false;
	}

    public static Item getItemFromName(String name){
		Item searchItem = new Item();
		for(Item item : Game.itemList){
			if(item.name.toLowerCase().equals(name)){
				searchItem = item;
			}
		}
		return searchItem;
	}
}
