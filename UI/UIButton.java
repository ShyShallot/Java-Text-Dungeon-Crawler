import java.awt.event.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

public class UIButton extends UI{

    private String text;
    private int textSize;
    private Color color;
    private int width;
    private int height;
    private double scale;
    public boolean activated = false;
 
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

    public int width(){
        return this.width;
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

}