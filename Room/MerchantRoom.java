import java.util.*;
public class MerchantRoom extends Room {
	ArrayList<Item> availItems = new ArrayList<>();

	public void createAvailItems(){
		for(Item item : Game.itemList){
			if(Math.random() > 0.65){
				availItems.add(item);
			}
		}
		if(availItems.size() == 0){
			availItems.add(Items.getItemFromName("Health Vial"));
		}
	}

	public void itemShop(boolean recall){
		if(!recall){
			String itemsString = "";
			System.out.println("Which Item would you like to purchese? You have " + Game.YELLOW + Game.mainPlayer.coins + Game.RESET + " Coins." + " To skip type none");
			for(Item item : availItems){
				itemsString += item.name + ", Description: " + item.description + ", " + "Cost: " + item.cost + " Coins \n";
			}
			itemsString.substring(0,(itemsString.length()-2));
			itemsString += ".";
		}
		String itemPickedString = Game.input.nextLine();
		if(itemPickedString.equals("none")){
			return;
		}
		Item itemPicked = Items.getItemFromName(itemPickedString);
		if(itemPicked == null){
			System.out.println("Could not find that item.");
			itemShop(true);
		}
		if(itemPicked.cost >= Game.mainPlayer.coins){
			System.out.println("You do not have enough coins for that item.");
			itemShop(true);
		}
		System.out.println("You have bought " + itemPicked.name + " for " + itemPicked.cost + " Coins");
		Game.mainPlayer.addItemToInventory(itemPicked);
		return;
	}

}