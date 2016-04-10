# Lejos Robot Character Detection project
Technical Manual
Sujil Maharjan

## Class Description:

### EV3 Classes:
a.	Robot.java: It handles all of the functions that the Lejos EV3 robot performs. The functions help the robot to move forward or make turns. 
  I.	Sound Functions: soundON(), soundOFF(), soundSuccess(), soundFailure() produces different types of beeps varying the sounds when the pixel is on, off, success, failure.
  II.	Color sensor Functions: startSensor(), recordON(), recordOFF(), readColor(), isON(), and readDistance() functions help start the color sensor, record color statistics for ON and OFF, read the color, check if the pixel is ON, and calibrate the size of the pixel. 
  III.	Travel Functions: travelOne() function helps to move the robot to a 1 unit distance according to the size of the board and the pixel. 

Java Classes:
a.	FileAccess.java: It handles the file access of the program. It accesses the file and fills in the rules into the algorithm so that we can start the algorithm functions.
b.	Pixel.java: It holds the coordinates of the box in the grid. As the definition suggests, it represents one pixel. It holds the coordinates of the pixel and whether the pixel is on or off.
c.	RuleNode.java: It handles the string parsing to parse the string from the file to actual rules of logic. It separates the Left Hand Side with the Right Hand Side and makes it available for the program to use anytime it needs.
d.	Algorithm.java: It handles the algorithm searches the user requests. 

View Classes:
a.	StartView.java: It is the introductory view of the program. It gives the user the option to calibrate or scan.
b.	MainView.java: This is the view to let user choose which type of algorithm they want to run. The algorithms available are Forward and Backward search.
c.	StartGameView.java: This view handles the backward search options. If the backward search is pressed, it provides the option of strings the user wants to search and verify.

AI Algorithms:
a.	Forward search: This search traverses through the table and updates the potential characters according to the ON or OFF value of the pixel. It reaches an ultimate decision following through the logic.
b.	Backward search: A character is provided to the algorithm which basically verifies if it is true according to the grid or not.

Classes using AI algorithm:
a.	Algorithm.java: This class has forward() and backward() functions which performs the forward and backward searches.

BUG report:
a.	Sometimes when you go back, the screen does not clear up. 
b.	The light sensor cannot be too high. It produces anomalies in the data. 
c.	Cannot go back to menus once backward is pressed.

Missing features:
a.	All of the features are included.

Additional features:
a.	If the user chooses to scan without calibrating in the first run, it automatically takes you to calibration option since it is very important to calibrate.

LOG: (Total: 24 hours)
4/6/2016 (10 hours)
-	PROBLEMS: file was not reading
-	Solved it as I was trying to do normal file reading instead of getting it from the resource. So, did that and got the result. Always put the text file in /bin.
-	Made MainView that will show if we should go for forward or backward search.
-	Made a StartView that will show the relative option for forward or backward search. It will not show anything for forward as we don’t need any options.
-	Made a Robot class under the Controller package. It will save the Lejos informations such as travel info and everything after the calibration. Also, made travelOne() function that will move the lejos to 8 by 6 grid automatically detecting where to move. So, it will go straight, right, or left automatically.
-	Managed the place of Algorithm and FileAccess in the view. So, FileAccess will be internally handled by algorithms class. Algorithm will call fileAccess and read all of the rules. Also, addRule() is static since we will only be reading the file one time during the whole program.
-	Made forward search to function properly. Made a possibleRules list and rejected list to take in possible values and also the values thrown out as we read in the pixels.
-	If the read pixel is off but a rule needs it ON, then that rule is eliminated from possibleRules arraylist. So, this way it is added to rejected list. 
-	TO DO: Make a new front screen with calibration and scan options.
-	TODO: Enable robotic characteristics in the program. Right now, algorithms work but there are no robotic features really.
4/7/2016 (6 hours)
-	Added the Robot class and included all of the sound and travel functions in it. 
-	Now, we can easily calibrate since we have to press enter to start any sort of calibration. 
-	The movement was very weird when I tried serpentine movement in the board since it was destroying the algorithm tactics. Instead of changing the algorithm, I just changed the way the board is read.
-	When the entire column is read, then the robot moves back and then starts over again. It does it by row-major.
-	Problem: It was not detecting ON and OFF properly. I put in the lower and upper bound. However, I just put upper and lower bound to be 20% different than the main value. So, while reading the board, it was not inside the range. So, it was creating the problem.
-	Solved it by just putting either upper or lower end depending on which one is ON (high).
-	Distance calibration was an anomaly. Fixed it by increasing/decreasing the upper/lower bound. 
4/8/2016 (8 hours)
-	Fixed all of the printing errors. I was printing through System.out.println() due to which LCD.clear() was not working. So, I fixed it by printing everything by LCD.drawString().
-	Fixed all of the display. I used TextMenu to fix the menu for backward search options. Otherwise, it was not scrolling.

