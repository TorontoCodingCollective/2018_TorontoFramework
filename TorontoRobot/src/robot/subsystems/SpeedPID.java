package robot.subsystems;

import edu.wpi.first.wpilibj.PIDController;
import edu.wpi.first.wpilibj.PIDOutput;
import edu.wpi.first.wpilibj.PIDSource;
import edu.wpi.first.wpilibj.PIDSourceType;

public class SpeedPID extends PIDController {

	private double output;
	
	public SpeedPID(double Kp) {
		super(Kp, 0.0d, 0.0d, 1.0d, 
				new PIDSource() {
					public void setPIDSourceType(PIDSourceType pidSource) {}
					public PIDSourceType getPIDSourceType() {return null;}
					public double pidGet() { return 0;	}
				}, 
				new PIDOutput() {
					public void pidWrite(double output) {}
		});
	}

	public double calculate(double setpoint, double normalizedSpeed) {
		
		// Calculate the error
		double error = setpoint - normalizedSpeed;
		
		// Get proportional output
		double p = getP() * error;
		
		// Calculate the total output
		output = setpoint + p;
		
		return output;
	}
	
	public double get() { 
		return output;
	}
}
