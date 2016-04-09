/************************************************************
 * Name:  Sujil Maharjan                                    *
 * Project:  Project 4/Lejos Robot			               *
 * Class:  Artificial Intelligence/CMP 331                  *
 * Date:  4/8/2016			                               *
 ************************************************************/
package com.lejos.model;

/**
 * It holds all of the information needed for the pixel
 * @author Sujil Maharjan
 *
 */
public class Pixel {
	// It holds the row, column, and if the pixel is ON
	private int row;
	private int column;
	private boolean isOn = false;
	
	public Pixel() {
		row = -1;
		column = -1;
	}
	
	/**
	 * Constructor for Pixel that takes in row, column numbers and if the pixel is ON
	 * @param rNumber
	 * @param col
	 * @param isOn
	 */
	public Pixel(int rNumber, int col, boolean isOn) {
		row = rNumber;
		column = col;
		this.isOn = isOn;
	}
	
	/**
	 * Returns the row
	 * @return
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * Returns the column number
	 * @return
	 */
	public int getColumn() {
		return column;
	}
	
	public void print() {
		System.out.println("\\("+row+","+column+"\\)" );
	}
	
	/**
	 * Sets the pixel as ON
	 */
	public void setOn() {
		isOn = true;
	}
	
	/**
	 * Sets the pixel as OFF
	 */
	public void setOff() {
		isOn = false;
	}
	
	/**
	 * Returns if the pixel is ON
	 * @return
	 */
	public boolean isOn() {
		return isOn;
	}
}
