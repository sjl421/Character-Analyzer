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

public class FileAccess {

	private ArrayList<RuleNode> rules = new ArrayList<RuleNode>();
	
	public FileAccess(String filename) {
		saveRules(filename);
		
	}
	
	private void saveRules(String filename) {
		
		try {
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
	
		
	public ArrayList<RuleNode> getRules() {
		return rules;
	}
}
