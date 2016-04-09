/************************************************************
 * Name:  Sujil Maharjan                                    *
 * Project:  Project 4/Lejos Robot			               *
 * Class:  Artificial Intelligence/CMP 331                  *
 * Date:  4/8/2016			                               *
 ************************************************************/
package com.lejos.model;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.ArrayList;

import com.lejos.controller.Robot;
import com.lejos.view.MainView;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.utility.Delay;

/**
 * It runs the forward and backward search algorithm
 * @author Sujil Maharjan
 *
 */
public class Algorithm {
	// It holds the rules as the RuleNode object
	public static ArrayList<RuleNode> rules = new ArrayList<RuleNode>();
	
	// Holds the boxes that are ON
	private ArrayList<Pixel> boxes;
	
	// Holds all of the possible rules given certain pixel as ON or OFF
	private ArrayList<RuleNode> possibleRules = new ArrayList<RuleNode>();
	private ArrayList<RuleNode> rejectedRules = new ArrayList<RuleNode>();
	
	// Holds the previous state of the node if in case we get a null value while scanning
	private ArrayList<RuleNode> previousState = new ArrayList<RuleNode>();
	
	// Holds all of the right hand side of the rule
	private static ArrayList<String> rhsRules = new ArrayList<String>();
	
	// Holds the file access object
	FileAccess fileAccess;
	
	public Algorithm(String filename, Robot robot) {
		// Initiates the file access
		fileAccess = new FileAccess(filename);
			
	}
	
	/**
	 * Adds the rule to the RuleNode and parses it accordingly
	 * @param str It holds the string equivalent of the whole rule
	 */
	public static void addRule(String str) {
		RuleNode temp = new RuleNode(str);
		rules.add(temp);
		
		// Adds the RuleNode to the rule
		rhsRules.add(temp.getRHS());
	}
	
	/**
	 * Finds the RuleNode for the string on the RHS
	 * @param str It is the string to be searched
	 * @return Returns the RuleNode object found in the rules
	 */
	public RuleNode find(String str) {
		for (int i =0; i < rules.size(); ++i) {
			RuleNode currentRule = rules.get(i);
			if (currentRule.getRHS().equals(str)) {
				return currentRule;
			}
		}
		
		return null;
	}
	
	/**
	 * It performs the forward search
	 * @param r It represents the Robot object
	 */
	public void forward(Robot r) {
		LCD.clear();
		
		// Initiates the forward condition by enabling possibilities as all of the rules
		initiateForwardCondition();
		
		// Stores the robot object
		Robot robot = r;
		
		// Initializes the arraylist to store ON pixels
		boxes = new ArrayList<Pixel>();
		
		// Declares the boolean for if the turn should be made or not
		boolean isTurn = false;
		
		// Declares the value for the success 
		boolean success = true;
		
		// Loops through all of the board and checks for the input (ON or OFF)
		// Then, it calculates the possibilities
		for (int row = 0; row <8; ++row ) {
			for (int col = 0; col < 6; ++col) {
				// The robot travels a unit distance
				robot.travelOne(isTurn);
				
				// Sets the turn to be false
				isTurn=false;
				
				// Checks if the pixel is ON. If yes, adds it to the boxes and updates the possible candidates.
				if (robot.isON()) {
					robot.soundON();
					boxes.add(new Pixel(row,col,true));
					updatePossibilities(new Pixel(row, col, true));
				}
				else {
					robot.soundOFF();
					updatePossibilities(new Pixel (row,col, false));
				}
				
				// Draws the pixel
				LCD.drawString("Pixel: "+ row+" "+col, 0, 0);
				
				// Draws all of the remaining possibilities
				for (int index = 0; index < possibleRules.size(); ++ index) {
					LCD.drawString(possibleRules.get(index).getRHS()+"", index * 2, 1);
				}
								
				// Checks if the possible rules is empty. If yes, then, go to the previous state and pull that information for closest possibilities
				if (!possibleRules.isEmpty()) {
					previousState = possibleRules;
				}
				else {
					success = false;
					possibleRules = previousState;
				}
				
				Delay.msDelay(1000);
				LCD.clear();
			}
			// Sets is turn as true since we need to change the row
			isTurn = true;
			
		}	
		
		// Declares the substring arraylist
		ArrayList<String> substrings = new ArrayList<String>();
		
		LCD.clear();
		
		// If it is the success, then it displays the match
		if (success) {
			robot.soundSuccess();
			LCD.drawString("Match:", 0, 0);
		}
		else {
			robot.soundFailure();
			LCD.drawString("Possibilities:", 0, 0);
		}
		
		// Prints the result at the last
		for (int index = 0; index < possibleRules.size(); ++ index) {
			LCD.drawString(possibleRules.get(index).getRHS()+"", index * 2, 1);
		}
		
		Delay.msDelay(3000);
		// Computes the substring
		substrings = getSubStrings();
		
		// Prints all of the substrings
		LCD.drawString("Substrings incld:", 0, 2);
		for (int subIndex=0; subIndex < substrings.size(); subIndex++) {
			LCD.drawString(substrings.get(subIndex)+"", subIndex * 2, 3);
		}
		
		Delay.msDelay(4000);
	}
	

