package robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import robot.RobotConst;
import robot.RobotMap;
import robot.commands.DefaultChassisCommand;

/**
 *
 */
public class ChassisSubsystem extends Subsystem {
	
	SpeedController leftMotor  = new Victor(RobotMap.LEFT_MOTOR);
	SpeedController rightMotor = new Victor(RobotMap.RIGHT_MOTOR);
	
	Encoder leftEncoder = new Encoder(0, 1);
	Encoder rightEncoder = new Encoder(2, 3, RobotConst.INVERTED);
	
	public void init() {
		rightMotor.setInverted(true);
	}
	
	public void initDefaultCommand() {
		
		setDefaultCommand(new DefaultChassisCommand());
	}
	
	public void setSpeed(double speed) {
		leftMotor.set(speed);
		rightMotor.set(speed);
		
		SmartDashboard.putNumber("Left Speed", speed);
		SmartDashboard.putNumber("Right Speed", speed);
	}

	public void setSpeed(double leftSpeed, double rightSpeed) {
		leftMotor.set(leftSpeed);
		rightMotor.set(rightSpeed);

		SmartDashboard.putNumber("Left Speed", leftSpeed);
		SmartDashboard.putNumber("Right Speed", rightSpeed);
	}

	public void updatePeriodic() {

		SmartDashboard.putNumber("Left Encoder Raw", leftEncoder.getRaw());
		SmartDashboard.putNumber("Right Encoder Raw", rightEncoder.getRaw());
	}
	
}
