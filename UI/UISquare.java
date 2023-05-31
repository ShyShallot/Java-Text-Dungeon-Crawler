import java.awt.Color;
import java.awt.Graphics2D;

public class UISquare extends UIAnimatable {
    private int width;
    private int height;
    private Color color;
    
    public UISquare(GamePanel gp, int x, int y, int width, int height, Color color){
        super(gp,x,y,3);
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public UISquare(GamePanel gp, int x, int y, int width, Color color){
        this(gp,x,y,width,width,color);
    }


    public void draw(Graphics2D gD){
        gD.setColor(this.color);
        gD.setPaint(color);
        gD.fillRect(this.xPos(), this.yPos(), this.width, this.height);
    }

    public int getWidth(){
        return this.width;
    }

    public void setWidth(int width){
        this.width = width;
    }

    public int getHeight(){
        return this.height;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public Color getColor(){
        return this.color;
    }


}