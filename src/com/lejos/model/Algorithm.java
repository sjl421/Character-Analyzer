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

public class Algorithm {
	public static ArrayList<RuleNode> rules = new ArrayList<RuleNode>();
	private ArrayList<Pixel> boxes = new ArrayList<Pixel>();
	
	private ArrayList<RuleNode> possibleRules = new ArrayList<RuleNode>();
	private ArrayList<RuleNode> rejectedRules = new ArrayList<RuleNode>();
	
	private Robot robot;
	
	FileAccess fileAccess;
	
	public Algorithm(String filename) {
		boxes.add(new Pixel(0,0,true));
		boxes.add(new Pixel(0,1,true));
		boxes.add(new Pixel(1,0,false));
		boxes.add(new Pixel(2,0,true));
		boxes.add(new Pixel(2,1,true));
		boxes.add(new Pixel(0,2,false));
		//boxes.add(new Pixel(0,2,true));
		
		fileAccess = new FileAccess(filename);
		
		rules = fileAccess.getRules();
		
		robot = new Robot();
	}
	
	public static void addRule(String str) {
		rules.add(new RuleNode(str));
		System.out.println(str);
	}
	
	public RuleNode find(String str) {
		for (int i =0; i < rules.size(); ++i) {
			RuleNode currentRule = rules.get(i);
			if (currentRule.getRHS().equals(str)) {
				return currentRule;
			}
		}
		
		return null;
	}
	
	public void forward() {
		initiateForwardCondition();
		
		//robot.travelOne();
		// Go through each and every pixels and update the possible rules each time
//		for (int row =0; row < 8; row++) {
//			for (int col = 0; col < 6; col ++) {
//				Pixel temp = new Pixel(row,col, true);
//				
//				updatePossibilities(temp);
//			}
//		}
		
		int i = 0;
		while (i < boxes.size()) {
			updatePossibilities(boxes.get(i));
			i++;
		}
		
		// At this point, possibleRules will have the possible values
		if (!possibleRules.isEmpty()) {
			for (int j=0; j < possibleRules.size(); ++ j) {
				System.out.println("Possible value is " + possibleRules.get(j).getRHS());
			}
		}
		else {
			System.out.println("No match");
		}
		
	}
	
	private void updatePossibilities(Pixel input) {
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
	}
	
	private void initiateForwardCondition() {
		possibleRules = rules;
	}
	
	public void backward(String str) {
		RuleNode mainRule = find(str);
		
		// Checks if the main rule is null. If it is not null, then check if the LHS of the 
		// rule follows all the rules
		if (mainRule != null) {
			ArrayList<Pixel> lhsRules = new ArrayList<Pixel>();
			
			lhsRules = mainRule.getLHSPixels();
								
			// Here we go through each and every pixels of the rule. This should be changed for different platforms
			// ROBOT FUNCTIONS GO HERE
			for (int i = 0; i < boxes.size(); ++i) {	
				Pixel testCoord = boxes.get(i);
				
				for (int index = 0; index < lhsRules.size(); index++) {
					Pixel ruleCoord = lhsRules.get(index);
					
					// At this point, check if the robot generated coordinates is equal to the ones in the list and if it is on. If not, then error
					if (testCoord.getRow() == ruleCoord.getRow() && testCoord.getColumn() == ruleCoord.getColumn()) {
						if (!testCoord.isOn()) {
							LCD.drawString("Sorry not found",0,0);
							return;
						}
					}
				}
			}
			
			// At this point, character is recognized
			LCD.drawString("Character has been recognized",0,0);
		}
		else {
			LCD.drawString("It is null",0,0);
		}
		
		
	}
	
	/**
	 * Gets the rule coordinates of left hand side given the string on the right hand side
	 * @param value
	 * @return
	 */
	public static ArrayList<Pixel> getRules(String value) {
		if (rules.isEmpty()) {
			return null;
		}
		
		ArrayList<Pixel> lhsCoords = new ArrayList<Pixel>();
		
		for (int i =0; i < rules.size(); ++i){
			if (value.equals(rules.get(i).getRHS())) {
				return rules.get(i).getLHSPixels();
			}
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
			    
		Algorithm algo = new Algorithm("text.txt");
		
	    //Button.waitForAnyPress();
		
		algo.forward();
	    
	}
		
		/*
		Charset charset = Charset.forName("US-ASCII");
		
		Path file = Paths.get("text.txt");
		
		
		
		try (BufferedReader reader = Files.newBufferedReader(file, charset)) {
		    String line;
		    while ((line = reader.readLine()) != null) {
		        //System.out.println(line);
		        working.addRule(line);
		    }
		} catch (IOException x) {
		    LCD.drawString("IOException: %s%n", 0,0);
		}*/
	
//		Algorithm working = new Algorithm();
//		// Do backward search
//		String character = "D";
//		working.backward(character);
////		
//		
//		Delay.msDelay(3000);

	//}

}
