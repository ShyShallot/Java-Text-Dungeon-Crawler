import java.util.HashMap;

public class UISpriteAnimList {
    private HashMap<String, UISpriteAnim> animations = new HashMap<>();

    public UISpriteAnimList(){
        
    }

    public void addAnimation(String reference, UISpriteAnim anim){
        if(animations.containsKey(reference)){
            return;
        }
        this.animations.put(reference,anim);
    }

    public void removeAnimation(String ref){
        if(!animations.containsKey(ref)){
            return;
        }
        animations.remove(ref);
    }

    public boolean containsAnim(String ref){
        if(this.animations.containsKey(ref)){
            return true;
        }
        return false;
    }

    public UISpriteAnim getAnimation(String ref){
        if(!animations.containsKey(ref)){
            return null;
        }
        return animations.get(ref);
    }

    public HashMap<String, UISpriteAnim> getAnimationsList(){
        return animations;
    }
}
