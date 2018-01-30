package robot.oi;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoSelector {
	
	private SendableChooser<String> robotStartPosition;
	
	private static final String ROBOT_LEFT   = "Robot Left";
	private static final String ROBOT_CENTER = "Robot Center";
	private static final String ROBOT_RIGHT  = "Robot Right";

	public AutoSelector() {

		// Robot Position Options
		robotStartPosition = new SendableChooser<String>();
		robotStartPosition.addObject (ROBOT_LEFT, ROBOT_LEFT);
		robotStartPosition.addDefault(ROBOT_CENTER, ROBOT_CENTER);
		robotStartPosition.addObject (ROBOT_RIGHT,  ROBOT_RIGHT);
		
		SmartDashboard.putData("Robot Start", robotStartPosition);
		
	}
	
	/** 
	 * Get the robot starting position on the field.
	 * @return 'L' for left, 'R' for right or 'C' for center
	 */
	public char getRobotStartPosition() {
		
		switch (robotStartPosition.getSelected()) {
		
		case ROBOT_LEFT:    return 'L';
		case ROBOT_CENTER:  return 'C';
		case ROBOT_RIGHT:  	return 'R';
		
		default: return ' ';
		}
	}
	
	public void updatePeriodic() {
		
		SmartDashboard.putString("Selected Robot Position", String.valueOf(getRobotStartPosition()));
	}
}
