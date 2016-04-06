package com.lejos.controller;

import com.lejos.model.Pixel;

public class Robot {
	private int travelDistance = 0;
	private int row = 0;
	private int col = 0;
	
	private Pixel currentPixel;
	
	public Robot() {
		calibrate();
	}
	
	// This will first calibrate the robot and find out the distance and everything
	public void calibrate() {
		
	}
	
	public void travelOne() {
		// Robot checks with count and see if it should go right, left or forward
		if (row ==7 && col == 5) {
			return;
		}
		else if (row %2 == 0) {
			if (col < 5) {
				col ++;
				// Robot goes forward
			}
			else {
				row ++;
				// Robot goes right
			}
		}
		else if (row % 2 == 1) {
			if (col >0) {
				col--;
				// Robot goes forward
			}
			else {
				row++;
				// Robot goes left
			}
		}
	}
}
