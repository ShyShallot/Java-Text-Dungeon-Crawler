import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

public class UIText extends UIAnimatable{
    
    private String text;
    private Color color = Color.WHITE;
    private Color secondaryColor = null;
    private int fontSize;
    private boolean isCentered;
    private Object[] variableArray = new Object[0];

    public UIText(GamePanel gp, String text, int x, int y, int size){
        super(gp,x,y);
        this.text = text;
        this.color = Color.white;
        this.fontSize = size;
        this.isCentered = false;
        this.setID(gp.UITexts.size());
        gp.UITexts.add(this);
    }

    public UIText(GamePanel gp, String text, int x , int y, int size, Object... vars){
        this(gp,text,x,y,size);
        variableArray = vars;
        if(CusLib.getSpeicalCharCount(this.text, '%','c') != this.variableArray.length){
            throw new ArrayIndexOutOfBoundsException();
        }

    }

    public UIText(GamePanel gp, String text, int x, int y, int size, Color color){
        this(gp,text,x,y,size);
        this.setColor(color);
    }

    public UIText(GamePanel gp, String text, int x , int y, int size, Color color, Object... vars){
        this(gp,text,x,y,size,color);
        variableArray = vars;
        if(CusLib.getSpeicalCharCount(this.text, '%','c') != this.variableArray.length){
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public UIText(GamePanel gp, String text, int x, int y, int size, Color color, Color secondaryColor){
        this(gp,text,x,y,size,color);
        this.secondaryColor = secondaryColor;
    }

    public UIText(GamePanel gp, String text, int x, int y, int size, Color color, Color secondaryColor, Object... vars){
        this(gp,text,x,y,size,color,secondaryColor);
        variableArray = vars;
        CusLib.DebugOutputLn(this.variableArray.length + ", " + CusLib.getOccurencesOfString(this.text, "%c"));
        if(CusLib.getSpeicalCharCount(this.text, '%','c') != this.variableArray.length){
            throw new ArrayIndexOutOfBoundsException();
        }
    }


    public void draw(Graphics2D gD){
        if(secondaryColor != null){
            this.multiDraw(gD);
            return;
        }
        if(this.isDead()){
            return;
        }
        String replacedText = this.text;
        if(this.variableArray.length > 0){
            for(int i=0;i<this.variableArray.length;i++){
                replacedText = this.text.replace("%c","" + this.variableArray[i]);
            }
        }
        gD.setFont(new Font("Arial", Font.PLAIN, this.fontSize));
        gD.setColor(this.color);
        gD.drawString(replacedText, this.xPos(), this.yPos());
    }

    public void multiDraw(Graphics2D gD){
        int colorCharCount = CusLib.getSpeicalCharCount(this.text, '%', 's');
        if(colorCharCount %2 != 0 || colorCharCount == 0){
            return;
        }
        String replacedText = this.text;
        if(this.variableArray.length > 0){
            for(int i=0;i<this.variableArray.length;i++){
                replacedText = replacedText.replaceFirst("%c",""+this.variableArray[i]);
                //System.out.println(replacedText);
            }
        }
        String finalString = "";
        String[] split = replacedText.split("%s");
        int[] indexs = new int[CusLib.getSpeicalCharCount(replacedText, '%', 's')];
        //CusLib.DebugOutputLn(indexs.length);
        int count = 0;
        //CusLib.DebugOutputLn(split.length);
        /*for(int i=0;i<split.length;i++){
            System.out.print("0"+split[i] + "/");
        }*/
        CusLib.DebugOutputLn();
        
        if(split.length == 2 ){
            for(int i=0;i<split.length;i++){
                finalString += split[i];
                indexs[count] = finalString.length();
                count++;
            }
        } else {
            for(int i=0;i<split.length;i++){
                if(i > 0){
                    CusLib.DebugOutputLn(finalString.length());
                    indexs[count] = finalString.length();
                    CusLib.DebugOutputLn(split[i]);
                    finalString += " " + split[i];
                    count++;
                } else if( i== 0) {
                    CusLib.DebugOutputLn(split[i]);
                    if(split[i].equals("")){
                        continue;
                    }
                    finalString += split[i];
                    indexs[count] = finalString.length();
                    count++;
                }
                //CusLib.DebugOutputLn(finalString);
                
            }
        }
        
        if(indexs[indexs.length-1] == 0){
            indexs[indexs.length-1] = finalString.length();
        }
        //CusLib.DebugOutputLn(finalString);
        for(int i=0;i<indexs.length;i++){
            CusLib.DebugOutput(indexs[i]);
        }
        CusLib.DebugOutputLn();
        AttributedString attributedText = new AttributedString(finalString);
        attributedText.addAttribute(TextAttribute.SIZE, this.fontSize, 0, finalString.length()-1);
        for(int i=0;i<indexs.length;i+=2){
            CusLib.DebugOutputLn(finalString);
            //CusLib.DebugOutputLn(indexs[i] + "&&" + indexs[i+1] + "/" + finalString.length());
            attributedText.addAttribute(TextAttribute.FOREGROUND, this.secondaryColor, indexs[i], indexs[i+1]);
        }
        gD.drawString(attributedText.getIterator(),this.xPos(),this.yPos());

    }

    public <T> void update(T... vars){
        if(vars.length != this.variableArray.length){
            throw new ArrayIndexOutOfBoundsException();
        }
        for(int i=0;i<this.variableArray.length;i++){
            this.variableArray[i] = vars[i];
        }
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

}
