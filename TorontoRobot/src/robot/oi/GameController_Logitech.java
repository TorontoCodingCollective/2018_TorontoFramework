package robot.oi;

public class GameController_Logitech extends GameController {

	public GameController_Logitech(int port) {
		super(port);
	}

	@Override
	public double getAxis(Stick stick, Axis axis) {
		
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
				return super.getRawAxis(4);
			case Y:
				return super.getRawAxis(5);
			}
			
		default: return 0.0;
		}
	}
	
	@Override
	public double getTrigger(Trigger trigger) {
		
		switch (trigger) {
		case LEFT:
			return getRawAxis(2);
		case RIGHT:
			return getRawAxis(3);
			
		default: return 0.0;
		}
	}
	
	@Override
	public boolean getButton(Button button) {
		
		switch (button) {
		case A:
			return getRawButton(1);
		case B:
			return getRawButton(2);
		case X:
			return getRawButton(3);
		case Y:
			return getRawButton(4);
			
		case BACK:
			return getRawButton(7);
		case START:
			return getRawButton(8);

		case LEFT_BUMPER:
			return getRawButton(5);
		case RIGHT_BUMPER:
			return getRawButton(6);
	
		default: return false;
		}
	}
	
	@Override
	public boolean getButton(Trigger trigger) {
		
		switch (trigger) {
		case LEFT:
			return getRawAxis(2) > 0.3;
		case RIGHT:
			return getRawAxis(3) > 0.3;
			
		default: return false;
		}
	}

	@Override
	public boolean getButton(Stick stick) {

		switch (stick) {
		case LEFT:
			return getRawButton(9);
		case RIGHT:
			return getRawButton(10);
			
		default: return false;
		}
	}
}
