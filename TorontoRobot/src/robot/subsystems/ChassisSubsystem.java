package robot.subsystems;

import com.torontocodingcollective.sensors.gyro.TAnalogGyro;
import com.torontocodingcollective.speedcontroller.TCanSpeedController;
import com.torontocodingcollective.speedcontroller.TCanSpeedControllerType;
import com.torontocodingcollective.subsystem.TGryoDriveSubsystem;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotConst;
import robot.RobotMap;
import robot.commands.drive.DefaultChassisCommand;

/**
 *
 */
public class ChassisSubsystem extends TGryoDriveSubsystem {
	
	private DigitalInput frontLimitSwitch = new DigitalInput(4);
	
	private Solenoid shifter = new Solenoid(0);
	
	public boolean LOW_GEAR = false;
	public boolean HIGH_GEAR = true;
	
	private boolean turboEnabled = false;
	
	public ChassisSubsystem() {
		
		// Uncomment this block to use CAN based speed controllers
		super(
			new TAnalogGyro(0, RobotConst.INVERTED),
			new TCanSpeedController(TCanSpeedControllerType.TALON_SRX, RobotMap.LEFT_MOTOR_CAN_ADDRESS,  RobotConst.NOT_INVERTED, RobotMap.LEFT_FOLLOWER_CAN_ADDRESS), 
			new TCanSpeedController(TCanSpeedControllerType.TALON_SRX, RobotMap.RIGHT_MOTOR_CAN_ADDRESS, RobotConst.INVERTED,     RobotMap.RIGHT_FOLLOWER_CAN_ADDRESS), 
			null,
			null,
			1.0,
			0.1,
			RobotConst.MAX_DRIVE_ENCODER_SPEED);
		
		super.setEncoders(
				((TCanSpeedController) super.leftMotor) .getEncoder(), RobotConst.NOT_INVERTED,
				((TCanSpeedController) super.rightMotor).getEncoder(), RobotConst.INVERTED );

		
		// Uncomment this constructor to use PWM based Speed controllers
//		super(
//				new TAnalogGyro(0, RobotConst.INVERTED),
//				new TPwmSpeedController(TPwmSpeedControllerType.VICTOR, RobotMap.LEFT_MOTOR_PWM_PORT,  RobotConst.NOT_INVERTED, RobotMap.LEFT_FOLLOWER_PWM_PORT), 
//				new TPwmSpeedController(TPwmSpeedControllerType.VICTOR, RobotMap.RIGHT_MOTOR_PWM_PORT, RobotConst.INVERTED,     RobotMap.RIGHT_FOLLOWER_PWM_PORT), 
//				new TPwmEncoder(0, 1, RobotConst.NOT_INVERTED),
//				new TPwmEncoder(2, 3, RobotConst.INVERTED),
//				1.0,
//				0.1,
//				RobotConst.MAX_DRIVE_ENCODER_SPEED);
	}

	@Override
	public void init() {
		TAnalogGyro gyro = (TAnalogGyro) super.gyro;
		gyro.setSensitivity(0.0017);
		
		turboEnabled = false;
		shifter.set(LOW_GEAR);
		this.setMaxEncoderSpeed(RobotConst.MAX_LOW_GEAR_SPEED);
	};

	// Initialize the default command for the Chassis subsystem.
	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new DefaultChassisCommand());
	}
	
	//********************************************************************************************************************
	// Turbo routines
	//********************************************************************************************************************
	public void enableTurbo() {
		this.turboEnabled = true;
		shifter.set(true);
	}
	
	public void disableTurbo() {
		this.turboEnabled = false;
		shifter.set(false);
	}
	
	//********************************************************************************************************************
	// Limit Switch routines
	//********************************************************************************************************************
	/**
	 * At front limit
	 * @return {@literal true} if at the limit, {@literal false} otherwise.
	 */
	public boolean atFrontLimit() {
		// The limit switch we are using is wired to return true when 
		// not activated and false otherwise.
		return !frontLimitSwitch.get();
	}

	//********************************************************************************************************************
	// Update the SmartDashboard
	//********************************************************************************************************************
	// Periodically update the dashboard and any PIDs or sensors
	@Override
	public void updatePeriodic() {
		
		super.updatePeriodic();

		SmartDashboard.putBoolean("Turbo Enabled", turboEnabled);
		SmartDashboard.putBoolean("At Front Limit", atFrontLimit());
	}
	
}
