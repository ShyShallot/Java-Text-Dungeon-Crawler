import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

public class Input implements KeyListener{

    public static HashMap<String, Boolean> expectedInput = new HashMap<>();


    @Override
    public void keyTyped(KeyEvent e){

    }

    @Override
    public void keyPressed(KeyEvent e){
        String code = KeyEvent.getKeyText(e.getKeyCode());

        //System.out.println(code);

        if(expectedInput.containsKey(code)){
            expectedInput.replace(code,true);
        } else {
            expectedInput.put(code,true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e){
        String code = KeyEvent.getKeyText(e.getKeyCode());

        //System.out.println(code);

        if(expectedInput.containsKey(code)){
            expectedInput.replace(code,false);
        } else {
            expectedInput.put(code,false);
        }
    }

    public static boolean wasKeyPressed(String key){
        if(!expectedInput.containsKey(key)){
            return false;
        }
        return expectedInput.get(key);
    }
    
}
