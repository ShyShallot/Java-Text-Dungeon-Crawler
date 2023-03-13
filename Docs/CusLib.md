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
private ArrayList<String> queuedText;
```

## Static Functions

```java
int randomNum(int min, int max) // generates a random integer within a range

double linearInterp(double f, double min, double max, boolean override) // Linear Interpolation using a Min and Max and a Fraction Value (f), if f = =-0.5 and min = 0, and max = 1, the result will be 0.5 give or take, used by the AI functions

double randomNum(double min, double max) // Same as randomNum but returns a double

<T> String colorText(T text, String color) // input anything and it will be output with the color if it matches the input, Only Available colors are the ones defined above

String getColor(String color) // returns the Color Variable associated with the string, this is seperate due to earlier impentations of colorText.

<T> void DebugOutputLn(T text); // If Main.debugMode is true then it will output the text as a new Line

<T> void DebugOutput(T text); // Same as DebugOutputLn but only doesnt print a new line

<T> void advanceText(T text); // Wait to outputs the text until the player gives any input.

<T> void advanceTextLn(T text); // Same as advanceText but prints a new line.

<T> void queueText(T text); // Adds text to the queuedText array list.

void callQueue(boolean advance, boolean newLine); // Loops thru the queuedText list and if advance is true, waits for user input before printing the next text, and if newLine is true, prints to a new line.
```