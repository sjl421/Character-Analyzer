/************************************************************
 * Name:  Sujil Maharjan                                    *
 * Project:  Project 4/Lejos Robot			               *
 * Class:  Artificial Intelligence/CMP 331                  *
 * Date:  4/8/2016			                               *
 ************************************************************/
package com.lejos.controller;

import com.lejos.model.Pixel;

import lejos.hardware.Audio;
import lejos.hardware.Sound;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.utility.Delay;

/**
 * Robot handles all of the functionalities that the robot can have. It includes making robot travel, sense the color, and run motor
 * @author Sujil Maharjan
 *
 */
public class Robot {
	// Values for turning the robot to the right and left
	private final int RIGHT = 90;
	private final int LEFT = -90;
	
	
	// Records the total distance
	private double travelDistance = 0;
	
	// Records the speed of the robot
	private int speed = 10;
	
	// Handles the sensor ports and EV3Color sensors
	private static Port colorSensorPort = SensorPort.S4;
    private static EV3ColorSensor colorSensor = null;
    private static SampleProvider sampleProvider;
    private static int sampleSize;
    
    // Holds the value for ON and OFF
    private float on = 0;
    private float off = 1;
    
    // It is the pilot for moving the robot
    DifferentialPilot pilot;
	
    /**
     * It returns the value of the light sensed
     * @return Returns the value of the surface
     */
    private static float getSample() {
        // Initializes the array for holding samples
        float[] sample = new float[sampleSize];

        // Gets the sample an returns it
        sampleProvider.fetchSample(sample, 0);
        return sample[0];
    }
	
	public Robot() {
		pilot = new DifferentialPilot(5.42f, 9.6f, Motor.A, Motor.C, false);
		travelDistance = (float) 3.0;
		pilot.setTravelSpeed(speed);		
	}
	
	/**
	 * Sounds the robot when it is ON
	 */
	public void soundON() {
		Sound.systemSound(true, 0);
	}
	
	/**
	 * Sounds the robot when it is OFF
	 */
	public void soundOFF() {
		Sound.systemSound(true, 1);
	}
	
	/**
	 * Sounds the robot when it is a success
	 */
	public void soundSuccess() {
		Sound.systemSound(true, 3);
	}
	
	/**
	 * Sounds the robot when it is failure
	 */
	public void soundFailure() {
		Sound.systemSound(true, 2);
	}
	
	/**
	 * It makes the robot travel one unit distance
	 * @param isTurn It holds if the robot should make a turn or go straight
	 */
	public void travelOne(boolean isTurn) {
		// Checks if we need to turn the robot.
		// If no, then just move the robot forward. Else, take it all the way back to the beginning and turn it.
		if (!isTurn ) {
			pilot.travel(travelDistance);
		}
		else {
			pilot.travel(-6* travelDistance);
			pilot.rotate(RIGHT);
			pilot.travel(travelDistance);
			pilot.rotate(LEFT);
			pilot.travel(travelDistance);
		}
		
	}
	
	/**
	 * It records the ON value from the color sensor
	 */
	public void recordON() {
		// Sense the color
		sampleProvider = colorSensor.getRedMode();
        sampleSize = sampleProvider.sampleSize();
		
		// record it in on
        on = getSample();
        String val = on+"value";
        LCD.drawString(val,0,2);
        //Delay.msDelay(3000);
	}
	
	/**
	 * It records the off value from the color sensor
	 */
	public void recordOFF() {
		// Sense the color
		sampleProvider = colorSensor.getRedMode();
        sampleSize = sampleProvider.sampleSize();
		
		// record it in on
        off = getSample();
        String val = off+"value";
        
        LCD.drawString(val,0,2);
        //Delay.msDelay(3000);
	}
	
	/**
	 * It initiates and lights up the sensor
	 */
	public void startSensor() {
		if (colorSensor ==null) {
			colorSensor = new EV3ColorSensor(colorSensorPort);
		}
	}
	
	/**
	 * It returns the ON value
	 * @return
	 */
	public float getON() {
		return on;
	}
	
	/**
	 * It returns the OFF value	
	 * @return
	 */
	public float getOFF() {
		return off;
	}
	
	/**
	 * It scans the surface and checks if it is on 
	 * @return Returns true if the surface is ON. Else, false
	 */
	public boolean isON() {
		// Get the surface value
		sampleProvider = colorSensor.getRedMode();
        sampleSize = sampleProvider.sampleSize();
        
        float value = getSample();
        
             
        // Checks if the value is over 0.12. If yes, return true
		if ( value > 0.12) {
			return true;
		}
        
		return false;
	}
	
	/**
	 * Reads the color of the surface
	 * @return Retursn the float value of the surface
	 */
	private float readColor() {
		sampleProvider = colorSensor.getRedMode();
        sampleSize = sampleProvider.sampleSize();
        
        return getSample();
	}
	
	/**
	 * Reads the distance of the pixel size.
	 */
	public void readDistance(){
		LCD.clear();
		
		LCD.drawString("Scanning the pixelSize", 0, 1);

		// Pilot moves forward
		pilot.forward();
		
		// Initialize the start and end time for the calculation
		double time1 =0.00;
		double time2 = 0.00;
		
		boolean onColorRead = false;
		
		// Loops until the robot goes through all of the pixel width.
		while(true){
			float colorRead = readColor();
			System.out.println();
			System.out.println();
			LCD.drawString(colorRead+"", 0, 0);
			if(!onColorRead && (colorRead >=0.1)){
				onColorRead = true;
				time1 = (double) System.currentTimeMillis()/1000.00;
			}			
			else if(onColorRead && (colorRead<0.1 )){
				time2 = (double) System.currentTimeMillis()/1000.00;
				break;
			}
		}
		pilot.stop();
		LCD.clear();
		
		// Velocity equation to calculate the size of the pixel
		double pixelSize = speed*(time2-time1);	
		
		travelDistance = pixelSize;
		
		LCD.drawString("Pixel Size read as:", 0, 3);
		LCD.drawString(""+pixelSize, 0, 4);
		Delay.msDelay(1000);
		
	}
}
