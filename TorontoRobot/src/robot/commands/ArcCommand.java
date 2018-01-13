package robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import robot.Robot;

public class ArcCommand extends Command {
	
	private double dist;
	private double startDirection;
	private double endDirection;
	private double turnangle;
	private double rWidth = 69.69125;
	private double rSpeed;
	private double lSpeed;
	private double speed;
	
	public ArcCommand(double dist, double startDirection, double endDirection, double speed) {
		this.dist = dist;
		this.startDirection = startDirection;
		this.endDirection = endDirection;
		this.speed = speed;
		this.turnangle = (this.endDirection - this.startDirection);
		if(this.turnangle > 180){
			this.turnangle -= 360;
		}
		else if(this.turnangle < -180) {
			this.turnangle += 360;
		}
		requires(Robot.chassisSubsystem);
	}
	
	public void initialize() {
		
		double radius = this.dist/Math.toRadians(this.turnangle);
		double fastSpeed = this.speed;
		double slowSpeed = ((radius-(this.rWidth/2))* Math.toRadians(this.turnangle)) / 
				((radius+(this.rWidth/2))* Math.toRadians(this.turnangle)) * this.speed;
		
		if (this.turnangle < 0) {
			this.rSpeed = slowSpeed;
			this.lSpeed = fastSpeed;
		}
		else if (this.turnangle > 0) {
			this.rSpeed = fastSpeed;
			this.lSpeed = slowSpeed;
		}
		Robot.chassisSubsystem.setSpeed(lSpeed, rSpeed);
	}

	protected boolean isFinished() {
		if (Math.abs(Robot.chassisSubsystem.getGryoAngle() - this.endDirection) <= 2){
			return true;
		}
		return false;
	}

}
