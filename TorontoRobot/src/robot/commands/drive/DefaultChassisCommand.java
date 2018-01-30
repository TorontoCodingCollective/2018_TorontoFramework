package robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import robot.Robot;
import robot.commands.drive.DriveDirectionCommand;

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


		if (Robot.oi.getTurboOn()) {
			Robot.chassisSubsystem.enableTurbo();
		}
		else {
			Robot.chassisSubsystem.disableTurbo();
		}

		if (Robot.oi.reset()){
			Robot.chassisSubsystem.resetGyroAngle();
			Robot.chassisSubsystem.resetEncoders();
		}

		if (Robot.oi.getSpeedPidEnabled()) {
			Robot.chassisSubsystem.enableSpeedPids();
		}
		else {
			Robot.chassisSubsystem.disableSpeedPids();
		}


		if (Robot.oi.getForwardThrust()) {
			Scheduler.getInstance().add(new DriveTimeCommand(0));
		}

		if (Robot.oi.getStartDriveDirection()) {
			Scheduler.getInstance().add(new DriveDirectionCommand(0, .8, 10));
		}
		if (Robot.oi.getArcCommand() == 90){
			Scheduler.getInstance().add(new ArcCommand(200, Robot.chassisSubsystem.getGryoAngle(), Robot.chassisSubsystem.getGryoAngle() + 90, 1));
		}
		if (Robot.oi.getArcCommand() == 270){
			Scheduler.getInstance().add(new ArcCommand(200, Robot.chassisSubsystem.getGryoAngle(), Robot.chassisSubsystem.getGryoAngle() - 90, 1));
		}

		double speed = Robot.oi.getSpeed();
		double turn  = Robot.oi.getTurn();

		double leftSpeed = 0;
		double rightSpeed = 0;
		
		//straight driving
		if (Math.abs(speed) > 0.05 && Math.abs(turn) < 0.03) {
			leftSpeed = speed;
			rightSpeed = speed;
		}
		//straight turning
		if (Math.abs(turn) > 0.03 && Math.abs(speed) < 0.05) {
			leftSpeed = turn;
			rightSpeed = -turn;
		}
		
		if ( speed > 0.05 && turn > 0.03) {
			leftSpeed = speed;
			rightSpeed = speed - (turn / 2);
		}
		if ( speed > 0.05 && turn < -0.03) {
			leftSpeed = speed + (turn / 2);
			rightSpeed = speed;
		}
		if ( speed < -0.05 && turn > 0.03) {
			leftSpeed = speed - (turn / 2);
			rightSpeed = speed;
		}
		if ( speed < -0.05 && turn < -0.03) {
			leftSpeed = speed;
			rightSpeed = speed + (turn / 2);
		}
		//System.out.println(speed);
		//System.out.println(turn);
		
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
