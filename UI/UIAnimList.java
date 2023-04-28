import java.util.HashMap;

public class UIAnimList {
    private HashMap<String, UIAnim> animations = new HashMap<>();

    public UIAnimList(){
        
    }

    public void addAnimation(String reference, UIAnim anim){
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

    public UIAnim getAnimation(String ref){
        if(!animations.containsKey(ref)){
            return null;
        }
        return animations.get(ref);
    }

    public HashMap<String, UIAnim> getAnimationsList(){
        return animations;
    }
}
