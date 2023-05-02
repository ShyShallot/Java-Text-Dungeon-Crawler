public class UI{

    private GamePanel gp;
    private int x,y;
    private int baseX,baseY;
    private boolean destory = false;
    private boolean hide = false;
    private int id;
    private UIAnimList animList = new UIAnimList();
    private boolean isAnimPlaying;

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

    public void setPos(int x, int y){
        this.x = x;
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

    public void addAnimation(String ref, UIAnim anim){
        this.animList.addAnimation(ref,anim);
    }

    public void removeAnimation(String ref){
        this.animList.removeAnimation(ref);
    }

    public UIAnimList getAnimationsList(){
        return this.animList;
    }

    public void playAnimation(String ref){
        if(Main.gp.animationQeue.containsKey(this)){
            return;
        }
        this.baseX = this.xPos();
        this.baseY = this.yPos();
        //if(this.isAnimPlaying){
        //    return;
        //}
        if(!this.animList.containsAnim(ref)){
            return;
        }
        UIAnim anim = this.animList.getAnimation(ref);
        Main.gp.animationQeue.put(this,anim);
        this.isAnimPlaying = true;
    }

    public void stopAnimation(){
        Main.gp.animationQeue.remove(this);
        this.setPos(baseX, baseY);
    }

}
