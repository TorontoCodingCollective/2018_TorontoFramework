package robot.oi;

import edu.wpi.first.wpilibj.Joystick;

public class GameController extends Joystick {

	public GameController(int port) {
		super(port);
	}
	
	public double getStickAxis(Stick stick, Axis axis) {
		
		switch (stick) {
		
		case LEFT:
			switch (axis) {
			case X:
				return super.getRawAxis(0);
			case Y:
				return super.getRawAxis(1);
			}
			
		case RIGHT:
			switch (axis) {
			case X:
			case Y:
			}
			
		}
		
		return 0;
	}
	
	public double getTrigger(Trigger trigger) {
		return 0;
	}
	
	public boolean getButton(Button button) {
		
		switch (button) {
		case X:
		case Y:
		case A:
		case B:
		}
		return false;
	}
	
	public boolean getButton(Trigger trigger) {
		return false;
	}
	
	public int getPOV() {
		return super.getPOV();
	}
	
}
