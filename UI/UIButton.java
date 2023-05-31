import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.SwingConstants;

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
        super(gp,x,y,0);
        this.color = color;
        this.text = "";
        this.textSize = 0;
        this.scale = scale;
    }
 
    public UIButton(GamePanel gp, int x, int y, int width, int height, Color color, String text, int textSize){
        super(gp,x,y,0);
        this.width = width;
        this.height = height;
        this.color = color;
        this.text = text;
        this.textSize = textSize;
        this.scale = 1.0;
    }

    public UIButton(GamePanel gp, int x, int y, int width, int height, Color color, String text, int textSize, double scale){
        this(gp,x,y,width,height,color,text,textSize);
        this.scale = scale;

    }

    public void draw(Graphics2D gD){
        if(this.getJButton() != null){
            if(this.xPos() != this.getJButton().getX() || this.yPos() != this.getJButton().getY()){ // idk if this ever gets called, but just incase if the positions of the physical button and the button class are desynced
                this.getGP().remove(this.getJButton());
                this.getJButton().setBounds(this.xPos(),this.yPos(),this.width(),this.height());
                this.getGP().add(this.getJButton());
            }
        }
        this.createJButton();
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

    private void createJButton(){
        if(this.JButton != null){
            return;
        }
        UIButton UIbutton = this;
        JButton button = new JButton(this.text);
        button.setBounds(this.xPos(),this.yPos(),(int)(this.width()*this.getScale()), (int)(this.height()*this.getScale()));
        button.setVerticalAlignment(SwingConstants.CENTER);
        button.setBackground(this.getColor());
        button.setFont(new Font("Arial",Font.PLAIN, this.getTextSize()));
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                Game.buttonPressed(UIbutton);
                UIbutton.activated = true;
            }
        });
        this.setJButton(button);
        this.getGP().add(button);
    }

    public void destory(){
        super.destory();
        this.getGP().remove(this.getJButton());
        this.getGP().repaint();
    }

}
