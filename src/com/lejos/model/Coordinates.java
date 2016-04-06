package com.lejos.model;

public class Coordinates {
	private int row;
	private int column;
	private boolean isOn = false;
	
	public Coordinates() {
		row = -1;
		column = -1;
	}
	
	public Coordinates(int rNumber, int col,boolean isOn) {
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
