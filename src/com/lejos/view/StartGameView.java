package com.lejos.view;

import com.lejos.model.Algorithm;

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.lcd.GraphicsLCD;
import lejos.hardware.lcd.LCD;

public class StartGameView {
	private String[] characters = {"A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
	private boolean isForward;
	
	private int menu_x = 0;
	private final int CURSOR_X_START = 2;
	
	Algorithm algo;
	
	public StartGameView(boolean forward) {
		// Initiate file access and everything
		
		algo = new Algorithm("text.txt");
		
		isForward = forward;
		
		startGame();
		
		
	}
	
	private void startGame() {
		if (isForward) {
			startForward();
		}
		else {
			startBackward();
		}
	}
	
	private void startForward() {
		algo.forward();
	}
	
	private void startBackward() {
		LCD.clear();
		int cursorIndex=0;
		// Display the choices
		LCD.scroll();
		for (int i=0; i < characters.length; ++i) {
			System.out.println(characters[i]);
		}
		
		LCD.drawString(MainView.CURSOR, CURSOR_X_START, cursorIndex);
		
		while (true) {
			int buttonPressed = Button.waitForAnyPress();
			
			if (buttonPressed == Button.ID_ESCAPE) {
				
				break;
			}
			else if(buttonPressed == Button.ID_DOWN && cursorIndex < characters.length) {
				LCD.drawString(" ", CURSOR_X_START, cursorIndex);
				cursorIndex++;
				
				LCD.drawString(MainView.CURSOR, CURSOR_X_START, cursorIndex);
			}
			else if (buttonPressed == Button.ID_UP ) {
				LCD.drawString(" ", CURSOR_X_START, cursorIndex);
				cursorIndex--;
				//LCD.scroll();
				LCD.drawString(MainView.CURSOR, CURSOR_X_START, cursorIndex);
			}
			else if (buttonPressed == Button.ID_ENTER) {
				// Then get the character we want to compare and break from the loop
				break;
			}
			
		}
		
		// At this point, cursor index points to the character we want
		String testValue = characters[cursorIndex];
		
		// Temporarily perform the backward search for C
		LCD.clear();
		
		algo.backward("C");
		
		Button.waitForAnyPress();
		
		
	}
}
