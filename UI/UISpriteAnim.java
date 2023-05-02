public class UISpriteAnim {
    private int[] animation;
    private int curKeyframe = 0;
    private int lastKeyFrame = 0;
    private boolean isPlaying = false;
    private boolean loop = false;
    private boolean done = false;
    private int speed; // 1 is every frame, 2 is every other frame, 3 is 1/3 of all frames and so on


    public UISpriteAnim(int[] anim, boolean looping, int animSpeed){
        this.animation = anim;
        this.loop = looping;
        if(animSpeed > Main.gp.FPS){
            animSpeed = Main.gp.FPS;
        }
        this.speed = animSpeed;
    }


    public int[] getAnimation(){
        return this.animation;
    }

    public int getCurKeyframe(){
        return this.curKeyframe;
    }


    public int getLastKeyframe(){
        return this.lastKeyFrame;
    }

    public boolean isPlaying(){
        return this.isPlaying;
    }

    public boolean doesLoop(){
        return this.loop;
    }

    public boolean isDone(){
        return this.done;
    }

    public int getAnimSpeed(){
        return this.speed;
    }


    public void incrementKeyframe(){
        this.lastKeyFrame = this.curKeyframe;
        this.curKeyframe++;
        if(this.curKeyframe == animation.length){
            this.curKeyframe = 0;
            if(!this.loop){
                this.done = true;
            }
        }
    }


    public int getKeyframe(){
        return (int)(this.getAnimation()[this.curKeyframe]);
    }

    public int getKeyframe(int keyFrame){
        if(keyFrame > this.animation.length || keyFrame < 0){
            return -1;
        }
        return this.animation[keyFrame];
    }


}
