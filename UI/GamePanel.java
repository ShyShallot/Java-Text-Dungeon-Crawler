import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import java.util.Iterator;
import java.util.Map.Entry;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.PrintStream;

//https://www.youtube.com/watch?v=om59cwR7psI
public class GamePanel extends JPanel implements Runnable{
    
    final int originalTileSize = 16;
    final int scale = 3;

    final int tileSize = originalTileSize * scale;
    final int maxScreenCol = 16;
    final int maxScreenRow = 12;
    final int screenWidth = tileSize * maxScreenCol;
    final int screenHeight = tileSize * maxScreenRow;
    final int FPS = 60;
    private int drawCount;
    private double delta = 0;
    HashMap<String,int[]> positionBaseTable = new HashMap<>();
    
    ArrayList<UI> AllUIElems = new ArrayList<>();
    HashMap<UIAnimatable, UIAnim> animationQeue = new HashMap<>();
    HashMap<UISprite, UISpriteAnim> spriteQueue = new HashMap<>();
    ArrayList<QueuedText> TextNotifQueue = new ArrayList<>();

    //private JTextArea textArea = new JTextArea(15, 30);
    //private TextAreaOutputStream taOutputStream = new TextAreaOutputStream(textArea, "Test");

    Scanner input = new Scanner(System.in);
    Game game = new Game();

    private UIText fpsDisplay;
    private UIOverlay debugText;
    

    Thread drawThread;
    Thread gameThread;

    public GamePanel(){
        //System.out.println(screenWidth + "/" + screenHeight);
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.startGameThread();
        this.addKeyListener(Main.keyHandler);
        this.setFocusable(true);
        game.SetupInput(input);
        //add(new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        //System.setOut(new PrintStream(taOutputStream));
        positionBaseTable.put("damage_notif",new int[]{300,350});
        positionBaseTable.put("notif_area",new int[]{500,300});
    }

    public void startGameThread(){
        
        drawThread = new Thread(this);
        drawThread.start();
        gameThread = new Thread(game);
        gameThread.start();
        
    }

