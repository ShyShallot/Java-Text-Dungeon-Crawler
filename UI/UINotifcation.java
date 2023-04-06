import java.awt.*;

public class UINotifcation extends UIText{
    
    public boolean show = false;
    private int timeToShow;
    private int time = 0;

    public UINotifcation(GamePanel gp, String text, int x, int y, int size, int seconds){
        super(gp,text,x,y,size);
        this.timeToShow = seconds;
        this.setID(gp.UINotifcations.size());
        gp.UINotifcations.add(this);

    }

    public void draw(Graphics2D gD){
        if(this.isDead()){
            return;
        }
        if(!show){
            return;
        }
        gD.setFont(new Font("Arial", Font.PLAIN, this.fontSize()));
        gD.setColor(this.getColor());
        gD.drawString(this.getMessage(), this.xPos(), this.yPos());
    }

    public void show(){
        this.show = true;
    }

    public int timeToShow(){
        return this.timeToShow;
    }

    public int time(){
        return this.time;
    }

    public void incTime(){
        this.time++;
    }
}
