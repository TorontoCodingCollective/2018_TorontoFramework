package robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotConst;
import robot.RobotMap;
import robot.commands.DefaultChassisCommand;

/**
 *
 */
public class ChassisSubsystem2 extends DriveSubsystem {
	
	DigitalInput frontLimitSwitch = new DigitalInput(4);
	
	public ChassisSubsystem2() {
		super(
			new Victor(RobotMap.LEFT_MOTOR),  RobotConst.NOT_INVERTED,
			new Victor(RobotMap.RIGHT_MOTOR), RobotConst.INVERTED,
			new Encoder(0, 1),                RobotConst.NOT_INVERTED,
			new Encoder(2, 3),                RobotConst.INVERTED,
			1.0,
			RobotConst.MAX_DRIVE_ENCODER_SPEED);
	}

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
	public void initDefaultCommand() {
		
		setDefaultCommand(new DefaultChassisCommand());
	}
	

	// Periodically update the dashboard and any PIDs or sensors
	public void updatePeriodic() {
		
		super.updatePeriodic();

		SmartDashboard.putBoolean("At Front Limit", atFrontLimit());
	}
	
}
