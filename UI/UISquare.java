import java.awt.Color;
import java.awt.Graphics2D;

public class UISquare extends UI{
    private int width;
    private int height;
    private Color color;
    private UIAnim animation;
    
    public UISquare(GamePanel gp, int x, int y, int width, int height, Color color){
        super(gp,x,y);
        this.width = width;
        this.height = height;
        this.color = color;
        gp.UISquares.add(this);
    }

    public UISquare(GamePanel gp, int x, int y, int width, int height, Color color, UIAnim animation){
        this(gp,x,y,width,height,color);
        this.animation = animation;
    }

    public UISquare(GamePanel gp, int x, int y, int width, Color color){
        this(gp,x,y,width,width,color);
    }

    public UISquare(GamePanel gp, int x, int y, int width, Color color, UIAnim animation){
        this(gp,x,y,width,color);
        this.animation = animation;
    }

    public void draw(Graphics2D gD){
        gD.setColor(this.color);
        gD.setPaint(color);
        gD.fillRect(this.xPos(), this.yPos(), this.width, this.height);
    }

    public int getWidth(){
        return this.width;
    }

    public int getHeight(){
        return this.height;
    }

    public Color getColor(){
        return this.color;
    }

    public UIAnim getAnimation(){
        return animation;
    }
}