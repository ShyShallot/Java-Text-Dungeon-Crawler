import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

public class UISpriteSheet {
    private int width,height;
    private int currentIndex;
    private BufferedImage sprite;
    private BufferedImage spriteSheet;
    private BufferedImage[] spriteChunks;

    public UISpriteSheet(String file, int charWdith, int charHeight){
        try{
            spriteSheet = ImageIO.read(new File(file));
        } catch (IOException ex){
            ex.printStackTrace();
        }

        this.width = charWdith;
        this.height = charHeight;

        if(spriteSheet.getWidth() % width != 0 || spriteSheet.getHeight() % height != 0){
            throw new IndexOutOfBoundsException();
        }

        int rows = spriteSheet.getWidth()/this.width;
        int cols = spriteSheet.getHeight()/this.height;

        spriteChunks = new BufferedImage[rows*cols];
        //System.out.println(spriteChunks.length);
        int x = 0;
        int y = 0;
        int count = 0;
        for(int i=0;i<rows;i++){
            for(int k=0;k<cols;k++){
                //System.out.println(count);
                spriteChunks[count] = spriteSheet.getSubimage(x,y, this.width, this.height);
                if(x < spriteSheet.getWidth()){
                    x += this.width;
                }
                if(x >= spriteSheet.getWidth()){
                    x = 0;
                    y += this.height;
                }
                count++;
            }
        }
    }

    public UISpriteSheet(String file, int charTile){
        this(file,charTile,charTile);
    }

    public BufferedImage getSprite(int index){
        if(index < 0 || index >= this.spriteChunks.length){
            return null;
        }
        return this.spriteChunks[index];
    }

    public String toString(){
        return String.format("Tile: %s, %s, Sprite Sheet: %s, Sprite Chunks: %s", this.width,this.height,this.spriteSheet,this.spriteChunks.length);
    }

    public BufferedImage[] getSpriteChunks(){
        return this.spriteChunks;
    }


}
