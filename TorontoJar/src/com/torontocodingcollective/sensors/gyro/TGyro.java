package com.torontocodingcollective.sensors.gyro;

import edu.wpi.first.wpilibj.GyroBase;

public abstract class TGyro extends GyroBase {

	private boolean inverted;
	private double offset = 0;
	
	public TGyro(boolean inverted) {
		this.inverted = inverted;
	}
	
	protected boolean isInverted() {
		return inverted;
	}
	
	public void resetGyroAngle() {
		resetGyroAngle(0);
	}

	public void resetGyroAngle(double angle) {
		
		// clear the previous offset
		offset = 0;
		
		// set the offset to the current angle
		// in order to zero the output.
		offset = -getAngle();
		
		// This offset will result in an output
		// of zero.  Add the passed in angle
		// to make the desired angle
		offset += angle;
	}
	
	protected double getRate(double rawRate) {
		if (inverted) {
			return -rawRate;
		}
		
		return rawRate;
	}
	
	protected double getAngle(double rawAngle) {
		
		// Invert before subtracting the offset.
		if (inverted) {
			rawAngle = -rawAngle;
		}

		return getNormalizedAngle(rawAngle + offset);
	}
	
	private double getNormalizedAngle(double rawAngle) {
		
		double angle = rawAngle % 360.0;
		
		if (angle < 0) {
			angle += 360.0;
		}
		
		return angle;
	}
	
}
