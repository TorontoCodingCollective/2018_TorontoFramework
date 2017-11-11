package robot.subsystems;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import robot.RobotMap;
import robot.commands.DefaultChassisCommand;

/**
 *
 */
public class ChassisSubsystem extends Subsystem {
	
	SpeedController leftMotor  = new Victor(RobotMap.LEFT_MOTOR);
	SpeedController rightMotor = new Victor(RobotMap.RIGHT_MOTOR);
	
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

	public void setSpeed(double leftSpeed, double rightSpeed) {
		leftMotor.set(leftSpeed);
		rightMotor.set(rightSpeed);
	}
}
