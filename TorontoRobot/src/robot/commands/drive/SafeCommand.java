package robot.commands.drive;

import edu.wpi.first.wpilibj.command.Command;

public class SafeCommand extends Command {

	private final double maxTimeSec;
	
	public SafeCommand(double maxTimeSec) {
		this.maxTimeSec = maxTimeSec;
	}
	
    protected boolean isFinished() {
        return false;
    }

    public boolean isCancelled() {
    	return false;
    }
    
    public boolean isTimedOut() {
    	timeSinceInitialized();
    	return false;
    }
}
