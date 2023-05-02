import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.nio.Buffer;

public class UISprite extends UI{

    private BufferedImage sprite;
    private UISpriteSheet parentSheet;
    private UISpriteAnimList spriteAnimList = new UISpriteAnimList();
    private double scale = 1.0;
    
    public UISprite(GamePanel gp, int x, int y, int spriteIndex, UISpriteSheet parent, double scale){
        super(gp,x,y);
        this.sprite = parent.getSprite(spriteIndex);
        this.parentSheet = parent;
        gp.UISprites.add(this);
        this.scale = scale;
    }

    public void updateSprite(BufferedImage newSprite){
        this.sprite = newSprite;
    }

    public BufferedImage getSprite(){
        return this.sprite;
    }

    public void draw(Graphics2D gD){
        gD.drawImage(sprite, this.xPos(), this.yPos(), (int)(this.sprite.getWidth()*this.scale), (int)(this.sprite.getHeight()*this.scale), null);
    }

    public UISpriteSheet getParentSheet(){
        return this.parentSheet;
    }

    public void addAnimation(String ref, UISpriteAnim anim){
        this.spriteAnimList.addAnimation(ref, anim);
    }

    public void removeSpriteAnimation(String ref){
        this.spriteAnimList.removeAnimation(ref);
    }

    public UISpriteAnimList getSpriteAnimationsList(){
        return this.spriteAnimList;
    }

    public void playSpriteAnimation(String ref){
        if(Main.gp.spriteQueue.containsKey(this)){
            return;
        }
        if(!this.spriteAnimList.containsAnim(ref)){
            return;
        }
        UISpriteAnim anim = this.spriteAnimList.getAnimation(ref);
        Main.gp.spriteQueue.put(this,anim);
    }

    public double getScale(){
        return this.scale;
    }
}
