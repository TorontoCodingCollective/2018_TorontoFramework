package robot.oi;

import com.torontocodingcollective.oi.TAxis;
import com.torontocodingcollective.oi.TButton;
import com.torontocodingcollective.oi.TGameController;
import com.torontocodingcollective.oi.TGameController_Logitech;
import com.torontocodingcollective.oi.TStick;
import com.torontocodingcollective.oi.TToggle;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {
	
	private TGameController gameController = new TGameController_Logitech(0);
	
	private TToggle pneumaticsToggle = new TToggle(gameController, TStick.LEFT);
	
	public double getSpeed() {
		return - gameController.getAxis(TStick.LEFT, TAxis.Y);
	}
	
	public double getTurn() {
		return gameController.getAxis(TStick.RIGHT, TAxis.X);
	}
	
	public boolean getPidOff() {
		return gameController.getButton(TButton.X);
	}
	
	public boolean getPidOn() {
		return gameController.getButton(TButton.Y);
	}
	
	public boolean getForwardThrust() {
		return gameController.getButton(TButton.A);
	}

	public boolean getStartDriveDirection() {
		return gameController.getButton(TButton.B);
	}
	
	public boolean getTurboOn() {
		return gameController.getButton(TButton.LEFT_BUMPER);
	}
	
	public boolean getCompressorEnabled() {
		return pneumaticsToggle.get();
	}
	
	
	public void updatePeriodic() {
		pneumaticsToggle.updatePeriodic();
	}
	
	//// CREATING BUTTONS
	// One type of button is a joystick button which is any button on a
	//// joystick.
	// You create one by telling it which joystick it's on and which button
	// number it is.
	// Joystick stick = new Joystick(port);
	// Button button = new JoystickButton(stick, buttonNumber);

	// There are a few additional built in buttons you can use. Additionally,
	// by subclassing Button you can create custom triggers and bind those to
	// commands the same as any other Button.

	//// TRIGGERING COMMANDS WITH BUTTONS
	// Once you have a button, it's trivial to bind it to a button in one of
	// three ways:

	// Start the command when the button is pressed and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenPressed(new ExampleCommand());

	// Run the command while the button is being held down and interrupt it once
	// the button is released.
	// button.whileHeld(new ExampleCommand());

	// Start the command when the button is released and let it run the command
	// until it is finished as determined by it's isFinished method.
	// button.whenReleased(new ExampleCommand());
}
