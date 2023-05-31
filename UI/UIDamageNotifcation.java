public class UIDamageNotifcation extends UINotifcation{
    public UIDamageNotifcation(GamePanel gp, String text, int x, int y){
        super(gp,text,x,y,20,0.85);
        int[][] animArr = {{-2,2},{-2,2},{-2,2},{-2,2}};
        UIAnim anim = new UIAnim(gp,animArr, true, true, 4);
        this.addAnimation("show_00", anim);
    }

    public UIDamageNotifcation(GamePanel gp, String text, UI graphicElement){
        this(gp,text,graphicElement.xPos(),graphicElement.yPos());
    }

    public UIDamageNotifcation(GamePanel gp, String text){
        this(gp,text,gp.positionBaseTable.get("damage_notif")[0],gp.positionBaseTable.get("damage_notif")[1]);
    }

    public void activate(){
        this.show();
        this.playAnimation("show_00");
    }
}
