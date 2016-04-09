/************************************************************
 * Name:  Sujil Maharjan                                    *
 * Project:  Project 4/Lejos Robot			               *
 * Class:  Artificial Intelligence/CMP 331                  *
 * Date:  4/8/2016			                               *
 ************************************************************/
package com.lejos.view;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.lejos.controller.Robot;
import com.lejos.model.Algorithm;
import com.lejos.model.FileAccess;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.Font;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.lcd.LCD;

/**
 * It is the view for letting users select the search type
 * @author Sujil Maharjan
 *
 */
public class MainView {
	// Initialize forward search selection as true
	private boolean isForward = true;
	
	// It holds the Cursor position in the LED and the actual cursor 
	private final int CURSOR_X = 10;
	public final static String CURSOR = "<";
	
	/**
	 * Constructor
	 * @param fileName Name of the text file
	 * @param robot It holds the robot object
	 */
	public MainView(String fileName, Robot robot) {
		// Initiates the menu on the screen
		initiateMenu(robot);
		
	}
		
	/**
	 * It initiates the menu on the screen
	 * @param robot It holds the robot object
	 */
	public void initiateMenu(Robot robot) {
		LCD.clear();
		
		// Draw the cursor
		LCD.drawString(CURSOR, CURSOR_X, 0);
		while (true) {
			//LCD.clear();
			LCD.drawString("Forward", 0, 0);
			LCD.drawString("Backward", 0, 1);
			
			// Wait for the user to press the button
			int buttonPressed = Button.waitForAnyPress();
			
			// Check which button is pressed and handle the tasks accordingly
			if (buttonPressed == Button.ID_DOWN && isForward) {
				clearCursor();
				LCD.drawString(CURSOR, CURSOR_X, 1);
				isForward = !isForward;
			}
			else if (buttonPressed == Button.ID_UP && !isForward) {
				clearCursor();
				LCD.drawString(CURSOR, CURSOR_X, 0);
				isForward = !isForward;
			}
			else if (buttonPressed == Button.ID_ENTER) {
				// Starts the game if enter is pressed
				StartGameView startGame = new StartGameView(isForward, robot);
				LCD.clear();
			}
			else if (buttonPressed == Button.ID_ESCAPE) {
				break;
			}
		}
	
	}
	
	/**
	 * It clears the cursor from the screen
	 */
	public void clearCursor() {
		LCD.drawString(" ", CURSOR_X, 0);
		LCD.drawString(" ", CURSOR_X, 1);
	}
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		MainView mainview = new MainView("text.txt", new Robot());
		
		
	}
	
	
}
