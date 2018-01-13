package robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SampleRobot;

/**
 * This is a demo program showing the use of the RobotDrive class. The
 * SampleRobot class is the base of a robot application that will automatically
 * call your Autonomous and OperatorControl methods at the right time as
 * controlled by the switches on the driver station or the field controls.
 *
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the SampleRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 *
 * WARNING: While it may look like a good choice to use for your code if you're
 * inexperienced, don't. Unless you know what you are doing, complex code will
 * be much more difficult under this system. Use IterativeRobot or Command-Based
 * instead if you're new.
 */
public class Robot extends SampleRobot {

	Joystick driverController = new Joystick(0);

	TalonSRX canTalon = new TalonSRX(0);
	
	public Robot() {
	}

	@Override
	public void robotInit() {
	}

	@Override
	public void autonomous() {
	}

	/**
	 * Runs the motors
	 */
	@Override
	public void operatorControl() {
		while (true) {
			canTalon.set(ControlMode.PercentOutput, -driverController.getRawAxis(1));
		}
	}

	/**
	 * Runs during test mode
	 */
	@Override
	public void test() {
	}
}
