import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class UIText extends UI{
    
    private String text;
    private Color color = Color.WHITE;
    private int fontSize;
    private boolean isCentered;


    public UIText(GamePanel gp, String text, int x, int y, int size){
        super(gp,x,y);
        this.text = text;
        this.color = Color.white;
        this.fontSize = size;
        this.isCentered = false;
        this.setID(gp.UITexts.size());
        gp.UITexts.add(this);
    }

    public void draw(Graphics2D gD){
        if(this.isDead()){
            return;
        }
        gD.setFont(new Font("Arial", Font.PLAIN, this.fontSize));
        gD.setColor(this.color);
        gD.drawString(this.text, this.xPos(), this.yPos());
    }

    public <T> void updateText(T newText){
        this.text = "" + newText;
    }

    public void setColor(Color color){
        this.color = color;
    }

    public String getMessage(){
        return this.text;
    }

    public Color getColor(){
        return this.color;
    }

    public int fontSize(){
        return this.fontSize;
    }

    public void setCentered(boolean shouldCenter){
        this.isCentered = shouldCenter;
    }

    public boolean isCentered(){
        return this.isCentered;
    }

}
