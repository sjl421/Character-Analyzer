/************************************************************
 * Name:  Sujil Maharjan                                    *
 * Project:  Project 4/Lejos Robot			               *
 * Class:  Artificial Intelligence/CMP 331                  *
 * Date:  4/8/2016			                               *
 ************************************************************/
package com.lejos.view;

import java.util.ArrayList;

import com.lejos.controller.Robot;
import com.lejos.model.Algorithm;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.lcd.LCD;
import lejos.utility.TextMenu;

/**
 * It is the view for backward search which allows the user to pick between various values in the option
 * @author Sujil Maharjan
 *
 */
public class StartGameView {
	// It holds the characters that are present in the rule
	private ArrayList<String> characters = new ArrayList<String>();
	private boolean isForward;
	
	// It holds the start location of the cursor
	private final int CURSOR_X_START = 2;
		
	// It holds the algorithm object
	Algorithm algo;
	
	/**
	 * Constructor to start the view
	 * @param forward It holds if the search type is forward or backward
	 * @param robot It holds the robot object
	 */
	public StartGameView(boolean forward, Robot robot) {
		// Initiate file access and everything
		
		algo = new Algorithm("text.txt", robot);
		
		characters = algo.getRuleValues();
		
		isForward = forward;
		
		startGame(robot);
	}
	
	/**
	 * It starts the game
	 * @param robot It holds the robot object
	 */
	private void startGame(Robot robot) {
		// Checks if the user pressed forward or backward and starts the game accordingly
		if (isForward) {
			startForward(robot);
		}
		else {
			startBackward(robot);
		}
		LCD.clear();
	}
	
	/**
	 * Starts the forward search
	 * @param robot It holds the robot object
	 */
	private void startForward(Robot robot) {
		algo.forward(robot);
	}
	
	/**
	 * It starts the backward search
	 * @param robot It holds the robot object
	 */
	private void startBackward(Robot robot) {
		LCD.clear();
		int cursorIndex=0;
		
		// Display the choices
		String[] chars = new String[characters.size()];
		
		for (int i=0; i < characters.size(); ++i) {
			chars[i] = characters.get(i);
		}
		
		LCD.drawString(MainView.CURSOR, CURSOR_X_START, cursorIndex);
		
		int testsNumber =0;
		
		while (!Button.ENTER.isDown()) {
			TextMenu testsMenu = new TextMenu(chars, 1, "Select a test");
			testsNumber = testsMenu.select();
			
						
		}
		
		// At this point, cursor index points to the character we want
		String testValue = characters.get(testsNumber);
		
		// Temporarily perform the backward search for C
		LCD.clear();
		
		algo.backward(testValue, robot);
		
		Button.waitForAnyPress();
		
		
	}
}
