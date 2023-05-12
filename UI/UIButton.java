import java.awt.Color;

import javax.swing.JButton;

public class UIButton extends UI{
 
    private String text;
    private int textSize;
    private Color color;
    private int width;
    private int height;
    private double scale;
    public boolean activated = false;
    private JButton JButton;
    private Object object = null;

    public UIButton(GamePanel gp, int x, int y, Color color, double scale){
        super(gp,x,y);
        this.color = color;
        this.text = "";
        this.textSize = 0;
        this.scale = scale;
    }
 
    public UIButton(GamePanel gp, int x, int y, int width, int height, Color color, String text, int textSize){
        super(gp,x,y);
        this.width = width;
        this.height = height;
        this.color = color;
        this.text = text;
        this.textSize = textSize;
        this.setID(gp.UIButtons.size());
        gp.UIButtons.add(this);
        this.scale = 1.0;
    }

    public UIButton(GamePanel gp, int x, int y, int width, int height, Color color, String text, int textSize, double scale){
        this(gp,x,y,width,height,color,text,textSize);
        this.scale = scale;

    }

    public int height(){
        return this.height;
    }

    public void setHeight(int height){
        this.height = height;
    }

    public int width(){
        return this.width;
    }

    public void setWidth(int width){
        this.width = width;
    }

    public Color getColor(){
        return this.color;
    }

    public String message(){
        return this.text;
    }

    public int getTextSize(){
        return this.textSize;
    }

    public double getScale(){
        return this.scale;
    }

    public void setJButton(JButton jbutton){
        this.JButton = jbutton;
    }

    public JButton getJButton(){
        return this.JButton;
    }
    
    public void setAccObject(Object obj){
        this.object = obj;
    }

    public Object getAccObject(){
        return this.object;
    }

}
