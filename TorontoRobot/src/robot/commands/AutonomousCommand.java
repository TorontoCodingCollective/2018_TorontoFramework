package robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import robot.Robot;
import robot.commands.drive.DriveDirectionCommand;
import robot.commands.drive.MotionProfileCommand;

/**
 *
 */
public class AutonomousCommand extends CommandGroup {

    public AutonomousCommand() {

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
    	System.out.println("Robot Position : " + Robot.oi.getRobotStartPosition());
    	System.out.println("Close Switch   : " + Robot.gameData.getCloseSwitch());
    	
    	addSequential(new DriveDirectionCommand(0, .5, 5));
//    	addSequential(new MotionProfileCommand(5));
//    	addSequential(new ArcCommand(100, 0, 310, 1.0));
//    	addSequential(new ArcCommand(140, 310, 350, 1.0));
    }
}
