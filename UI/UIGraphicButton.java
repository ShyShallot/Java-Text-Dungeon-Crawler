import java.awt.Color;
import java.rmi.UnexpectedException;

import javax.swing.ImageIcon;

public class UIGraphicButton extends UIButton{
    private ImageIcon image;

    public UIGraphicButton(GamePanel gp, int x, int y, String imageLoc, double scale){
        super(gp,x,y,Color.white,scale);
        this.image = new ImageIcon(imageLoc);
        this.setHeight(this.image.getIconHeight());
        this.setWidth(this.image.getIconWidth());
        gp.UIGraphicButtons.add(this);
    }

    public ImageIcon getImage(){
        return this.image;
    }
}
