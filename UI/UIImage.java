import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class UIImage extends UIAnimatable{
    
    private BufferedImage image;
    private int xScale;
    private int yScale;


    public UIImage(GamePanel gp, BufferedImage image, int x, int y, int xScale, int yScale){
        super(gp,x,y,1);
        this.image = image;
        this.xScale = xScale;
        this.yScale = yScale;
    }

    public void draw(Graphics2D gD){
        gD.drawImage(image, this.xPos(), this.yPos(), this.xScale, this.yScale, null);
    }

}
