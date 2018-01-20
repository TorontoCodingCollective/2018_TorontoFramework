package robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;

/**
 *
 */
public class DriveDirectionCommand extends Command {

	private double direction;
	private double speed;
	
    public DriveDirectionCommand(double direction, double speed) {
    	this.direction = direction;
    	this.speed     = speed;
    	
    	requires(Robot.chassisSubsystem);
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	
    	Robot.chassisSubsystem.enableGyroPid();
    	Robot.chassisSubsystem.setDirection(direction);
    	
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	// Drive with steering
    	double leftSpeed = speed;
    	double rightSpeed = speed;
    	
    	double steering = Robot.chassisSubsystem.getGyroPidSteering();
    	
    	if (steering > 0) {
    		rightSpeed = leftSpeed * (1.0 - steering);
    	}

    	if (steering < 0) {
    		leftSpeed = rightSpeed * (1.0 + steering);
    	}
    	
    	Robot.chassisSubsystem.setSpeed(leftSpeed, rightSpeed);
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	if (timeSinceInitialized() > 5) { 
    		return true; 
		}
    	return false;
    }

    // Called once after isFinished returns true
    protected void end() {

    	Robot.chassisSubsystem.disableGyroPid();
    	Robot.chassisSubsystem.setSpeed(0, 0);
    	
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}
