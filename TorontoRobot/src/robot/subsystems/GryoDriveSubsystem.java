package robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.GyroBase;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.interfaces.Gyro;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public abstract class GryoDriveSubsystem extends DriveSubsystem {

	private GyroBase gyro;
	
	private GyroPID gyroPid = new GyroPID(1.0);
	
	/**
	 * Drive subsystem with left/right drive and gyro.
	 * <p>
	 * The GyroDrive subsystem extends the DriveSubsystem and adds has a 
	 * gyro and PID controller to control the angle heading of the robot.
	 * The GyroPID is initialized to disabled.  Use the {@link #enableAnglePid()} 
	 * and {@link #disableAnglePid()} to enable and disable the angle PID.
	 * <p>
	 * The drive subsystem has pids that are initialized to 
	 * disabled.  Use the {@link #enableSpeedPids()} and {@link #disableSpeedPids()}
	 * routines to set the PIDs on and off
	 * 
	 * @param leftMotor that implements the SpeedController interface
	 * @param leftMotorInverted {@literal true} if the motor is 
	 * inverted, {@literal false} otherwise
	 * @param rightMotor that implements the SpeedController interface
	 * @param rightMotorInverted {@literal true} if the motor is 
	 * inverted, {@literal false} otherwise
	 * @param leftEncoder encoder for the left motor
	 * @param leftEncoderInverted {@literal true} if the encoder is 
	 * inverted, {@literal false} otherwise
	 * @param rightEncoder encoder for the right motor
	 * @param rightEncoderInverted {@literal true} if the encoder is 
	 * inverted, {@literal false} otherwise
	 * @param kP Default Proportional gain for the motor speed pid.  The 
	 * speed PIDs are displayed on the SmartDashboard and can be 
	 * adjusted through that interface
	 * @param maxEncoderSpeed the max loaded robot encoder rate used
	 * to normalize the PID input encoder feedback.
	 */
	public GryoDriveSubsystem(
			GyroBase gyro,
			SpeedController leftMotor,  boolean leftMotorInverted, 
			SpeedController rightMotor,	boolean rightMotorInverted, 
			Encoder leftEncoder,        boolean leftEncoderInverted, 
			Encoder rightEncoder,	    boolean rightEncoderInverted, 
			double kP, 
			double maxEncoderSpeed
			) {
		
		super(
			leftMotor, leftMotorInverted, 
			rightMotor, rightMotorInverted, 
			leftEncoder, leftEncoderInverted, 
			rightEncoder, rightEncoderInverted, 
			kP, maxEncoderSpeed);
		
		this.gyro = gyro;
	}
	
	
	/**
	 * Disable the angle PID for the Drive subsystem.
	 * <p>
	 * NOTE: If the angle PIDs is not currently enabled,
	 * this routine has no effect
	 */
	public void disableAnglePid() {
		gyroPid.disable();
	}
	
	/**
	 * Enable the angle PIDs for the Drive subsystem.
	 * <p>
	 * NOTE: If the angle PID is already enabled,
	 * this routine has no effect.
	 */
	public void enableAnglePid() {
		gyroPid.enable();
	}
	
	/**
	 * Set the current gyro heading to zero.
	 */
	public void resetGyroAngle() {
		gyro.reset();
	}
	
	/**
	 * Reset the gyro angle to a known heading angle.
	 * <p>
	 * This routine is useful when start autonomous
	 * to set the gyro angle to a known configuration
	 * at the start of the match 
	 * (ie pointed right = 90 degrees)
	 * @param angle new angle reading for the gyro
	 */
	public void resetGyroAngle(double angle) {
		// FIXME:
	}
	
	/**
	 * Get the current gyro angle
	 * <p>
	 * NOTE: This routine will always return a positive
	 * angle >= 0 and < 360 degrees.
	 * @return gyro angle in degrees.
	 */
	public double getGryoAngle() {
		
		// Normalize the angle
		double angle = gyro.getAngle() % 360;
		
		if (angle < 0) {
			angle = angle + 360;
		}
		
		return angle;
	}
	
	/** 
	 * Get the GyroPid Steering
	 */
	public double getGyroPidSteering() {
		return gyroPid.get();
	}
	
	/**
	 * Set the Heading for the Gyro PID
	 */
	public void setDirection(double direction) {
		gyroPid.setSetpoint(direction);
	}
	
	@Override
	public void updatePeriodic() {
		
		super.updatePeriodic();
		
		// Update all PIDs
		if (gyroPid.isEnabled()) {
			gyroPid.calculate(gyro.getAngle());
		}
		
		// Update all SmartDashboard values
		SmartDashboard.putData("Gyro", gyro);
		SmartDashboard.putNumber("Gyro Angle", getGryoAngle());
		
		SmartDashboard.putData("Gyro PID", gyroPid);
	}
	
	
	
}
