package com.lejos.view;

import java.util.ArrayList;

import com.lejos.controller.Robot;
import com.lejos.model.Algorithm;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.lcd.LCD;
import lejos.utility.TextMenu;

public class StartGameView {
	private ArrayList<String> characters = new ArrayList<String>();
	private boolean isForward;
	
	private int menu_x = 0;
	private final int CURSOR_X_START = 2;
	
	
	Algorithm algo;
	//private Robot robot;
	
	public StartGameView(boolean forward, Robot robot) {
		// Initiate file access and everything
		
		algo = new Algorithm("text.txt", robot);
		
		characters = algo.getRuleValues();
		
		isForward = forward;
		
		//this.robot = robot;
		
		startGame(robot);
		
		
	}
	
	private void startGame(Robot robot) {
		if (isForward) {
			startForward(robot);
		}
		else {
			startBackward(robot);
		}
	}
	
	private void startForward(Robot robot) {
		algo.forward(robot);
	}
	
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
			//int buttonPressed = Button.waitForAnyPress();
			
//			if (buttonPressed == Button.ID_ESCAPE) {
//				
//				break;
//			}
//			else if(buttonPressed == Button.ID_DOWN && cursorIndex < characters.size()) {
//				LCD.drawString(" ", CURSOR_X_START, cursorIndex);
//				cursorIndex++;
//				LCD.drawString(MainView.CURSOR, CURSOR_X_START, cursorIndex);
//			}
//			else if (buttonPressed == Button.ID_UP ) {
//				LCD.drawString(" ", CURSOR_X_START, cursorIndex);
//				cursorIndex--;
//				LCD.drawString(MainView.CURSOR, CURSOR_X_START, cursorIndex);
//			}
//			else if (buttonPressed == Button.ID_ENTER) {
//				// Then get the character we want to compare and break from the loop
//				break;
//			}
			
		}
		
		// At this point, cursor index points to the character we want
		String testValue = characters.get(testsNumber);
		
		// Temporarily perform the backward search for C
		LCD.clear();
		
		algo.backward(testValue, robot);
		
		Button.waitForAnyPress();
		
		
	}
}
