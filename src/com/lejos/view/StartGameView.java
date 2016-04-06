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
	
	public StartGameView(boolean forward) {
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
		
	}
	
	private void startBackward() {
		LCD.clear();
		int cursorIndex=2;
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
			
		}
		
		// Temporarily perform the backward search for C
		LCD.clear();
		Algorithm algo = new Algorithm();
		algo.backward("C");
		
		Button.waitForAnyPress();
		
		
	}
}
