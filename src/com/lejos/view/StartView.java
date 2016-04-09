/************************************************************
 * Name:  Sujil Maharjan                                    *
 * Project:  Project 4/Lejos Robot			               *
 * Class:  Artificial Intelligence/CMP 331                  *
 * Date:  4/8/2016			                               *
 ************************************************************/
package com.lejos.view;

import com.lejos.controller.Robot;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

/**
 * It is the initial window to the game
 * @author Sujil Maharjan
 *
 */
public class StartView {
	// It holds the cursor position and the actual cursor
	private final int CURSOR_X = 10;
	private final String CURSOR = "<";
	
	// Initializes the scan value as false
	private boolean isScan = false;
	
	// Initializes the robot
	private Robot robot = new Robot();
	
	// Initializes the calibration to be false at the beginning
	private boolean calibrated = false;
	
	/**
	 * Constructor: It initiates the Menu
	 */
	public StartView() {
		initiateMenu();
		
	}
	
	/**
	 * It initiates the menu and lets the user choose between calibration and scan
	 */
	public void initiateMenu() {
		LCD.clear();
		
		int x_coord = 0;
		int y_coord = 0;
		
		LCD.drawString(CURSOR, CURSOR_X, 0); 
		
		
		
		while (true) {
			
			LCD.drawString("Calibrate", x_coord, y_coord);
			LCD.drawString("Scan", x_coord, y_coord+1);
			
			// Ask for user to press the button and handle the tasks accordingly
			int button = Button.waitForAnyPress();
			
			if (button == Button.ID_DOWN && !isScan) {
				LCD.drawString(" ", CURSOR_X, 0);
				LCD.drawString(CURSOR, CURSOR_X, 1);
				isScan = !isScan;
			}
			else if (button == Button.ID_UP && isScan) {
				LCD.drawString(" ", CURSOR_X, 1);
				LCD.drawString(CURSOR, CURSOR_X, 0);
				isScan = !isScan;
			}
			else if (button == Button.ID_ENTER) {
				// Check if calibration is pressed. If it is pressed, then run calibration first. Then, fill the Robot values and then run the MainView
				if (!isScan) {
					// Run calibration
					calibrate();
				}
				else {
					if (!calibrated) {
						calibrate();
					}
					else {
						MainView mainView = new MainView("text.txt", robot);
						LCD.clear();
					}
				}
			}
			else if (button == Button.ID_ESCAPE) {
				break;
			}
		}
	}
	
	/**
	 * Handles the calibration of the robot with the board
	 */
	public void calibrate() {
		LCD.clear();
		
		// Starts the color sensor
		robot.startSensor();
		
		LCD.drawString("Enter for ON",0,0);
		
		// Waits for user to press the button every time they are ready for the calculation
		int buttonID = Button.waitForAnyPress();
		
		if (buttonID == Button.ID_ESCAPE) {
			return;
		}
		else if (buttonID == Button.ID_ENTER) {
			robot.recordON();
		}
		
		LCD.clear();
		LCD.drawString("Enter for OFF",0,0);
		buttonID = Button.waitForAnyPress();
		
		if (buttonID == Button.ID_ESCAPE) {
			return;
		}
		else if (buttonID == Button.ID_ENTER) {
			robot.recordOFF();
		}
		
		LCD.clear();
		LCD.drawString("Enter for distance",0,0);
		buttonID = Button.waitForAnyPress();
		
		if (buttonID == Button.ID_ESCAPE) {
			return;
		}
		else if (buttonID == Button.ID_ENTER) {
			// It computes the distance of the pixel (pixel size)
			robot.readDistance();
		}
		
		
		calibrated = true;
		LCD.drawString("Calibrated!!",0,2);
		
		Delay.msDelay(1000);
		
		//LCD.clear();
		initiateMenu();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StartView start = new StartView();
	}
}
