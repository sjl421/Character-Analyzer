package com.lejos.model;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.*;
import java.util.ArrayList;

import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;

public class Algorithm {
	public static ArrayList<RuleNode> rules = new ArrayList<RuleNode>();
	private ArrayList<Coordinates> boxes = new ArrayList<Coordinates>();
	
	public Algorithm() {
		boxes.add(new Coordinates(0,0,true));
		boxes.add(new Coordinates(0,1,true));
		boxes.add(new Coordinates(1,0,true));
		boxes.add(new Coordinates(2,0,true));
		boxes.add(new Coordinates(2,1,true));
		boxes.add(new Coordinates(0,2,true));
	}
	
	public void addRule(String str) {
		rules.add(new RuleNode(str));
		System.out.println("Rule is added");
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
	
	public void backward(String str) {
		RuleNode mainRule = find(str);
		
		// Checks if the main rule is null. If it is not null, then check if the LHS of the 
		// rule follows all the rules
		if (mainRule != null) {
			ArrayList<Coordinates> lhsRules = new ArrayList<Coordinates>();
			
			lhsRules = mainRule.getLHSCoords();
								
			// Here we go through each and every pixels of the rule. This should be changed for different platforms
			for (int i = 0; i < boxes.size(); ++i) {	
				Coordinates testCoord = boxes.get(i);
				
				for (int index = 0; index < lhsRules.size(); index++) {
					Coordinates ruleCoord = lhsRules.get(index);
					
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
	public static ArrayList<Coordinates> getRules(String value) {
		if (rules.isEmpty()) {
			return null;
		}
		
		ArrayList<Coordinates> lhsCoords = new ArrayList<Coordinates>();
		
		for (int i =0; i < rules.size(); ++i){
			if (value.equals(rules.get(i).getRHS())) {
				return rules.get(i).getLHSCoords();
			}
		}
		
		return null;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String text = "text.txt";
		File data = new File(text);
			    
	    try {
	      InputStream is = new FileInputStream(data);
	      DataInputStream din = new DataInputStream(is);
	      BufferedReader br = new BufferedReader(new InputStreamReader(is));
	      while (br.readLine() != null) {
	    	  System.out.println("Hello");
	      }
	      din.close();
	    } catch (FileNotFoundException e) {
			e.printStackTrace();
			Sound.beep();
		} catch (IOException e) {
			e.printStackTrace();
		}
	    //Button.waitForAnyPress();
	    
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
