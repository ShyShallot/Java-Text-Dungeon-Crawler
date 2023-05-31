import java.awt.*;

public class UINotifcation extends UIText{
    
    public boolean show = false;
    private double timeToShow;
    private double time = 0;

    public UINotifcation(GamePanel gp, String text, int x, int y, int size, double seconds){
        super(gp,text,x,y,size);
        this.timeToShow = seconds;

    }

    public void draw(Graphics2D gD){
        if(this.isDead()){
            return;
        }
        if(!show){
            return;
        }
        if(this.time > this.timeToShow*this.getGP().FPS){
            this.destory();
            return;
        }
        if(this.isCentered()){
                //System.out.println("Centering Text: " + i);
                int textLength = (int)gD.getFontMetrics(new Font("Arial", Font.PLAIN, this.fontSize())).stringWidth(this.getMessage());
                //System.out.println(screenWidth + " " + textLength);
                this.setX((int)((this.getGP().screenWidth-textLength)/2));
        }
        gD.setFont(new Font("Arial", Font.PLAIN, this.fontSize()));
        gD.setColor(this.getColor());
        gD.drawString(this.getMessage(), this.xPos(), this.yPos());
        this.incTime();
    }

    public void show(){
        this.show = true;
    }

    public double timeToShow(){
        return this.timeToShow;
    }

    public double time(){
        return this.time;
    }

    public void incTime(){
        this.time++;
    }
}
