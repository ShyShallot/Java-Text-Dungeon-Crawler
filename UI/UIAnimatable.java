public class UIAnimatable extends UI { // this is just a UI class that allows for animation, since all UI elements extend UI, we might have some that shouldnt animate, like buttons
    private int baseX,baseY;
    private UIAnimList animList = new UIAnimList();
    private boolean isAnimPlaying;

    public UIAnimatable(GamePanel  gp, int x, int y, int layer){
        super(gp,x,y, layer);
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
        if(this.getGP().animationQeue.containsKey(this)){
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
        this.getGP().animationQeue.put(this,anim);
        this.isAnimPlaying = true;
    }

    public void stopAnimation(){
        this.getGP().animationQeue.remove(this);
        this.setPos(baseX, baseY);
    }
}
