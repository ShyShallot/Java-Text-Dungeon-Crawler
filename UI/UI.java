public class UI{

    private GamePanel gp;
    private int x,y;
    private boolean destory = false;
    private int id;

    public UI(GamePanel gp, int x, int y){
        this.gp = gp;
        this.x = x;
        this.y = y;
    }

    public int xPos(){
        return this.x;
    }

    public void setX(int x){
        this.x = x;
    }

    public int yPos(){
        return this.y;
    }

    public void setY(int y){
        this.y = y;
    }

    public GamePanel getGP(){
        return this.gp;
    }

    public boolean isDead(){
        return this.destory;
    }

    public void destory(){
        this.destory = true;
    }

    public void revive(){
        this.destory = false;
    }

    public void setID(int n){
        this.id = n;
    }

    public int ID(){
        return this.id;
    }

}
