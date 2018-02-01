package com.torontocodingcollective.sensors.ultrasonic;

import edu.wpi.first.wpilibj.AnalogInput;

public class TUltrasonic extends AnalogInput {

	private double v40 = 5.0; //voltage @ 40inch away
	
	double m1 = 79.92;    //normal slope inch/volt
	double b1 = 11.32;    //default offset inches
	double m2, b2;
	
	
	
	
	public TUltrasonic(int analogPort) {   //ultrasonic is connected to analog port
		super(analogPort);
	}

	public void calibrate(double v20, double v40, double v80) {
		this.v40 = v40;
		
		//calculating m1, b1 for voltage < v40
				m1 = (40.0 - 20.0) / (v40 - v20);
				b1 = 20.0 - (m1*v20);
				
		//calculating m2, b2 for voltage > v40
				m2 = (80 - 40) / (v80 - v40);
				b2 = (80 - 40) / (v80 - v40);	
	}
	
	/***
	 * getting distance in inches from the back
	 * face of the Ultrasonic sensor
	 * @return double distance in inches
	 */
	public double getDistance() {
		double distance = 0;
		double voltage = super.getVoltage();
		
		if (voltage <= v40) {
			
			//calculating distance based on m1 & b1
			distance = m1 * voltage + b1;
			
		} else {
			
			//calculating distance based on m2, b2
			distance = m2 * voltage + b2;
			
		}
		
		//return the distance
		return distance;
	}
}
