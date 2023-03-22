import java.util.ArrayList;

public class BossRoom extends Room{
    private Boss boss;
    private ArrayList<Item> rewards = new ArrayList<>();

    public BossRoom(){
        createRandomBoss();
        treasure();
    }

    private void createRandomBoss(){
        BossType[] bossTypes = {new FireGoblin()};
        int randomBossIndex = CusLib.randomNum(0, bossTypes.length-1);
        BossType bossType = bossTypes[randomBossIndex];
        this.boss = new Boss(bossType.getName(), bossType, bossType.baseHealth() * Game.DifficultyMod());
    }

    private void treasure(){
        for(int i=0;i<Item.ItemList.size();i++){
            Item curItem = Item.ItemList.get(i);
            if(Math.random() > (0.45 + (Math.random()/10))){
                if(rewards.size() < 5 && !Game.mainPlayer.getType().isItemIllegal(curItem)){
                    rewards.add(curItem);
                }
            }
        }
    }

    public void rewardPlayer(){
        String rewardList = "You found: ";
        for(int i=0;i<rewards.size();i++){
            Game.mainPlayer.addItemToInventory(rewards.get(i));
            rewardList += rewards.get(i).getName() + ", ";
        }
        rewardList.substring(0, rewardList.length()-2);
        rewardList += "!";
        System.out.println(rewardList);
    }

    public Boss boss(){
        return this.boss;
    }


}
