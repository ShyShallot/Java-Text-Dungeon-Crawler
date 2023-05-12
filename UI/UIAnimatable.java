public class UIAnimatable extends UI {
    private int baseX,baseY;
    private UIAnimList animList = new UIAnimList();
    private boolean isAnimPlaying;

    public UIAnimatable(GamePanel  gp, int x, int y){
        super(gp,x,y);
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
