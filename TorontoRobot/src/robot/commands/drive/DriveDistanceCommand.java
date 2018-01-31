package robot.commands.drive;

import robot.Robot;
import robot.RobotConst;

public class DriveDistanceCommand extends DriveDirectionCommand {
	double distance = 0; // in inches 

	public DriveDistanceCommand(double distance, double direction, double speed, double timeout, boolean brakeWhenFinished) {
		super(direction, speed, timeout, brakeWhenFinished);
		this.distance = distance;
	}
	protected void initialize() {
		//System.out.println("starting drive");
		Robot.chassisSubsystem.resetEncoders();

	}
	protected boolean isFinished() {

		if (super.isFinished()) {
			return true;
		}
		if (distance * RobotConst.ENCODER_COUNTS_PER_INCH < Robot.chassisSubsystem.getEncoderDistance() + 20){
			//System.out.println("ending drive");
			return true;
		}
		return false;
	}
}
