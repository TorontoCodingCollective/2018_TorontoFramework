package robot.oi;

import edu.wpi.first.wpilibj.Joystick;

public abstract class GameController extends Joystick {

	public GameController(int port) {
		super(port);
	}

	public abstract double getAxis(Stick stick, Axis axis);

	public abstract double getTrigger(Trigger trigger);

	public abstract boolean getButton(Button button);
	public abstract boolean getButton(Stick stick);
	public abstract boolean getButton(Trigger trigger);
}
