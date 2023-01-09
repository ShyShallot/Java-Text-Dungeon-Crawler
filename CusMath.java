import java.util.*;
public class CusMath {
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
                System.out.println("Fixing Min Max's");
                tempMin = max;
                tempMax = min;
                max = tempMax;
                min = tempMin;
            }
        }   
        System.out.println(String.format("Fraction: %s, Min: %s, Max: %s",f,min,max));
        System.out.println(max + f * (max-min));
        return max + f * (max-min);
    }

    public static double random(double max){
        double ran = Math.random()-(max/2);
        if(ran > max){
            ran = max;
        }
        return ran;
    }

}
