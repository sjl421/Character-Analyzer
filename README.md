# Lejos Robot Character Detection project
Technical Manual
Sujil Maharjan

## Class Description:

### EV3 Classes:
1.	**Robot.java**: It handles all of the functions that the Lejos EV3 robot performs. The functions help the robot to move forward or make turns. 
  i.	**Sound Functions**: soundON(), soundOFF(), soundSuccess(), soundFailure() produces different types of beeps varying the sounds when the pixel is on, off, success, failure.
  ii.	**Color sensor Functions**: startSensor(), recordON(), recordOFF(), readColor(), isON(), and readDistance() functions help start the color sensor, record color statistics for ON and OFF, read the color, check if the pixel is ON, and calibrate the size of the pixel. 
  iii.	**Travel Functions**: travelOne() function helps to move the robot to a 1 unit distance according to the size of the board and the pixel. 

### Java Classes:
1.	**FileAccess.java**: It handles the file access of the program. It accesses the file and fills in the rules into the algorithm so that we can start the algorithm functions.
2.	**Pixel.java**: It holds the coordinates of the box in the grid. As the definition suggests, it represents one pixel. It holds the coordinates of the pixel and whether the pixel is on or off.
3.	**RuleNode.java**: It handles the string parsing to parse the string from the file to actual rules of logic. It separates the Left Hand Side with the Right Hand Side and makes it available for the program to use anytime it needs.
4.	**Algorithm.java**: It handles the algorithm searches the user requests. 

### View Classes:
1.	**StartView.java**: It is the introductory view of the program. It gives the user the option to calibrate or scan.
2.	**MainView.java**: This is the view to let user choose which type of algorithm they want to run. The algorithms available are Forward and Backward search.
3.	**StartGameView.java**: This view handles the backward search options. If the backward search is pressed, it provides the option of strings the user wants to search and verify.

### AI Algorithms:
1.	**Forward search**: This search traverses through the table and updates the potential characters according to the ON or OFF value of the pixel. It reaches an ultimate decision following through the logic.
2.	**Backward search**: A character is provided to the algorithm which basically verifies if it is true according to the grid or not.

###Classes using AI algorithm:
1.	**Algorithm.java**: This class has forward() and backward() functions which performs the forward and backward searches.

#### BUG report:
1.	Sometimes when you go back, the screen does not clear up. 
2.	The light sensor cannot be too high. It produces anomalies in the data. 
3.	Cannot go back to menus once backward is pressed.

#### Additional features:
1.	If the user chooses to scan without calibrating in the first run, it automatically takes you to calibration option since it is very important to calibrate.

