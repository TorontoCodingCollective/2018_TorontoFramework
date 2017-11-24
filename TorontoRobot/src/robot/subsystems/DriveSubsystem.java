package robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * DriveSubsystem
 * <p>
 * The DriveSubsystem is a left right drive with encoders on each side
 * of the drivetrain.  The DriveSubsystm can be used with drive PIDs
 * on or off. 
 */
public abstract class DriveSubsystem extends Subsystem {
	
	private final SpeedController leftMotor;
	private final SpeedController rightMotor;
	
	private final Encoder leftEncoder;
	private final Encoder rightEncoder;
	
	private final SpeedPID leftSpeedPid;  
	private final SpeedPID rightSpeedPid; 
	
	boolean speedPidsActive = false;

	/**
	 * Drive subsystem with left/right drive.
	 * <p>
	 * The drive subsystem has pids that are initialized to 
	 * disabled.  Use the {@link #enableSpeedPids()} and {@link #disableSpeedPids()}
	 * routines to set the PIDs on and off
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
	 * @param p Default Proportional gain for the motor speed pid.  The 
	 * speed PIDs are displayed on the SmartDashboard and can be 
	 * adjusted through that interface
	 * @param maxEncoderSpeed the max loaded robot encoder rate used
	 * to normalize the PID input encoder feedback.
	 */
	public DriveSubsystem(
			SpeedController leftMotor,
			boolean         leftMotorInverted,
			SpeedController rightMotor,
			boolean         rightMotorInverted,
			Encoder         leftEncoder,
			boolean         leftEncoderInverted,
			Encoder         rightEncoder,
			boolean         rightEncoderInverted,
			double          p,
			double          maxEncoderSpeed) {
		
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
		this.leftEncoder = leftEncoder;
		this.rightEncoder = rightEncoder;
		
		if (leftMotorInverted) {
			rightMotor.setInverted(true);
		}
		if (rightMotorInverted) {
			rightMotor.setInverted(true);
		}
		
		if (leftEncoderInverted) {
			leftEncoder.setReverseDirection(true);
		}
		if (rightEncoderInverted) {
			rightEncoder.setReverseDirection(true);
		}
		
		leftSpeedPid  = new SpeedPID(p, maxEncoderSpeed);
		rightSpeedPid = new SpeedPID(p, maxEncoderSpeed);
		
		disableSpeedPids();
	}
			
	/**
	 * Disable the speed PIDs for the Drive subsystem.
	 * <p>
	 * NOTE: This routine will not change the motor speeds.  
	 * Use {@link #setSpeed(double, double)} to update the 
	 * motor speeds after disabling the PIDs.
	 * <p>
	 * NOTE: If the speed PIDs are not currently enabled,
	 * this routine has no effect
	 */
	public void disableSpeedPids() {
		
		if (speedPidsActive) {
			leftSpeedPid.disable();
			rightSpeedPid.disable();
			speedPidsActive = false;
		}
	}
	
	/**
	 * Enable the speed PIDs for the Drive subsystem.
	 * <p>
	 * NOTE: This routine may change the motor speeds. Enabling
	 * a disabled speed PID will clear the setpoint.  
	 * Use {@link #setSpeed(double, double)} to update the 
	 * motor speeds after enabling the PIDs
	 * <p>
	 * NOTE: If the speed PIDs are already enabled,
	 * this routine has no effect.
	 */
	public void enableSpeedPids() {
		if (!speedPidsActive) {
			leftSpeedPid.enable();
			rightSpeedPid.enable();
			speedPidsActive = true;
		}
	}
	
	/**
	 * Reset the encoder counts on the encoders.
	 */
	public void resetEncoders() {
		leftEncoder.reset();
		rightEncoder.reset();
	}
	
	/**
	 * Get the raw distance covered since the last encoder reset
	 * @return
	 */
	public int getRawDistance() {
		return (leftEncoder.get() + rightEncoder.get()) / 2;
	}
	
	public double getRawSpeed() {
		return (leftEncoder.getRate() + rightEncoder.getRate()) / 2.0d;
	}
	
	public void setSpeed(double leftSpeedSetpoint, double rightSpeedSetpoint) {

		if (speedPidsActive) {

			leftSpeedPid.setSetpoint(leftSpeedSetpoint);
			rightSpeedPid.setSetpoint(rightSpeedSetpoint);
		
		}
		else {
			
			SmartDashboard.putNumber("Left Speed", leftSpeedSetpoint);
			SmartDashboard.putNumber("Right Speed", rightSpeedSetpoint);
	
			leftMotor.set(leftSpeedSetpoint);
			rightMotor.set(rightSpeedSetpoint);
		}
	}
	
	public void updatePeriodic() {
		
		// Update all of the PIDS
		if (speedPidsActive) {
			
			leftSpeedPid .calculate(leftEncoder.getRate());
			rightSpeedPid.calculate(rightEncoder.getRate());
			
			leftMotor .set(leftSpeedPid.get());
			rightMotor.set(rightSpeedPid.get());

			SmartDashboard.putNumber("Left Speed", leftSpeedPid.get());
			SmartDashboard.putNumber("Right Speed", rightSpeedPid.get());
	
		}
		
		// Update all SmartDashboard values
		SmartDashboard.putBoolean("Speed PIDs Active", speedPidsActive);

		SmartDashboard.putNumber("Left Encoder Raw", leftEncoder.getRaw());
		SmartDashboard.putNumber("Left Encoder Speed", leftEncoder.getRate());
		SmartDashboard.putNumber("Right Encoder Raw", rightEncoder.getRaw());
		SmartDashboard.putNumber("Right Encoder Speed", rightEncoder.getRate());
		SmartDashboard.putData("LeftPid", leftSpeedPid);
		SmartDashboard.putData("RightPid", rightSpeedPid);
	}
	
}
