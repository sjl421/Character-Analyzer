package com.lejos.model;

public class Pixel {
	private int row;
	private int column;
	private boolean isOn = false;
	
	public Pixel() {
		row = -1;
		column = -1;
	}
	
	public Pixel(int rNumber, int col, boolean isOn) {
		row = rNumber;
		column = col;
		this.isOn = isOn;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getColumn() {
		return column;
	}
	
	public void print() {
		System.out.println("\\("+row+","+column+"\\)" );
	}
	
	public void setOn() {
		isOn = true;
	}
	
	public void setOff() {
		isOn = false;
	}
	
	public boolean isOn() {
		return isOn;
	}
}
