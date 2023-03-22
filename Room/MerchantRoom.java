import java.util.*;
public class MerchantRoom extends Room {
	ArrayList<Item> availItems = new ArrayList<>();

	public void createAvailItems(){
		for(Item item : Item.ItemList){
			if(Math.random() > 0.55){
				availItems.add(item);
			}
		}
		if(availItems.size() == 0){
			availItems.add(Item.getItemFromName("Health Vial"));
		}
	}

	public void itemShop(boolean recall){
		if(!recall){
			String itemsString = "";
			System.out.println("Which Item would you like to purchese? You have " + CusLib.colorText(Game.mainPlayer.getCoins(), "yellow") + " Coins." + " To skip type none");
			for(Item item : availItems){
				itemsString += item.getName() + ", Description: " + item.getDescription() + ", " + "Cost: " + item.getCost() + " Coins \n";
			}
			itemsString.substring(0,(itemsString.length()-2));
			itemsString += ".";
		}
		String itemPickedString = Game.input.nextLine();
		if(itemPickedString.equals("none")){
			return;
		}
		Item itemPicked = Item.getItemFromName(itemPickedString);
		if(itemPicked == null){
			System.out.println("Could not find that item.");
			itemShop(true);
		}
		if(itemPicked.getCost() >= Game.mainPlayer.getCoins()){
			System.out.println("You do not have enough coins for that item.");
			itemShop(true);
		}
		System.out.println("You have bought " + itemPicked.getName() + " for " + itemPicked.getCost() + " Coins");
		Game.mainPlayer.addItemToInventory(itemPicked);
		itemShop(true);
	}

}