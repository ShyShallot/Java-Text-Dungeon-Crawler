public class UIAnim {
    private int[][] animation; // Index = Keyframe, first index xPos, 2nd index yPos, ex: {4,5},{10,9}
    //                                                                                     X Y   X  Y
    private int curKeyframe = 0;
    private double subKeyFrame = 0;
    private int lastKeyFrame = 0;
    private boolean isPlaying = false;
    private boolean loop = false;
    private boolean done = false;
    private boolean random = false;
    private double randomFrequency;
    private int speed; // 1 is every frame, 2 is every other frame, 3 is 1/3 of all frames and so on
    private double scale = 1;
    private boolean isAdditive = false;


    public UIAnim(int[][] anim, boolean looping, boolean additive, int animSpeed){
        this.animation = anim;
        this.loop = looping;
        if(animSpeed > Main.gp.FPS){
            animSpeed = Main.gp.FPS;
        }
        this.speed = animSpeed;
        this.isAdditive = additive;
    }

    public UIAnim(int[][] anim, boolean looping, boolean additive, int animSpeed, double scale){
        this(anim,looping,additive,animSpeed);
        this.scale = scale;
    }

    public UIAnim(int[][] anim, int animSpeed, boolean additive, double scale, double randomFreq){
        this(anim,true,additive,animSpeed,scale);
        this.random = true;
        if(randomFreq > 1.0){
            randomFreq = 1.0;
        }
        if(randomFreq < 0.0){
            randomFreq = 0.0;
        }
        this.randomFrequency = randomFreq;
    }

    public int[][] getAnimation(){
        return this.animation;
    }

    public int getCurKeyframe(){
        return this.curKeyframe;
    }

    public double getCurSubKeyframe(){
        return this.subKeyFrame;
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

    public double getScale(){
        return this.scale;
    }

    public void setScale(double scale){
        this.scale = scale;
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

    public void incrementSubKeyframe(){
        if(this.subKeyFrame >= 1.0){
            this.subKeyFrame = 0.0;
        }
        this.subKeyFrame += 0.1;
    }

    public int getKeyframeX(){
        return (int)(this.getAnimation()[this.curKeyframe][0]*this.scale);
    }

    public int getKeyframeX(int keyFrame){
        if(keyFrame > this.animation.length || keyFrame < 0){
            return -1;
        }
        return this.animation[keyFrame][0];
    }

    public int getKeyframeY(){
        return (int)(this.getAnimation()[this.curKeyframe][1]*this.scale);
    }

    public int getKeyframeY(int keyFrame){
        if(keyFrame > this.animation.length || keyFrame < 0){
            return -1;
        }
        return this.animation[keyFrame][1];
    }

    public boolean isRandom(){
        return this.random;
    }

    public double randomFreq(){
        return this.randomFrequency;
    }

    public boolean isAdditive(){
        return this.isAdditive;
    }
}
