/************************************************************
 * Name:  Sujil Maharjan                                    *
 * Project:  Project 4/Lejos Robot			               *
 * Class:  Artificial Intelligence/CMP 331                  *
 * Date:  4/8/2016			                               *
 ************************************************************/
package com.lejos.model;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import com.lejos.view.MainView;

import lejos.hardware.Sound;

/**
 * It handles the file access in the Lejos
 * @author Sujil Maharjan
 *
 */
public class FileAccess {

	// It consists of the arraylist of rule nodes
	private ArrayList<RuleNode> rules = new ArrayList<RuleNode>();
	
	public FileAccess(String filename) {
		// Parses the file and saves the rules individually in line by line basis
		saveRules(filename);
	}
	
	/**
	 * Saves the rules line by line by parsing through the text file
	 * @param filename
	 */
	private void saveRules(String filename) {
		
		try {
			// Parses the rules
			  InputStream is = FileAccess.class.getResource(filename).openStream();
			  BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			  String line;
		    while ((line = reader.readLine()) != null) {
		        //System.out.println(line);
		        Algorithm.addRule(line);
		    }
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				Sound.beep();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
		
	/**
	 * It returns all of the rules
	 * @return
	 */
	public ArrayList<RuleNode> getRules() {
		return rules;
	}
}
