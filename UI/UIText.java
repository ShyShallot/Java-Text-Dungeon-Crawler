import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.font.TextAttribute;
import java.text.AttributedString;

public class UIText extends UIAnimatable{
    
    private String text;
    private String finalText;
    private Color color = Color.WHITE;
    private Color secondaryColor = null;
    private int fontSize;
    private boolean isCentered;
    private Object[] variableArray = new Object[0];

    public UIText(GamePanel gp, String text, int x, int y, int size){
        super(gp,x,y,1);
        this.text = text;
        this.color = Color.white;
        this.fontSize = size;
        this.isCentered = false;
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
        if(secondaryColor != null){ // if we have a secondary color just run the multidraw function
            this.multiDraw(gD);
            return;
        }
        if(this.isDead()){
            return;
        }
        String replacedText = this.text;
        if(this.variableArray.length > 0){
            for(int i=0;i<this.variableArray.length;i++){
                replacedText = replacedText.replaceFirst("%c",""+this.variableArray[i]);
                //System.out.println(replacedText);
            }
        }
        this.finalText = replacedText;
        if(this.isCentered){
            int textLength = (int)gD.getFontMetrics(new Font("Arial", Font.PLAIN, this.fontSize())).stringWidth(this.getMessage());  // get the length of the text in pixels
            this.setX((int)((this.getGP().screenWidth-textLength)/2));
        }
        gD.setFont(new Font("Arial", Font.PLAIN, this.fontSize));
        gD.setColor(this.color);
        gD.drawString(replacedText, this.xPos(), this.yPos());
    }

    public void multiDraw(Graphics2D gD){
        int colorCharCount = CusLib.getSpeicalCharCount(this.text, '%', 's');
        if(colorCharCount %2 != 0 || colorCharCount == 0){ // we cant do diff colors if we have an uneven amount of special chars for the color
            return;
        }
        String replacedText = this.text;
        if(this.variableArray.length > 0){ // this just goes through the var special chars and replaces text accordingly, we need to do this before our color so our indexs match
            for(int i=0;i<this.variableArray.length;i++){
                replacedText = replacedText.replaceFirst("%c",""+this.variableArray[i]);
                //System.out.println(replacedText);
            }
        }
        String finalString = ""; // we need a seperate string so that we dont override our actual text
        String[] split = replacedText.split("%s"); // split the string into parts by the color special char
        int[] indexs = new int[CusLib.getSpeicalCharCount(replacedText, '%', 's')]; // create an int array with the size of the amount of special chars
        //CusLib.DebugOutputLn(indexs.length);
        int count = 0; // this is just so that we can access the array, think of it as the I variable in a for loop
        //CusLib.DebugOutputLn(split.length);
        for(int i=0;i<split.length;i++){ // debug purposes
            CusLib.DebugOutput("0"+split[i] + "/");
        }
        CusLib.DebugOutputLn();
        
        if(split.length == 2 || indexs.length == 2){ // special case, if we dont do this we get array out of bounds exceptions
            for(int i=0;i<split.length;i++){
                if(count >= indexs.length){ // special case if our count is greater than our indexs length, this is where the weird behvaior is, we just add the last part of the split
                    if(split.length > indexs.length){ // we check if we have more splits than indexs, because if you have 2 special chars you will have 3 splits
                        finalString += split[i];
                    } else {
                        continue;
                    }
                }
                finalString += split[i];
                indexs[count] = finalString.length(); // set the index for the color to the length of the string as we will add the next, so the end of this part of the string is the start of the next part, which is where the color is
                count++;
            }
        } else {
            for(int i=0;i<split.length;i++){
                if(count >= indexs.length){
                    continue;
                }
                if(i > 0){ // we have this seperate as our first split, split[0] will need be added before we add the index of the string to the indexs array 
                    CusLib.DebugOutputLn("String Length: " + finalString.length());
                    indexs[count] = finalString.length();
                    CusLib.DebugOutputLn("Current Split: " + split[i]);
                    finalString += " " + split[i];
                    count++;
                } else if( i== 0) {
                    CusLib.DebugOutputLn("Current Split: " + split[i]);
                    if(split[i].equals("")){
                        continue;
                    }
                    finalString += split[i];
                    CusLib.DebugOutputLn("String Length: " + finalString.length());
                    indexs[count] = finalString.length();
                    count++;
                }
                //CusLib.DebugOutputLn(finalString);
                
            }
        }
        
        if(indexs[indexs.length-1] == 0){ // if the last index of our array == 0 (which it shouldnt), its safe to assume that its meant to be the length of the string
            indexs[indexs.length-1] = finalString.length();
        }
        //CusLib.DebugOutputLn(finalString);
        for(int i=0;i<indexs.length;i++){ // debug
            CusLib.DebugOutput(indexs[i]);
        }
        CusLib.DebugOutputLn();
        AttributedString attributedText = new AttributedString(finalString);
        attributedText.addAttribute(TextAttribute.SIZE, this.fontSize, 0, finalString.length()-1); // set the font size of our whole string
        for(int i=0;i<indexs.length;i+=2){ // since we are working in pairs we can just do i+=2
            CusLib.DebugOutputLn(finalString);
            CusLib.DebugOutputLn("Subtring Start: " + indexs[i] + ", Substring End: " + indexs[i+1]);
            //CusLib.DebugOutputLn(indexs[i] + "&&" + indexs[i+1] + "/" + finalString.length());
            attributedText.addAttribute(TextAttribute.FOREGROUND, this.secondaryColor, indexs[i], indexs[i+1]); // this is the reason that we check if we have a pair or even amount of special chars
        }
        this.finalText = finalString; // this is the variable so that we can get the most recent version of our VSC (var special char) for length purposes used in our event text list
        gD.drawString(attributedText.getIterator(),this.xPos(),this.yPos());

    }

    public <T> void update(T... vars){ // update the variables in our variable array
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

    public String getReplacedMessage(){
        if(this.finalText == null){
            this.finalText = this.text;
        }
        return this.finalText;
    }

    public Color getColor(){
        return this.color;
    }

    public int fontSize(){
        return this.fontSize;
    }

    public void setFontSize(int size){
        this.fontSize = size;
    }

    public void setCentered(boolean shouldCenter){
        this.isCentered = shouldCenter;
    }

    public boolean isCentered(){
        return this.isCentered;
    }

}