	/**
	 * Calculates the total substrings in the board
	 * @return Returns the arraylist of sub strings
	 */
	public ArrayList<String> getSubStrings() {
		
		ArrayList<String> substrings = new ArrayList<String>();
		
		// Gathers the LHS of the rule
		RuleNode lhsRules;
		
		// Loops through each and every rule and then, checks if our ON arraylist contains all of the LHS pixels of the rules
		for (int mainIndex = 0; mainIndex < rules.size(); ++mainIndex) {
			lhsRules = rules.get(mainIndex);
			
			int totalPixels =0;
			
			// Here we go through each and every pixels of the board where it is ON and compare it with the rule.
			// If it contains all of the pixels in the rule, then it is a substring
			for (int i = 0; i < boxes.size(); ++i) {	
				Pixel testCoord = boxes.get(i);
				
				// Check if the pixel that is ON is in LHS of the rule. If yes, increase the size of total pixels
				if (RuleNode.checkIfExists(testCoord, lhsRules)) {
					totalPixels++;
				}
				
				// If the total pixel size is equal to LHS pixel size, then that rule is a substring
				if (lhsRules.getLHSPixels().size() == totalPixels) {
					substrings.add(lhsRules.getRHS());
					break;
				}
			}
	
		}
		
		return substrings;
	}
	
	/**
	 * Updates the possible rules left
	 * @param input It holds the input pixel and if it is ON or OFF
	 */
	private void updatePossibilities(Pixel input) {
		// Checks if the input pixel is ON. If yes, then check if the rules also have that pixel on. If they contradict, remove the rule from possibilities
		if (!input.isOn()) {
			// Now, check which rules have the pixel and eliminate it
			// Because the input from Robot don't have that pixel light up
			for (int result = 0; result < possibleRules.size(); ++result) {
				if (RuleNode.checkIfExists(input, possibleRules.get(result))) {
					rejectedRules.add(possibleRules.get(result));
					possibleRules.remove(result);
					result--;
				}
			}
		}
		else {
			// If the input is ON but the possible rule doesn't have that pixel, remove it
			for (int result = 0; result < possibleRules.size(); ++result) {
				if (!RuleNode.checkIfExists(input, possibleRules.get(result))) {
					rejectedRules.add(possibleRules.get(result));
					possibleRules.remove(result);
					result--;
				}
			}
		}
	}
	
	/**
	 * Initiates the conditions for forward search
	 */
	private void initiateForwardCondition() {
		possibleRules.addAll(rules);
		previousState.addAll(rules);
	}
	
	/**
	 * It handles the backward search
	 * @param str It holds the string value of the one to be searched
	 * @param robot It is the Robot object
	 */
	public void backward(String str, Robot robot) {
		LCD.drawString(str+" is selected", 0, 0);
		Delay.msDelay(2000);
		
		// Finds the string in the arraylist of rules
		RuleNode mainRule = find(str);
		
		// Checks if the main rule is null. If it is not null, then check if the LHS of the 
		// rule follows all the rules
		if (mainRule != null) {
			ArrayList<Pixel> lhsRules = new ArrayList<Pixel>();
			
			lhsRules = mainRule.getLHSPixels();
								
			// Here we go through each and every pixels of the rule. This should be changed for different platforms
			boolean isTurn = false;
			for (int row = 0; row <8; ++row ) {
				for (int col = 0; col < 6; ++col) {
					robot.travelOne(isTurn);
					
					isTurn = false;
							
					
					//Delay.msDelay(2000);
					for (int index = 0; index < lhsRules.size(); index++) {
						Pixel ruleCoord = lhsRules.get(index);
						
						Pixel testCoord = new Pixel(row,col,true);

						// At this point, check if the robot generated coordinates is equal to the ones in the list and if it is on. If not, then error
						if (testCoord.getRow() == ruleCoord.getRow() && testCoord.getColumn() == ruleCoord.getColumn()) {
							if (robot.isON()) {
								robot.soundON();
								testCoord = new Pixel(row, col, true);
							}
							else {
								robot.soundOFF();
								//LCD.drawString("is off and " + robot.getOFF(), 0, 0);
								testCoord = new Pixel(row, col, false);
							}
							
							if (!testCoord.isOn()) {
								robot.soundFailure();
								LCD.drawString("Sorry not found",0,0);
								return;
							}
						}
					}
					
					LCD.drawString(row+" "+col, 0, 1);

					Delay.msDelay(1000);
					LCD.clear();
				}
				isTurn = true;
				
			}
			
			// At this point, character is recognized
			LCD.drawString("Character has been recognized",0,0);
			robot.soundSuccess();
		}
		else {
			LCD.drawString("It is null",0,0);
		}
		
		
	}
	
	/**
	 * Gets the rule coordinates of left hand side given the string on the right hand side
	 * @param value It holds the string value of RHS
	 * @return Returns the arraylist of pixels in LHS
	 */
	public static ArrayList<Pixel> getRules(String value) {
		if (rules.isEmpty()) {
			return null;
		}
		
		for (int i =0; i < rules.size(); ++i){
			if (value.equals(rules.get(i).getRHS())) {
				return rules.get(i).getLHSPixels();
			}
		}
		
		return null;
	}
	
	/**
	 * It returns the rules
	 * @return Returns the rules
	 */
	public ArrayList<String> getRuleValues() {
		return rhsRules;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			    
		Algorithm algo = new Algorithm("text.txt", new Robot());

	}

}
