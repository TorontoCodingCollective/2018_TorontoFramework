package robot.oi;

public class GameController_Logitech extends GameController {

	public GameController_Logitech(int port) {
		super(port);
	}
	
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

	@Override
	public boolean getButton(Stick stick) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
