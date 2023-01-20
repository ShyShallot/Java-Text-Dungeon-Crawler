import java.util.*;
public class Types {
    public static ArrayList<Type> typeLib = new ArrayList<>();

    public static void createTypeLib(){
        typeLib.add(new Mage());
        typeLib.add(new Skeleton());
        typeLib.add(new Knight());
        typeLib.add(new Goblin());
        typeLib.add(new Hog());
        typeLib.add(new Spider());
    }

    public static Type getTypeFromName(String name){
        Type returnType = null;
        for(int i=0; i<typeLib.size();i++){
            Type type = typeLib.get(i);
            //System.out.println(type.getName() + ", " + name);
            if(type.getName().toLowerCase().equals(name.toLowerCase())){
                returnType = type;
            }
        }
        return returnType;
    }
}
