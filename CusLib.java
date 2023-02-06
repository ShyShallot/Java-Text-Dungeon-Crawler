import java.util.*;
public class CusLib {
    public static final String RESET = "\u001B[0m";
	public static final String RED = "\u001B[31m";
	public static final String GREEN = "\u001B[32m";
	public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";
    public static int percentage(int part, int total){
        return (int)(((double)part/(double)total) * 100);
    }

    public static int randomNum(int min, int max){
		return (int)Math.floor(Math.random()*(max-min+1)+min);
	}
    //param f is the interpolation fraction, typically between 0 and 1, if you purposely want the min to be bigger than the max (or vice versa) then set the override to true
    public static double linearInterp(double f, double min, double max, boolean override){ // From http://www.java2s.com/example/java/java.lang/computes-a-linear-interpolation-between-two-values.html
        if(!override){
            double tempMin = 0.0;
            double tempMax = 0.0;
            if(min > max){
               // System.out.println("Fixing Min Max's");
                tempMin = max;
                tempMax = min;
                max = tempMax;
                min = tempMin;
            }
        }   
        //System.out.println(String.format("Fraction: %s, Min: %s, Max: %s",f,min,max));
        //System.out.println(max + f * (max-min));
        return max + f * (max-min);
    }

    public static double randomNum(double min, double max){
        double ran = Math.random()*(max-min+1)+min;
        if(ran < min){
            ran = min;
        }
        if(ran > max){
            ran = max;
        }
		return ran;
	}


    public static <T> String colorText(T text, String color){
        String finalColor = getColor(color);
        return String.format("%s%s%s",finalColor,text,RESET);
    }

    public static String getColor(String color){
        String finalColor = "";
        switch(color.toLowerCase()){
            case "red":
                finalColor = RED;
                break;
            case "green":
                finalColor = GREEN;
                break;
            case "yellow":
                finalColor = YELLOW;
                break;
            case "white":
                finalColor = WHITE;
                break;
            case "blue":
                finalColor = BLUE;
                break;
            case "cyan":
                finalColor = CYAN;
                break;
            case "purple":
                finalColor = PURPLE;
                break;
        }
        return finalColor;
    }
    

}
