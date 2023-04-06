import java.util.*;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
    
    ArrayList<UIImage> UIImages = new ArrayList<>();
    ArrayList<UINotifcation> UINotifcations = new ArrayList<>();
    ArrayList<UIText> UITexts = new ArrayList<>();
    ArrayList<UIButton> UIButtons = new ArrayList<>();

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
        fpsDisplay = new UIText(this, "", 100, 100, 40);
        fpsDisplay.setColor(Color.white);
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
        double delta = 0;
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

            //System.out.println(timer);
            if(timer >= 1000000000){
                fpsDisplay.updateText(drawCount);
                drawCount = 0;
                timer = 0;
            }
            

        }
    }

    public void update(){
        if(Input.wasKeyPressed("Enter")){
            System.out.println("enter was pressed");
        }
        if(Input.wasKeyPressed("A")){
            System.out.println("A was pressed");
        }
        //game.update();
    }

    public void paintComponent(Graphics g){
        this.removeAll(); // due to the way we draw things, we remove all UI Components, which are things like buttons before we draw.
        drawCount++;
        super.paintComponent(g);

        Graphics2D gD = (Graphics2D)g;

        for(int i=0;i<UIButtons.size();i++){
            if(UIButtons.get(i).isDead()){
                UIButtons.remove(i);
                this.repaint();
                continue;
            }
            //System.out.println("Creating Button: " + i);
            UIButton UIButton = UIButtons.get(i);

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
            this.add(button);
        }

        for(int i=0;i<UIImages.size();i++){
            if(UIImages.get(i).isDead()){
                UIImages.remove(i);
                continue;
            }
            UIImages.get(i).draw(gD);
        }

        //System.out.println(UITexts.size());
        for(int i=0;i<UITexts.size();i++){
            UIText curText = UITexts.get(i);
            if(curText.isDead()){
                UITexts.remove(i);
                continue;
            }
            //System.out.println(curText.xPos());
            if(curText.isCentered()){
                //System.out.println("Centering Text: " + i);
                int textLength = (int)gD.getFontMetrics(new Font("Arial", Font.PLAIN, curText.fontSize())).stringWidth(curText.getMessage());
                //System.out.println(screenWidth + " " + textLength);
                curText.setX((int)((screenWidth-textLength)/2));
            }
            //System.out.println(curText.xPos());
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
        if(drawCount == 60){
            for(UIButton button : UIButtons){
                if(button.activated){
                    button.activated = false;
                }
            }
            drawCount = 0;
        }
        //gD.dispose();
    }

}
