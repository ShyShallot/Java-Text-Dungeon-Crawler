import javax.swing.JFrame;

public class BaseFrame extends JFrame{
    public GamePanel gamePanel = new GamePanel();

    public void frame(){
        JFrame window = new JFrame();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle("Java Dungeon Crawler");

        window.add(gamePanel);

        window.pack();
        
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
}