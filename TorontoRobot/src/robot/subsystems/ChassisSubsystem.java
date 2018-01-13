package robot.subsystems;

import com.torontocodingcollective.sensors.gyro.TAnalogGyro;
import com.torontocodingcollective.speedcontroller.TCanSpeedController;
import com.torontocodingcollective.speedcontroller.TCanSpeedControllerType;
import com.torontocodingcollective.subsystem.TGryoDriveSubsystem;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotConst;
import robot.RobotMap;
import robot.commands.DefaultChassisCommand;

/**
 *
 */
public class ChassisSubsystem extends TGryoDriveSubsystem {
	
	DigitalInput frontLimitSwitch = new DigitalInput(4);
	
	public ChassisSubsystem() {
		
		// Uncomment this block to use CAN based speed controllers
		super(
			new TAnalogGyro(0, RobotConst.INVERTED),
			new TCanSpeedController(TCanSpeedControllerType.TALON_SRX, RobotMap.LEFT_MOTOR_CAN_ADDRESS,  RobotConst.NOT_INVERTED, RobotMap.LEFT_FOLLOWER_CAN_ADDRESS), 
			new TCanSpeedController(TCanSpeedControllerType.TALON_SRX, RobotMap.RIGHT_MOTOR_CAN_ADDRESS, RobotConst.INVERTED,     RobotMap.RIGHT_FOLLOWER_CAN_ADDRESS), 
			new Encoder(0, 1),                RobotConst.NOT_INVERTED,
			new Encoder(2, 3),                RobotConst.INVERTED,
			1.0,
			0.1,
			RobotConst.MAX_DRIVE_ENCODER_SPEED);

		// Uncomment this constructor to use PWM based Speed controllers
//		super(
//				new TAnalogGyro(0, RobotConst.INVERTED),
//				new TPwmSpeedController(TPwmSpeedControllerType.VICTOR, RobotMap.LEFT_MOTOR,  RobotConst.NOT_INVERTED, RobotMap.LEFT_FOLLOWER_PORT), 
//				new TPwmSpeedController(TPwmSpeedControllerType.VICTOR, RobotMap.RIGHT_MOTOR, RobotConst.INVERTED,     RobotMap.RIGHT_FOLLOWER_PORT), 
//				new Encoder(0, 1),                RobotConst.NOT_INVERTED,
//				new Encoder(2, 3),                RobotConst.INVERTED,
//				1.0,
//				0.1,
//				RobotConst.MAX_DRIVE_ENCODER_SPEED);
	}

	@Override
	public void init() {
		TAnalogGyro gyro = (TAnalogGyro) super.gyro;
		gyro.setSensitivity(0.0017);
	};
	
	/**
	 * At front limit
	 * @return {@literal true} if at the limit, {@literal false} otherwise.
	 */
	public boolean atFrontLimit() {
		// The limit switch we are using is wired to return true when 
		// not activated and false otherwise.
		return !frontLimitSwitch.get();
	}
	
	// Initialize the default command for the Chassis subsystem.
	@Override
	public void initDefaultCommand() {
		
		setDefaultCommand(new DefaultChassisCommand());
	}
	

	// Periodically update the dashboard and any PIDs or sensors
	@Override
	public void updatePeriodic() {
		
		super.updatePeriodic();

		SmartDashboard.putBoolean("At Front Limit", atFrontLimit());
	}
	
}
