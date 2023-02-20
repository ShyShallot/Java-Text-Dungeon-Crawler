This a class which contains all the Global Functions in one place

## Static Variables
```java
final String RESET
final String RED
final String GREEN
final String YELLOW
final String BLUE
final String PURPLE
final String CYAN
final String WHITE // These colors are for printing colored text and are used by the CusLib.colorText
```

## Static Functions

```java
int randomNum(int min, int max) // generates a random integer within a range

double linearInterp(double f, double min, double max, boolean override) // Linear Interpolation using a Min and Max and a Fraction Value (f), if f = =-0.5 and min = 0, and max = 1, the result will be 0.5 give or take, used by the AI functions

double randomNum(double min, double max) // Same as randomNum but returns a double

<T> String colorText(T text, String color) // input anything and it will be output with the color if it matches the input, Only Available colors are the ones defined above
```