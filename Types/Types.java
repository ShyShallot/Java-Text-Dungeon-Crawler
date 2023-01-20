import java.util.*;
public class Types {
    public static ArrayList<Type> typeLib = new ArrayList<>();

    public static void createTypeLib(){
        typeLib.add(new Mage());
        typeLib.add(new Skeleton());
    }

    public static Type getTypeFromName(String name){
        Type returnType = new Type();
        for(int i=0; i<typeLib.size();i++){
            Type type = typeLib.get(i);
            if(type.getName().equals(name)){
                returnType = type;
            }
        }
        return returnType;
    }
}