    @Override
    public void run(){

        fpsDisplay = new UIText(this, "%c", 100, 100, 40, 0);
        fpsDisplay.setColor(Color.white);
        if(Main.debugMode){
            debugText = new UIOverlay(this, "Art/Overlays/DEBUG.png", 0);
        }
        

        double drawInterval = 1000000000/FPS; // how long 1 frame should be
        
        long lastTime = System.nanoTime(); 
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        
        while(drawThread != null){ // as long as our draw thread exists

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval; // a value between 0 and 1 describing the time of 1 frame

            timer += (currentTime - lastTime);

            lastTime = currentTime;

            if(delta >= 1){
                update();
                repaint();
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000){
                fpsDisplay.update(drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update(){
        if(Input.wasKeyPressed("Enter")){
            System.out.println("enter was pressed");
        }
    }

    public void paintComponent(Graphics g){
        //this.removeAll(); // due to the way we draw things, we remove all UI Components, which are things like buttons before we draw.
        drawCount++;
        Graphics2D gD = (Graphics2D)g;
        gD.clearRect(0, 0, this.screenWidth, this.screenHeight);
        this.repaint();
        super.paintComponent(g);

        for(int i=6;i>=0;i--){
            for(int y=0;y<AllUIElems.size();y++){
                UI elem = AllUIElems.get(y);
                if(elem.layer != i){
                    continue;
                }
                if(elem.isDead()){
                    elem.destory();
                    AllUIElems.remove(y);
                    continue;
                }
                if(elem.isHidden()){
                    continue;
                }
                elem.draw(gD);
            }
        }

        //System.out.println(animationQeue.size());
        for(Map.Entry<UIAnimatable,UIAnim> entry : animationQeue.entrySet()){
            if(entry.getValue().isDone() && !entry.getValue().doesLoop()){
                System.out.println("Anim does not loop skipping");
                animationQeue.remove(entry.getKey());
                continue;
            }
            UI element = entry.getKey();
            UIAnim anim = entry.getValue();

            //double animDelta = ((double)anim.getAnimSpeed()/(double)FPS)*delta;
            int randIncrecment = 0;
            if(anim.isRandom()){
                randIncrecment = (int)(CusLib.randomNum(0.01, 0.1)*10); // for random scaling of an animations movement
            }
            if(drawCount % (anim.getAnimSpeed() + randIncrecment) == 0){ // this where animation speed is used, we modular the draw count by the animation speed, so that when its 0 it applies the keyframe
                if(anim.isAdditive()){
                    //System.out.println(anim.getKeyframeX());
                    element.setPos(element.xPos()+anim.getKeyframeX(), element.yPos()+anim.getKeyframeY());
                } else {
                    element.setPos(anim.getKeyframeX(), anim.getKeyframeY());
                }
                
                anim.incrementKeyframe();
            }
        }

        for(Map.Entry<UISprite, UISpriteAnim> entry : spriteQueue.entrySet()){ // this is for sprite animation, using indexs of the sprite sheet defined in the animation
            if(entry.getValue().isDone() && !entry.getValue().doesLoop()){
                spriteQueue.remove(entry.getKey());
                continue;
            }
            UISprite sprite = entry.getKey();
            UISpriteAnim anim = entry.getValue();

            if(drawCount % anim.getAnimSpeed() == 0){
                if(anim.getKeyframe() >= sprite.getParentSheet().getSpriteChunks().length){ // make sure that the index of the keyframe never exceeds the amount of the sprites
                    continue;
                }
                sprite.updateSprite(sprite.getParentSheet().getSprite(anim.getKeyframe()));
                anim.incrementKeyframe();
            }
        }

        checkTextQueue(gD);
        if(drawCount == FPS){ // this happens every second, not frame
            /*for(UIButton button : UIButtons){
                if(button.activated){
                    button.activated = false;
                }
            }*/
            drawCount = 0;
        }
    }

    public ArrayList<UIButton> getCreatedButtons(){ // to combine UIButtons and UIGraphicButtons
        ArrayList<UIButton> allButtons = new ArrayList<>();
        for(UI elem : AllUIElems){
            if(elem.getClass().getName().equals("UIButton") || elem.getClass().getName().equals("UIGraphicButton")){
                allButtons.add((UIButton)elem);
            }
        }
        return allButtons;
    }

    public void checkTextQueue(Graphics2D gD){
        for(int i=0;i<TextNotifQueue.size();i++){
            QueuedText textContainer = TextNotifQueue.get(i);
            UIText text = textContainer.getText();
            int position = textContainer.getQueuePosition(); // position in queue, used for the ypos
            int[] basePos = positionBaseTable.get("notif_area"); // base position of the text event area
            if(position > 4){ // make sure that we never display more than 5 text. since positions in queue start at 0
                break;
            }
            //System.out.println("Text " + text.ID() + " Position: " + position);
            int secondsLeft = textContainer.getSecondsLeft();
            //System.out.println(secondsLeft);
            text.hide(false);
            int textWidth = gD.getFontMetrics(new Font("Arial",Font.PLAIN,text.fontSize())).stringWidth(text.getReplacedMessage());
            int textHeight = (int)(new Font("Arial",Font.PLAIN,text.fontSize()).getLineMetrics(text.getReplacedMessage(),gD.getFontRenderContext()).getHeight());
            text.setFontSize(15);
            text.setPos(basePos[0], basePos[1]+(int)(textHeight*position));
            if(secondsLeft <= 2){ // when there is x amount of seconds left, start fading out our text
                text.setColor(new Color(text.getColor().getRed(),text.getColor().getGreen(),text.getColor().getBlue(),(int)(255*((double)secondsLeft/(double)7))));
            }
            int textSafeBounds = 25;
            int finalScreenWidth = screenWidth - textSafeBounds;
            if(basePos[0]+textWidth > finalScreenWidth){ // https://www.desmos.com/calculator/p4ml6y4m0m
                /*int a = basePos[0]; //Figuring out the conversion from desmos to reasonable java code
                int b = textWidth;
                int c = finalScreenWidth;
                int d = a+b;
                int f = d-c;
                int g = (d-f)-b;
                System.out.println(a +"\n"+b+"\n"+c+"\n"+d+"\n"+f+"\n"+(d-f)+"\n"+g);*/
                int x = ((basePos[0]+textWidth)-((basePos[0]+textWidth)-finalScreenWidth))-textWidth;
                //System.out.println(g + "/" + x);
                text.setX(x);
            }
            if(secondsLeft == 0){
                //System.out.println("removing text");
                text.destory();
                for(int y=i+1;y<TextNotifQueue.size();y++){ // this is to change the position of text in queue down one
                    //System.out.println("Old Position in Queue: " + TextNotifQueue.get(y).getQueuePosition());
                    TextNotifQueue.get(y).changePositionInQueue(TextNotifQueue.get(y).getQueuePosition()-1); // move down its position in queue
                    textHeight = (int)(new Font("Arial",Font.PLAIN,TextNotifQueue.get(y).getText().fontSize()).getLineMetrics(TextNotifQueue.get(y).getText().getMessage(),gD.getFontRenderContext()).getHeight());
                    TextNotifQueue.get(y).getText().setY(basePos[1]+(int)(textHeight*position)); // make sure that the text will be at the correct y pos
                    //System.out.println("New Position in Queue: " + TextNotifQueue.get(y).getQueuePosition());
                }
                TextNotifQueue.remove(i);
                break;
            }
            if(drawCount == FPS){
                TextNotifQueue.get(i).deIncrementSeconds();
            }
        }
    } 

    public void queueText(UIText text){
        TextNotifQueue.add(new QueuedText(text, 7, TextNotifQueue.size()));
    }
}


class QueuedText {
    private UIText text;
    private int secondsLeft;
    private int positionInQueue;

    public QueuedText(UIText text, int secondtoShow, int position){
        this.text = text;
        this.secondsLeft = secondtoShow;
        this.positionInQueue = position;
    }

    public UIText getText(){
        return this.text;
    }

    public int getQueuePosition(){
        return this.positionInQueue;
    }

    public int getSecondsLeft(){
        return this.secondsLeft;
    }

    public void deIncrementSeconds(){
        this.secondsLeft -= 1;
    }

    public void changePositionInQueue(int newPos){
        this.positionInQueue = newPos;
    }

}