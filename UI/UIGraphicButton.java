import java.rmi.UnexpectedException;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class UIGraphicButton extends UIButton{
    private ImageIcon image;

    public UIGraphicButton(GamePanel gp, int x, int y, String imageLoc, double scale){
        super(gp,x,y,Color.white,scale);
        this.image = new ImageIcon(imageLoc);
        this.setHeight(this.image.getIconHeight());
        this.setWidth(this.image.getIconWidth());
    }

    public ImageIcon getImage(){
        return this.image;
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
    
    private void createJButton(){
        if(this.getJButton() != null){
            return;
        }
        if(this.getImage() == null){
            return;
        }
        UIGraphicButton UIbutton = this;
        JButton button = new JButton();
        button.setBounds(this.xPos(), this.yPos(), (int)(this.width()*this.getScale()), (int)(this.height()*this.getScale())); // set the bounds of the button by the scale defined when created
        button.setIcon(new ImageIcon(this.getImage().getImage().getScaledInstance((int)(this.getImage().getIconWidth()*this.getScale()),(int)(this.getImage().getIconHeight()*this.getScale()), Image.SCALE_SMOOTH))); // Scale Image
        button.setBackground(this.getColor());
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                Game.buttonPressed(UIbutton);
                UIbutton.activated = true;
            }
        });
        this.setJButton(button);
        this.getGP().add(button);
    }
}
