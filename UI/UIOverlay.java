import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class UIOverlay extends UI{
    
    private int layer;
    private BufferedImage image;
    private float opacity = 1f;

    public UIOverlay(GamePanel gp, String image, int layer){
        super(gp,0,0);
        if(layer > 6){
            layer = 6;
        }
        try{
            File img = new File(image);
            this.image = ImageIO.read(img);
            this.layer = layer;
            Main.gp.UIOverlays.add(this);
        } catch (IOException e){
            e.printStackTrace();
        }
        
    }

    public int getLayer(){
        return this.layer;
    }

    public void setLayer(int newLayer){
        if(layer > 6){
            layer = 6;
        }
        this.layer = newLayer;
    }

    public BufferedImage getImage(){
        return this.image;
    }

    public void setImage(BufferedImage image){
        this.image = image;
    }

    public void setOpacity(float newOpacity){
        if(newOpacity > 1f){
            newOpacity = 1f;
        }
        if(newOpacity < 0f){
            newOpacity = 0f;
        }
        this.opacity = newOpacity;
    }

    public float getOpacity(){
        return this.opacity;
    }

    public void draw(Graphics2D gD){
        gD.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, this.opacity));
        gD.drawImage(image, this.xPos(), this.yPos(), this.image.getWidth(), this.image.getHeight(), null);
    }
}
