package robot;

import com.torontocodingcollective.sensors.encoder.TEncoder;
import com.torontocodingcollective.speedcontroller.TCanSpeedController;
import com.torontocodingcollective.speedcontroller.TCanSpeedControllerType;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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

	TCanSpeedController canTalon4 = new TCanSpeedController(TCanSpeedControllerType.TALON_SRX, 4);
	TCanSpeedController canVictor5 = new TCanSpeedController(TCanSpeedControllerType.VICTOR_SPX, 4);
	TCanSpeedController canTalon0 = new TCanSpeedController(TCanSpeedControllerType.TALON_SRX, 0);
	TCanSpeedController canTalon2 = new TCanSpeedController(TCanSpeedControllerType.TALON_SRX, 2);
	TCanSpeedController canTalon3 = new TCanSpeedController(TCanSpeedControllerType.TALON_SRX, 3);
	TEncoder encoder = canTalon4.getEncoder();
	
	Solenoid solenoid = new Solenoid(0);
	
	public Robot() {
	}

	@Override
	public void robotInit() {
		solenoid.set(true);
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
			canTalon4.set(-driverController.getRawAxis(1));
			SmartDashboard.putNumber("Motor Setpoint", canTalon4.get());
			SmartDashboard.putNumber("Motor Setpoint", canTalon0.get());
			SmartDashboard.putNumber("Encoder", encoder.get());
			
			if (driverController.getRawButton(5)) {
				solenoid.set(false);
				
			}
			else {
				solenoid.set(true);
			}
			
			try {
				this.wait(100);
			}
			catch (Exception e) {
				System.out.println("Wait Failed");
			}
		}
	}

	/**
	 * Runs during test mode
	 */
	@Override
	public void test() {
	}
}
