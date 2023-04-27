public class UIAnim {
    private int[][] animation; // Index = Keyframe, first index xPos, 2nd index yPos, ex: {4,5},{10,9}
    //                                                                                     X Y   X  Y
    private int curKeyframe = 0;
    private boolean isPlaying = false;
    private boolean loop = false;
    private boolean done = false;
    private int speed; // 1 is every frame, 2 is every other frame, 3 is 1/3 of all frames and so on
    private double scale = 1;


    public UIAnim(int[][] anim, boolean looping, int animSpeed){
        this.animation = anim;
        this.loop = looping;
        if(animSpeed > Main.gp.FPS){
            animSpeed = Main.gp.FPS;
        }
        this.speed = animSpeed;
    }

    public UIAnim(int[][] anim, boolean looping, int animSpeed, double scale){
        this(anim,looping,animSpeed);
        this.scale = scale;
    }

    public int[][] getAnimation(){
        return this.animation;
    }

    public int getCurKeyframe(){
        return this.curKeyframe;
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

    public double getScale(){
        return this.scale;
    }

    public void incrementKeyframe(){
        this.curKeyframe++;
        if(this.curKeyframe == animation.length){
            this.curKeyframe = 0;
            if(!this.loop){
                this.done = true;
            }
        }
    }
}
