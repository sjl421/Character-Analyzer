package com.lejos.model;

import java.util.ArrayList;
import java.util.Vector;

public class RuleNode {
	private ArrayList<Coordinates> lhs = new ArrayList<Coordinates>();
	private String lhsCharacter;
	private String rhs = "";
	private boolean hasLHSChar = false;
	
	public RuleNode(String singleRule) {
		parseString(singleRule);
	}
	
	public void parseString(String singleRule) {
		boolean recordMode = false;
		String[] str = singleRule.split("->");
		
		// Gets the right hand side of the rule
		for (int index = 0 ; index < str[1].length(); ++index) {
			if (str[1].charAt(index) == ' ') {
				continue;
			}
			else {
				rhs += str[1].charAt(index);
			}
		}
		
		
		int lhsIndex =0;
		String lhsStr = str[0];
		
		while (lhsIndex < lhsStr.length()) {
			if ( lhsStr.charAt(lhsIndex) == ' ' || lhsStr.charAt(lhsIndex) == ',') {
				lhsIndex++;
				continue;
			}
			if ( lhsStr.charAt(lhsIndex) == '(') {
				// Record the next indexes
				lhsIndex ++;
				int[] coord = new int[2];
				int tempIndex =0;
				while (lhsStr.charAt(lhsIndex) != ')') {
					// At this point, we have to record numbers
					if (lhsStr.charAt(lhsIndex) != ' ' && lhsStr.charAt(lhsIndex) != ',') {
						coord[tempIndex] = Integer.parseInt(lhsStr.charAt(lhsIndex)+"");
						tempIndex++;
					}
					lhsIndex++;
				}
				
				// compute the coordinates and put it in the node
				lhs.add(new Coordinates(coord[0], coord[1], true));
				
				//System.out.println(coord[0] + " and " + coord[1]);
				continue;
			}
			else {
				// At this point, it consists of characters
				if (lhsStr.charAt(lhsIndex) != ')') {
					lhsCharacter = Character.toString(lhsStr.charAt(lhsIndex));
					
					ArrayList<Coordinates> temp = Algorithm.getRules(lhsCharacter);
					
					lhs.addAll(temp);
					
					hasLHSChar = true;
					//System.out.println(lhsStr.charAt(lhsIndex));
				}
			}
						
			lhsIndex++;
		}
		
		//System.out.println("The total size is " + lhs.size()+ " for " + rhs);
	}
	
	public String getRHS() {
		return rhs;
	}
	
	public ArrayList<Coordinates> getLHSCoords () {
		return lhs;
	}
	
	public String getLHSCharacter() {
		return lhsCharacter;
	}
	
	public boolean lhsHasChar () {
		return hasLHSChar;
	}
}

