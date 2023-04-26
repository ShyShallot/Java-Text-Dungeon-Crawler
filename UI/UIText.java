import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

public class UIText extends UI{
    
    private String text;
    private Color color = Color.WHITE;
    private Color secondaryColor = null;
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

    public UIText(GamePanel gp, String text, int x, int y, int size, Color color){
        this(gp,text,x,y,size);
        this.setColor(color);
    }

    public UIText(GamePanel gp, String text, int x, int y, int size, Color color, Color secondaryColor){
        this(gp,text,x,y,size,color);
        this.secondaryColor = secondaryColor;
    }

    public void draw(Graphics2D gD){
        if(secondaryColor != null){
            this.multiDraw(gD);
            return;
        }
        if(this.isDead()){
            return;
        }
        gD.setFont(new Font("Arial", Font.PLAIN, this.fontSize));
        gD.setColor(this.color);
        gD.drawString(this.text, this.xPos(), this.yPos());
    }

    public void multiDraw(Graphics2D gD){
       int[] foundSpecials = CusLib.getOccurencesOfChar(this.text, "%");
       if(foundSpecials.length%2 != 0){
            System.out.println("ERROR, Uneven amount of Special Chars");
            return;
       }
       String newText = this.text.replace("%"," ");
       AttributedString newString = new AttributedString(newText);
       for(int i=0;i<foundSpecials.length;i+=2){
            newString.addAttribute(TextAttribute.FOREGROUND, this.secondaryColor, foundSpecials[i], foundSpecials[i+1]);
       }
       gD.drawString(newString.getIterator(),this.xPos(),this.yPos());

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

    public <T> void update(T text){
        this.text = "" + text;
    }

}
