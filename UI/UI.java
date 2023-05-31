import java.awt.Graphics2D;
import java.rmi.UnexpectedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UI{

    private GamePanel gp;
    private int x,y;
    private boolean destory = false;
    private boolean hide = false;
    private int id;
    private UI parent;
    private HashMap<String, UI> children = new HashMap<>(); // we use a hashmap for the children for string ref
    int layer;

    public UI(GamePanel gp, int x, int y, int layer){
        if(layer < 0 || layer > 6){
            System.out.println("ERROR! UI Layer is not within a valid range (0-6)!");
            System.exit(9);
        }
        this.gp = gp;
        this.x = x;
        this.y = y;
        if(this.getGP() != null){
            this.id = this.getGP().AllUIElems.size();
            this.getGP().AllUIElems.add(this);
        } else {
            this.id = 0;
        }
    }

    public void draw(Graphics2D gD){

    }

    public int getLayer(){
        return this.layer;
    }

    public void setLayer(int newLayer){
        if(layer > 6){
            layer = 6;
        }
        this.layer = newLayer;
    }

    public int xPos(){
        return this.x;
    }

    public void setX(int x){
        int lastX = this.x;
        this.x = x;
        int diff = this.x-lastX;
        if(this.children.size() != 0){ // this is to update the X Position of all our children
            for(Map.Entry<String,UI> entry : this.children.entrySet()){
                UI child = entry.getValue();
                child.setX(child.xPos() + diff);
            }
        }
    }

    public int yPos(){
        return this.y;
    }

    public void setY(int y){
        int lastY = this.y;
        this.y = y;
        int diff = this.y-lastY;
        if(this.children.size() != 0){ // same as setX but for the ypos
            for(Map.Entry<String,UI> entry : this.children.entrySet()){
                UI child = entry.getValue();
                child.setY(child.yPos() + diff);
            }
        }
    }

    public void setPos(int x, int y){
        int lastX = this.x;
        int lastY = this.y;
        this.x = x;
        this.y = y;
        int xDiff = this.x-lastX;
        int yDiff = this.y-lastY;
        if(this.children.size() != 0){
            for(Map.Entry<String,UI> entry : this.children.entrySet()){
                UI child = entry.getValue();
                child.setPos(child.x + xDiff, child.y + yDiff);
            }
        }
    }

    public GamePanel getGP(){
        return this.gp;
    }

    public boolean isDead(){
        return this.destory;
    }

    public void destory(){
        this.destory = true;
        if(this.children.size() > 0){ // go through and destory the children
            for(Map.Entry<String,UI> entry : this.children.entrySet()){
                entry.getValue().destory();
            }
        }
    }

    public void hide(boolean bool){
        this.hide = bool;
    }

    public boolean isHidden(){
        return this.hide;
    }

    public void setID(int n){
        this.id = n;
    }

    public int ID(){
        return this.id;
    }

    public UI getParent(){
        return this.parent;
    }

    public void setParent(UI parent, String ref){
        this.parent = parent;
        this.parent.addChild(this, ref);
    }

    private void addChild(UI child, String ref){ // private function as we shouldnt allow the outside to set a child, we only need to set a parent
        this.children.put(ref, child);
    }

    public UI getChild(String ref){
        if(!this.children.containsKey(ref)){
            return null;
        }
        return this.children.get(ref);
    }
}
