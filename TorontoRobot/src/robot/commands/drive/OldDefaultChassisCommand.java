package robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import robot.Robot;

/**
 *
 */
public class OldDefaultChassisCommand extends Command {
	
	public OldDefaultChassisCommand() {
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
			Robot.chassisSubsystem.enableSpeedPids();
		}
		if (Robot.oi.getPidOff()) {
			Robot.chassisSubsystem.disableSpeedPids();
		}
		
		if (Robot.oi.getForwardThrust()) {
			Scheduler.getInstance().add(new ForwardThrustCommand(0));
		}
		
		if (Robot.oi.getStartDriveDirection()) {
			Scheduler.getInstance().add(new DriveDirectionCommand(0, .3));
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