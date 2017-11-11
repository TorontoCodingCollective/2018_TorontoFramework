package robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import robot.Robot;

/**
 *
 */
public class DefaultChassisCommand extends Command {
	
	public DefaultChassisCommand() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.chassisSubsystem);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		
		double speed = Robot.oi.getSpeed();
		
		Robot.chassisSubsystem.setSpeed(speed);
		
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
