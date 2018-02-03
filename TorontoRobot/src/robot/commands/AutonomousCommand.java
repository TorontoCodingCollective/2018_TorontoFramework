package robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import robot.Robot;
import robot.commands.drive.ArcCommand;
import robot.commands.drive.DriveDistanceCommand;
import robot.oi.AutoSelector;

/**
 *
 */
public class AutonomousCommand extends CommandGroup {

	public static final char LEFT 				= 'L';
	public static final char RIGHT 				= 'R';
	public static final char CENTER 			= 'C';
	public static final String ROBOT_LEFT   	= "Robot Left";
	public static final String ROBOT_CENTER 	= "Robot Center";
	public static final String ROBOT_RIGHT  	= "Robot Right";
	public static final String SWITCH 			= "Switch";
	public static final String SCALE 			= "Scale";
	public static final String CROSS  			= "Cross";
	public static final String NONE  			= "None";

	public AutonomousCommand() {
		//getting info
		String robotStartPosition 	= AutoSelector.getRobotStartPosition();
		String firstAction 			= AutoSelector.getRobotFirstAction();
		String secondAction 		= AutoSelector.getRobotSecondAction();
		char closeSwitch 			= Robot.gameData.getCloseSwitch();
		char scale 					= Robot.gameData.getScale();

		// Add Commands here:
		// e.g. addSequential(new Command1());
		//      addSequential(new Command2());
		// these will run in order.

		// To run multiple commands at the same time,
		// use addParallel()
		// e.g. addParallel(new Command1());
		//      addSequential(new Command2());
		// Command1 and Command2 will run in parallel.

		// A command group will require all of the subsystems that each member
		// would require.
		// e.g. if Command1 requires chassis, and Command2 requires arm,
		// a CommandGroup containing them would require both the chassis and the
		// arm.

		System.out.println("Auto Command Configuration");
		System.out.println("--------------------------");
		System.out.println("Robot Position : " + robotStartPosition);
		System.out.println("Close Switch   : " + closeSwitch);
		System.out.println("Scale		   : " + scale);

		//overrides
		if (robotStartPosition.equals(ROBOT_CENTER) && !firstAction.equals(SWITCH)) {
			firstAction = SWITCH;
			System.out.println("overriding first action to switch");
		}
		if (robotStartPosition.equals(ROBOT_RIGHT) && secondAction.equals(SWITCH) && firstAction.equals(SWITCH) && closeSwitch == LEFT) {
			firstAction = CROSS;
		}
		if (robotStartPosition.equals(ROBOT_LEFT) && secondAction.equals(SWITCH) && firstAction.equals(SWITCH) && closeSwitch == RIGHT) {
			firstAction = CROSS;
		}



		//run the auto
		if (robotStartPosition.equals(ROBOT_LEFT)) {
			//robot starts to the left
			//first action
			if (firstAction.equals(SWITCH)) {
				//switch action is selected
				addSequential(new ArcCommand(80, 0, 45, 1.0));
				addSequential(new DriveDistanceCommand(25, 310, 1.0, 3.0, false));
				addSequential(new ArcCommand(120, 45, 0, 1.0));
			}

			else if (firstAction.equals(SCALE)) {
				//scale action is selected

				if (scale == LEFT){
					//scale is on the left side
				}
				else{
					//scale is on the right side	
				}
			}

			else{
				//cross action is selected
				addSequential(new DriveDistanceCommand(160, 310, 1.0, 3.0, false));
			}
			
			//System.out.println("Starting second action");

			if (secondAction.equals(SWITCH)) {

				//System.out.println("Switch action selected");

				if (closeSwitch == LEFT){
					//System.out.println("Executing left switch command");
				}
				else{
					//System.out.println("Executing right switch command");	
				}
			}

			else if (secondAction.equals(SCALE)) {
				if (scale == LEFT){
					//System.out.println("Executing left scale command");
				}
				else{
					//System.out.println("Executing right scale command");	
				}
			}
			else{
				//System.out.println("No second action");
			}
		}

		else if (robotStartPosition.equals(ROBOT_RIGHT)) {
			//robot starts to the right
			//first action
			if (firstAction.equals(SWITCH)) {
				//switch action is selected

			}

			else if (firstAction.equals(SCALE)) {
				//scale action is selected

				if (scale == RIGHT){
					//scale is on the left side
				}
				else{
					//scale is on the right side	
				}
			}

			else{
				//cross action is selected
			}

			//System.out.println("Starting second action");

			if (secondAction.equals(SWITCH)) {

				//System.out.println("Switch action selected");

				if (closeSwitch == LEFT){
					//System.out.println("Executing left switch command");
				}
				else{
					//System.out.println("Executing right switch command");	
				}
			}

			else if (secondAction.equals(SCALE)) {
				if (scale == LEFT){
					//System.out.println("Executing left scale command");
				}
				else{
					//System.out.println("Executing right scale command");	
				}
			}
			else{
				//System.out.println("No second action");
			}

		}

		else{

			//System.out.println("Robot is in the center");
			//System.out.println("Starting first action");

			if (firstAction.equals(SWITCH)) {

				//System.out.println("Switch action selected");

				if (closeSwitch == LEFT){

					//System.out.println("Executing left switch command");

					addSequential(new ArcCommand(100, 0, 310, 1.0));
					addSequential(new DriveDistanceCommand(20, 310, 1.0, 3.0, false));
					addSequential(new ArcCommand(140, 310, 350, 1.0));
				}
				else{
					//System.out.println("Executing right switch command");	
					addSequential(new ArcCommand(80, 0, 45, 1.0));
					addSequential(new DriveDistanceCommand(25, 310, 1.0, 3.0, false));
					addSequential(new ArcCommand(120, 45, 0, 1.0));
				}
			}

			//System.out.println("Starting second action");

			if (secondAction.equals(SWITCH)) {

				//System.out.println("Switch action selected");

				if (closeSwitch == LEFT){
					//System.out.println("Executing left switch command");
				}
				else{
					//System.out.println("Executing right switch command");	
				}
			}

			else if (secondAction.equals(SCALE)) {
				if (scale == LEFT){
					//System.out.println("Executing left scale command");
				}
				else{
					//System.out.println("Executing right scale command");	
				}
			}
			else{
				//System.out.println("No second action");
			}
		}	
	}
}
