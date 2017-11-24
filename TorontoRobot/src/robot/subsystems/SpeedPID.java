package robot.subsystems;

import edu.wpi.first.wpilibj.PIDController;

/**
 * Class implements a Proportional (PID) Control Loop for motor speed control.
 *
 * 
 * <p>Creates a separate thread which reads the given PIDSource and takes care of the integral
 * calculations, as well as writing the given PIDOutput.
 *
 * <p>This feedback controller runs in discrete time, so time deltas are not used in the integral
 * and derivative calculations. Therefore, the sample rate affects the controller's behavior for a
 * given set of PID constants.
 */
public class SpeedPID extends PIDController {

	private double output;
	private double maxEncoderRate;
	private double totalError;
	
	public SpeedPID(double Kp, double maxEncoderRate) {
		super(Kp, 0.0d, 0.0d, 1.0d, 
				new NullPIDSource(),
				new NullPIDOutput()	);
		this.maxEncoderRate = maxEncoderRate;
		this.totalError     = 0;
	}

	public SpeedPID(double Kp, double Ki, double maxEncoderRate) {
		super(Kp, Ki, 0.0d, 1.0d, 
				new NullPIDSource(),
				new NullPIDOutput()	);
		this.maxEncoderRate = maxEncoderRate;
		this.totalError     = 0;
	}

	/**
	 * Calculate the PID output.
	 * <p>
	 * In order to generate proper PID behaviour, this routine
	 * must be called at a consistent periodic rate.  Calling
	 * this routine anywhere in the main robot periodic loops is 
	 * sufficient.
	 * <p>
	 * NOTE: If the PID is disabled, this routine returns 0.
	 * @param rawRate the raw encoder rate used as feedback for this PID
	 * @return the calculated result. This result can also be 
	 * retrieved with subsequent calls to {@link #get()}.
	 */
	public double calculate(double rawRate) {
		
		// If the PID is not enabled, this routine does nothing.
		if (!this.isEnabled()) {
			return 0;
		}
		
		// Calculate the normalized rate (speed) of the encoder
		double normalizedRate = rawRate / maxEncoderRate;
		
		if (normalizedRate > 1.0) { 
			normalizedRate = 1.0; 
		}
		
		if (normalizedRate < -1.0) { 
			normalizedRate = -1.0; 
		}
		
		// Calculate the error
		double error = super.getSetpoint() - normalizedRate;
		
		// Get proportional output
		double proportionalOutput = getP() * error;

		// Calculate the total output for the proportional 
		// and feed forward terms.
		
		// Use a temporary totalOutput variable for 
		// all calculations.  Limit the totalOutput
		// and assign the value to the output variable at the 
		// end in order to prevent reads of intermediary
		// output results.
		double totalOutput = super.getSetpoint() + proportionalOutput;
		
		// The output cannot drive more than 1.0
		if (totalOutput > 1.0) {
			totalOutput = 1.0;
		}
		
		// The output cannot drive less than -1.0
		if (totalOutput < -1.0) {
			totalOutput = -1.0;
		}
		
		// Calculate the integral contribution. 
		// Integral controllers are very prone to saturation
		// and "wind-up".  In order to avoid "wind-up", 
		// do not allow the integral error total to exceed
		// the total required to saturate the output (-1.0 or 1.0).
		double Ki = super.getI();
		
		if (Ki != 0) {
			
			totalError += error;
			
			double integralOutput = totalError * Ki;
			
			if (integralOutput + totalOutput > 1.0) {
				// Clamp the total error
				totalError = (1.0 - totalOutput) / Ki;
				// set the total output to the limit
				totalOutput = 1.0;
			} 
			else if (integralOutput + totalOutput < -1.0) {
				// Clamp the total error
				totalError = (-1.0 - totalOutput) / Ki;
				// set the total output to the limit
				totalOutput = -1.0;
			}
			else {
				totalOutput += integralOutput;
			}
		}
		
		output = totalOutput;
		
		return output;
	}
	
	@Override
	public void disable() {
		super.disable();
		totalError = 0;
		output = 0;
	}
	
	@Override
	public double get() { 
		return output;
	}
}
