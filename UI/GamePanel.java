import java.util.*;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    
    ArrayList<UIImage> UIImages = new ArrayList<>();
    ArrayList<UINotifcation> UINotifcations = new ArrayList<>();
    ArrayList<UIText> UITexts = new ArrayList<>();
    ArrayList<UIButton> UIButtons = new ArrayList<>();
    ArrayList<UIGraphicButton> UIGraphicButtons = new ArrayList<>();
    ArrayList<UISquare> UISquares = new ArrayList<>();
    ArrayList<UISprite> UISprites = new ArrayList<>();
    HashMap<UIAnimatable, UIAnim> animationQeue = new HashMap<>();
    HashMap<UISprite, UISpriteAnim> spriteQueue = new HashMap<>();

    //private JTextArea textArea = new JTextArea(15, 30);
    //private TextAreaOutputStream taOutputStream = new TextAreaOutputStream(textArea, "Test");

    Scanner input = new Scanner(System.in);
    Game game = new Game();

    public UIText fpsDisplay;

    Thread drawThread;
    Thread gameThread;

    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.startGameThread();
        this.addKeyListener(Main.keyHandler);
        this.setFocusable(true);
        game.SetupInput(input);
        fpsDisplay = new UIText(this, "%c", 100, 100, 40, 0);
        fpsDisplay.setColor(Color.white);
        //add(new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
        //System.setOut(new PrintStream(taOutputStream));
        positionBaseTable.put("damage_notif",new int[]{300,200});
    }

    public void startGameThread(){
        drawThread = new Thread(this);
        drawThread.start();
        gameThread = new Thread(game);
        gameThread.start();
    }

    @Override
    public void run(){

        double drawInterval = 1000000000/FPS;
        
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        
        while(drawThread != null){

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;

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
        super.paintComponent(g);

        for(int i=0;i<UISquares.size();i++){
            UISquare square = UISquares.get(i);
            if(square.isDead()){
                UISquares.remove(i);
                continue;
            }
            square.draw(gD);
        }

        for(int i=0;i<UIButtons.size();i++){
            UIButton UIButton = UIButtons.get(i);
            if(UIButton.isDead()){
                //System.out.println(this.getComponentCount());
                this.remove(UIButton.getJButton());
                //System.out.println(this.getComponentCount());
                UIButtons.remove(i);
                this.repaint();
                continue;
            }
            if(UIButton.isHidden()){
                if(UIButton.getJButton() != null){
                    this.remove(UIButton.getJButton());
                }
                continue;
            }
            if(UIButton.getJButton() != null){
                if(UIButton.xPos() != UIButton.getJButton().getX() || UIButton.yPos() != UIButton.getJButton().getY()){
                    this.remove(UIButton.getJButton());
                    UIButton.getJButton().setBounds(UIButton.xPos(),UIButton.yPos(),UIButton.width(),UIButton.height());
                    this.add(UIButton.getJButton());
                    continue;
                }
                continue;
            }
            //System.out.println("Creating Button: " + i);
            JButton button = new JButton(UIButton.message());
            button.setBounds(UIButton.xPos(), UIButton.yPos(), (int)(UIButton.width()*UIButton.getScale()), (int)(UIButton.height()*UIButton.getScale()));
            button.setVerticalAlignment(SwingConstants.CENTER);
            button.setBackground(UIButton.getColor());
            button.setFont(new Font("Arial", Font.PLAIN,UIButton.getTextSize()));
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    System.out.println("BUTTON!");
                    Game.buttonPressed(UIButton);
                    UIButton.activated = true;
                }
            });
            UIButton.setJButton(button);
            this.add(button);
        }

        for(int i=0;i<UIGraphicButtons.size();i++){
            if(UIGraphicButtons.get(i).isDead()){
                this.remove(UIGraphicButtons.get(i).getJButton());
                UIGraphicButtons.remove(i);
                this.repaint();
                continue;
            }
            if(i >= UIGraphicButtons.size()){
                continue;
            }
            if(UIGraphicButtons.get(i).isHidden() || UIGraphicButtons.get(i).getJButton() != null){
                continue;
            }
            //System.out.println("Creating G Button: " + i);
            UIGraphicButton UIGButton = UIGraphicButtons.get(i);
            JButton button = new JButton();
            button.setBounds(UIGButton.xPos(), UIGButton.yPos(), (int)(UIGButton.width()*UIGButton.getScale()), (int)(UIGButton.height()*UIGButton.getScale()));
            button.setIcon(new ImageIcon(UIGButton.getImage().getImage().getScaledInstance((int)(UIGButton.getImage().getIconWidth()*UIGButton.getScale()),(int)(UIGButton.getImage().getIconHeight()*UIGButton.getScale()), Image.SCALE_SMOOTH))); // Scale Image
            button.setBackground(UIGButton.getColor());
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e){
                    System.out.println("BUTTON!");
                    Game.buttonPressed(UIGButton);
                    UIGButton.activated = true;
                }
            });
            this.add(button);
            UIGButton.setJButton(button);
        }

        for(int i=0;i<UIImages.size();i++){
            if(UIImages.get(i).isDead()){
                UIImages.remove(i);
                continue;
            }
            UIImages.get(i).draw(gD);
        }

        for(int i=0;i<UISprites.size();i++){
            if(UISprites.get(i).isDead()){
                UISprites.remove(i);
                continue;
            }
            UISprites.get(i).draw(gD);
        }

        //System.out.println(UITexts.size());
        for(int i=0;i<UITexts.size();i++){
            UIText curText = UITexts.get(i);
            if(curText.isDead()){
                UITexts.remove(i);
                continue;
            }
            if(curText.isHidden()){
                continue;
            }
            //System.out.println(curText.xPos());
            if(curText.isCentered()){
                //System.out.println("Centering Text: " + i);
                int textLength = (int)gD.getFontMetrics(new Font("Arial", Font.PLAIN, curText.fontSize())).stringWidth(curText.getMessage());
                //System.out.println(screenWidth + " " + textLength);
                curText.setX((int)((screenWidth-textLength)/2));
            }
            curText.draw(gD);
        }

        for(int i=0;i<UINotifcations.size();i++){
            if(UINotifcations.get(i).isDead()){
                UINotifcations.remove(i);
                continue;
            }
            UINotifcation notif = UINotifcations.get(i);
            CusLib.DebugOutputLn("Time Left: " + (notif.timeToShow() - (notif.time()/FPS)) + " Seconds.");
            if(notif.time() > notif.timeToShow()*FPS){ // Convert timeToShow which is in seconds to frames by multiplying by our FPS, ex: 4 seconds which is 4*60 = 240 frames
                notif.destory();
                continue;
            }
            if(notif.isCentered()){
                //System.out.println("Centering Text: " + i);
                int textLength = (int)gD.getFontMetrics(new Font("Arial", Font.PLAIN, notif.fontSize())).stringWidth(notif.getMessage());
                //System.out.println(screenWidth + " " + textLength);
                notif.setX((int)((screenWidth-textLength)/2));
            }
            notif.draw(gD);
            notif.incTime();
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
                randIncrecment = (int)(CusLib.randomNum(0.01, 0.1)*10);
            }
            if(drawCount % (anim.getAnimSpeed() + randIncrecment) == 0){
                if(anim.isAdditive()){
                    //System.out.println(anim.getKeyframeX());
                    element.setPos(element.xPos()+anim.getKeyframeX(), element.yPos()+anim.getKeyframeY());
                } else {
                    element.setPos(anim.getKeyframeX(), anim.getKeyframeY());
                }
                
                anim.incrementKeyframe();
            }
        }

        for(Map.Entry<UISprite, UISpriteAnim> entry : spriteQueue.entrySet()){
            if(entry.getValue().isDone() && !entry.getValue().doesLoop()){
                spriteQueue.remove(entry.getKey());
                continue;
            }
            UISprite sprite = entry.getKey();
            UISpriteAnim anim = entry.getValue();

            if(drawCount % anim.getAnimSpeed() == 0){
                if(anim.getKeyframe() >= sprite.getParentSheet().getSpriteChunks().length){
                    continue;
                }
                sprite.updateSprite(sprite.getParentSheet().getSprite(anim.getKeyframe()));
                anim.incrementKeyframe();
            }
        }

        if(drawCount == 60){
            for(UIButton button : UIButtons){
                if(button.activated){
                    button.activated = false;
                }
            }
            drawCount = 0;
        }
    }

    public ArrayList<UIButton> getCreatedButtons(){ // to combine UIButtons and UIGraphicButtons
        ArrayList<UIButton> allButtons = new ArrayList<>();
        allButtons.addAll(UIButtons);
        allButtons.addAll(UIGraphicButtons);
        return allButtons;
    }

}
