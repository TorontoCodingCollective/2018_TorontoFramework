package robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
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
		
		if (Robot.oi.getForwardThrust()) {
			Scheduler.getInstance().add(new ForwardThrustCommand());
		}
		
		double speed = Robot.oi.getSpeed();
		double turn  = Robot.oi.getTurn();
		
		double leftSpeed = 0;
		double rightSpeed = 0;
		//curving in the directions
		
		if (speed > 0.8 && turn > 0.8){
			leftSpeed = 1.0;
			rightSpeed = 0.9;
		}
		if (speed < -0.8 && turn < -0.8){
			leftSpeed = -1.0;
			rightSpeed = -0.9;
		}
		if (speed < -0.8 && turn > 0.8){
			leftSpeed = -0.9;
			rightSpeed = -1.0;
		}
		if (speed > 0.8 && turn < -0.8){
			leftSpeed = 0.9;
			rightSpeed = 1.0;
		}
		//slow curves
		//slow speed high turning
		if ((speed < 0.8 && speed > 0.2) && turn > 0.8){
			leftSpeed = 0.6;
			rightSpeed = 0.4;
		}
		if ((speed > -0.8 && speed < -0.2) && turn > 0.8){
			leftSpeed = -0.4;
			rightSpeed = -0.6;
		}
		if ((speed > 0.2 && speed < 0.8) && turn < -0.8){
			leftSpeed = 0.6;
			rightSpeed = 0.4;
		}
		if ((speed > -0.8 && speed < -0.2) && turn < -0.8){
			leftSpeed = 0.4;
			rightSpeed = 0.6;
		}
		//high speed small adjustments
		if (speed > 0.8 && (turn < 0.8 && turn > 0.2)){
			leftSpeed = 1.0;
			rightSpeed = 0.95;
		}
		if (speed < -0.8 && (turn > -0.8 && turn < -0.2)){
			leftSpeed = -1.0;
			rightSpeed = -0.95;
		}
		if (speed < -0.8 && (turn < 0.8 && turn > 0.2)){
			leftSpeed = -1.0;
			rightSpeed = -0.95;
		}
		if (speed > 0.8 && (turn > -0.8 && turn < -0.2)){
			leftSpeed = 1.0;
			rightSpeed = -0.95;
		}
		
		// If the speed is low, then turn
		if (Math.abs(speed) < 0.2 && Math.abs(turn) > 0.8) {
			leftSpeed = turn;
			rightSpeed = -turn;
		}
		if (Math.abs(speed) < 0.2 && (Math.abs(turn) > 0.2 && Math.abs(turn) < 0.8)){
			leftSpeed = 0.4;
			rightSpeed = -0.4;
		}
		// If the turn is low, then go based on speed
		if (Math.abs(speed) > 0.8 && Math.abs(turn) < 0.2) {
			leftSpeed = speed;
			rightSpeed = speed;
		}
		if ((Math.abs(speed) < 0.8 && Math.abs(speed) > 0.2) && Math.abs(turn) < 0.2){
			leftSpeed = 0.4;
			rightSpeed = 0.4;
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
