package robot.oi;

import edu.wpi.first.wpilibj.Joystick;

public abstract class GameController extends Joystick {

	public GameController(int port) {
		super(port);
	}

	public abstract double getAxis(Stick stick, Axis axis);

	public abstract boolean getButton(Button button);
	public abstract boolean getButton(Stick stick);
	
	public boolean getButton(Trigger trigger) {
		return getTrigger(trigger) > 0.3;
	};

	public abstract double getTrigger(Trigger trigger);
}
