package com.torontocodingcollective.subsystem;

import com.torontocodingcollective.pid.TSpeedPID;
import com.torontocodingcollective.sensors.encoder.TEncoder;
import com.torontocodingcollective.speedcontroller.TSpeedController;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * DriveSubsystem
 * <p>
 * The DriveSubsystem is a left right drive with encoders on each side
 * of the drive train.  The DriveSubsystm can be used with drive PIDs
 * on or off. 
 */
public abstract class TDriveSubsystem extends TSubsystem {
	
	protected final TSpeedController leftMotor;
	protected final TSpeedController rightMotor;
	
	private TEncoder leftEncoder = null;
	private TEncoder rightEncoder = null;
	
	private final TSpeedPID leftSpeedPid;  
	private final TSpeedPID rightSpeedPid; 
	
	private double maxEncoderSpeed = 1.0;
	
	boolean speedPidsActive = false;

	/**
	 * Drive subsystem with left/right drive.
	 * <p>
	 * The drive subsystem has pids that are initialized to 
	 * disabled.  Use the {@link #enableSpeedPids()} and {@link #disableSpeedPids()}
	 * routines to set the PIDs on and off
	 * 
	 * @param leftMotor that implements the TSpeedController interface
	 * @param rightMotor that implements the TSpeedController interface
	 * @param leftEncoder encoder for the left motor
	 * @param rightEncoder encoder for the right motor
	 * @param kP Default Proportional gain for the motor speed pid.  The 
	 * speed PIDs are displayed on the SmartDashboard and can be 
	 * adjusted through that interface
	 * @param maxEncoderSpeed the max loaded robot encoder rate used
	 * to normalize the PID input encoder feedback.
	 */
	public TDriveSubsystem(
			TSpeedController leftMotor,
			TSpeedController rightMotor,
			TEncoder         leftEncoder,
			TEncoder         rightEncoder,
			double           kP,
			double           maxEncoderSpeed) {
		
		this.leftMotor = leftMotor;
		this.rightMotor = rightMotor;
		this.leftEncoder = leftEncoder;
		this.rightEncoder = rightEncoder;
		
		leftSpeedPid  = new TSpeedPID(kP);
		rightSpeedPid = new TSpeedPID(kP);
		
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
		
		if (leftEncoder == null || rightEncoder == null) { return; }
		
		if (!speedPidsActive) {
			leftSpeedPid.enable();
			rightSpeedPid.enable();
			speedPidsActive = true;
		}
	}
	
	/**
	 * Get the raw distance covered since the last encoder reset
	 * @return
	 */
	public int getRawEncoderDistance() {
		if (leftEncoder == null || rightEncoder == null) { 
			return 0;
		}
		
		return (leftEncoder.get() + rightEncoder.get()) / 2;
	}
	
	public double getRawEncoderSpeed() {

		if (leftEncoder == null || rightEncoder == null) { 
			return 0;
		}
		
		return (leftEncoder.getRate() + rightEncoder.getRate()) / 2.0d;
	}
	
	/**
	 * Reset the encoder counts on the encoders.
	 */
	public void resetEncoders() {

		if (leftEncoder == null || rightEncoder == null) { return; } 

		leftEncoder.reset();
		rightEncoder.reset();
	}
	
	/**
	 * Initialize the encoders for this drive subsystem.  This method is used
	 * when the encoders are attached to a channel that is used by another device
	 * and must be constructed after this subsystem.  For example, if an
	 * encoder is attached to a CAN based TalonSRX device, the encoder
	 * can be retrieved from the TalonSRX using the code: 
	 * <br>
	 * {@code ((TCanSpeedController) xxxxMotor).getEncoder(); }
	 * 
	 * @param leftEncoder
	 * @param leftInverted {@code true} if the encoder is inverted, {@code false} otherwise
	 * @param rightEncoder
	 * @param rightInverted {@code true} if the encoder is inverted, {@code false} otherwise
	 */
	public void setEncoders(TEncoder leftEncoder, boolean leftInverted, TEncoder rightEncoder, boolean rightInverted) {
		
		this.leftEncoder = leftEncoder;
		this.rightEncoder = rightEncoder;
		
		if (leftEncoder != null) {
			this.leftEncoder.setInverted(leftInverted);
		}
		if (rightEncoder != null) {
			this.rightEncoder.setInverted(rightInverted);
		}
	}
	
	public void setMaxEncoderSpeed(double rawEncoderSpeed) {
		this.maxEncoderSpeed = rawEncoderSpeed;
	}
	
	public void setSpeed(double leftSpeedSetpoint, double rightSpeedSetpoint) {

		if (speedPidsActive) {

			leftSpeedPid.setSetpoint(leftSpeedSetpoint);
			rightSpeedPid.setSetpoint(rightSpeedSetpoint);
		
		}
		else {
			
			SmartDashboard.putNumber("Left Motor", leftSpeedSetpoint);
			SmartDashboard.putNumber("Right Motor", rightSpeedSetpoint);
	
			leftMotor.set(leftSpeedSetpoint);
			rightMotor.set(rightSpeedSetpoint);
		}
	}
	
	public void updatePeriodic() {

		if (leftEncoder != null && rightEncoder != null) {

			// Update all of the PIDS
			if (speedPidsActive) {
				
				// Speed PID calculations require a normalized rate
				leftSpeedPid .calculate(leftEncoder.getRate() /maxEncoderSpeed);
				rightSpeedPid.calculate(rightEncoder.getRate()/maxEncoderSpeed);
				
				leftMotor .set(leftSpeedPid.get());
				rightMotor.set(rightSpeedPid.get());
	
				SmartDashboard.putNumber("Left Motor", leftSpeedPid.get());
				SmartDashboard.putNumber("Right Motor", rightSpeedPid.get());
		
			}
		
			// Update all SmartDashboard values
			SmartDashboard.putBoolean("Speed PIDs Active", speedPidsActive);
			SmartDashboard.putNumber("Left Enc", leftEncoder.get());
			SmartDashboard.putNumber("Left Speed", leftEncoder.getRate());
			SmartDashboard.putNumber("Right Enc", rightEncoder.get());
			SmartDashboard.putNumber("Right Speed", rightEncoder.getRate());
		
			SmartDashboard.putData("LeftPid", leftSpeedPid);
			SmartDashboard.putData("RightPid", rightSpeedPid);
		}
	}
	
}
