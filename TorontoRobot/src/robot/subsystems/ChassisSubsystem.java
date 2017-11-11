package robot.subsystems;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import robot.commands.DefaultChassisCommand;

/**
 *
 */
public class ChassisSubsystem extends Subsystem {
	
	SpeedController leftMotor  = new Victor(0);
	SpeedController rightMotor = new Victor(1);
	
	public void init() {
		rightMotor.setInverted(true);
	}
	
	public void initDefaultCommand() {
		
		setDefaultCommand(new DefaultChassisCommand());
	}
	
	public void setSpeed(double speed) {
		leftMotor.set(speed);
		rightMotor.set(speed);
	}
}
