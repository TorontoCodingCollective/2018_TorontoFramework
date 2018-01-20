package robot.commands.drive;

import robot.Robot;

public class ArcCommand extends TSafeCommand {
	
	private double dist;
	private double startDirection;
	private double endDirection;
	private double turnangle;
	private double rWidth = 69.69125;//cm
	private double rSpeed;
	private double lSpeed;
	private double speed;
	
	public ArcCommand(double dist, double startDirection, double endDirection, double speed) {
		super(15);
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
		System.out.println(this.turnangle);//90//-90
		requires(Robot.chassisSubsystem);
	}
	
	public void initialize() {
		
		double radius = this.dist/Math.abs(Math.toRadians(this.turnangle));
		double fastSpeed = this.speed;
		double slowSpeed = ((radius-(this.rWidth/2))* Math.toRadians(this.turnangle)) / 
				((radius+(this.rWidth/2))* Math.toRadians(this.turnangle)) * this.speed;
		
		//System.out.println((radius-(this.rWidth/2))* Math.toRadians(this.turnangle));//345.26462024512756 //  454.7353797548724 
		//System.out.println((radius+(this.rWidth/2))* Math.toRadians(this.turnangle));// 454.7353797548724 // 345.26462024512756 
		//System.out.println(fastSpeed);// 0.4 // 0.4
		//System.out.println(slowSpeed);// 0.5268253427553903 // 0.30370596669319583 
		if (this.turnangle < 0) {
			this.rSpeed = slowSpeed;
			this.lSpeed = fastSpeed;
		}
		else if (this.turnangle > 0) {
			this.rSpeed = fastSpeed;
			this.lSpeed = slowSpeed;
		}
		System.out.println(this.rSpeed);// 0.4 // 0.30370596669319583 
		System.out.println(this.lSpeed);// 0.5268253427553903 // 0.4
		Robot.chassisSubsystem.setSpeed(lSpeed, rSpeed);
	}

	protected boolean isFinished() {
		if (super.isFinished()) {
			return true;
		}
		if (Math.abs(Robot.chassisSubsystem.getGryoAngle() - this.endDirection) <= 2){
			return true;
		}
		return false;
	}

}
