package com.lejos.view;

import com.lejos.controller.Robot;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

public class StartView {

	private final int CURSOR_X = 10;
	private final String CURSOR = "<";
	
	private boolean isScan = false;
	
	private Robot robot = new Robot();
	
	public StartView() {
		initiateMenu();
		//robot  = new Robot();
	}
	
	public void initiateMenu() {
		LCD.clear();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int x_coord = 0;
		int y_coord = 0;
		
		LCD.drawString("Calibrate", x_coord, y_coord);
		LCD.drawString("Scan", x_coord, y_coord+1);
		
		LCD.drawString(CURSOR, CURSOR_X, 0); 
		
		while (true) {
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
					MainView mainView = new MainView("text.txt", robot);
				}
			}
			else if (button == Button.ID_ESCAPE) {
				break;
			}
		}
	}
	
	public void calibrate() {
		LCD.clear();
		robot.startSensor();
		LCD.drawString("Enter for ON",0,0);
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
		
		robot.readPixelSize();
		
		LCD.drawString("Calibrated!!",0,2);
		
		Delay.msDelay(2000);
		
		//LCD.clear();
		initiateMenu();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StartView start = new StartView();
	}
}
