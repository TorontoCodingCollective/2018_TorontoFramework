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
	
	SpeedPID leftPid = new SpeedPID(1.0);
	SpeedPID rightPid = new SpeedPID(1.0);
	
	public void init() {
		rightMotor.setInverted(true);
	}
	
	public void initDefaultCommand() {
		setDefaultCommand(new DefaultChassisCommand());
	}
	
	public void setSpeed(double speedSetpoint) {
		
		double leftNormalizedSpeed = leftEncoder.getRate() /
				RobotConst.MAX_DRIVE_ENCODER_SPEED;
		
		double leftMotorPIDOutput = 
				leftPid.calculate(speedSetpoint, leftNormalizedSpeed);
		
		SmartDashboard.putNumber("Left Speed", leftMotorPIDOutput);
		SmartDashboard.putNumber("Right Speed", speedSetpoint);

		leftMotor.set(leftMotorPIDOutput);
		rightMotor.set(speedSetpoint);
	}

	public void setSpeed(double leftSpeedSetpoint, double rightSpeedSetpoint) {

		double leftNormalizedSpeed = leftEncoder.getRate() /
				RobotConst.MAX_DRIVE_ENCODER_SPEED;
		
		double leftMotorPIDOutput = 
				leftPid.calculate(leftSpeedSetpoint, leftNormalizedSpeed);
		
		SmartDashboard.putNumber("Left Speed", leftMotorPIDOutput);
		SmartDashboard.putNumber("Right Speed", rightSpeedSetpoint);

		leftMotor.set(leftMotorPIDOutput);
		rightMotor.set(rightSpeedSetpoint);
	}

	public void updatePeriodic() {
		
		

		SmartDashboard.putNumber("Left Encoder Raw", leftEncoder.getRaw());
		SmartDashboard.putNumber("Left Encoder Speed", leftEncoder.getRate());
		SmartDashboard.putNumber("Right Encoder Raw", rightEncoder.getRaw());
		SmartDashboard.putNumber("Right Encoder Speed", rightEncoder.getRate());
		SmartDashboard.putData("LeftPid", leftPid);
		SmartDashboard.putData("RightPid", rightPid);
	}
	
}
