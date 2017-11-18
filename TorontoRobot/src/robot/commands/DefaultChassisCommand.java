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
		
		if (Robot.oi.getPidOn()) {
			Robot.chassisSubsystem.setPidActive(true);
		}
		if (Robot.oi.getPidOff()) {
			Robot.chassisSubsystem.setPidActive(false);
		}
		
		double speed = Robot.oi.getSpeed();
		double turn  = Robot.oi.getTurn();
		
		double leftSpeed = 0;
		double rightSpeed = 0;
		
		// If the speed is low, then turn
		if (Math.abs(speed) < 0.2 && Math.abs(turn) > 0.2) {
			leftSpeed = turn;
			rightSpeed = -turn;
		}
		
		// If the turn is low, then go based on speed
		if (Math.abs(speed) > 0.2 && Math.abs(turn) < 0.2) {
			leftSpeed = speed;
			rightSpeed = speed;
		}
		
		Robot.chassisSubsystem.setSpeed(leftSpeed, rightSpeed);
		
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
