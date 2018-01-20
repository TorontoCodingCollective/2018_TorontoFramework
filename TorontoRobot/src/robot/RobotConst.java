package robot;

public class RobotConst {

	public static final boolean INVERTED = true;
	public static final boolean NOT_INVERTED = false;

	//*********************************************************
	// Drive Constants
	//*********************************************************
	public static final boolean RIGHT_MOTOR_ORIENTATION = NOT_INVERTED;
	public static final boolean LEFT_MOTOR_ORIENTATION = INVERTED;
	
	public static final boolean RIGHT_ENCODER_ORIENTATION = INVERTED;
	public static final boolean LEFT_ENCODER_ORIENTATION = NOT_INVERTED;
	
	public static final double MAX_LOW_GEAR_SPEED = 1700.0;
	public static final double MAX_HIGH_GEAR_SPEED = 1700.0;
	
	public static final double DRIVE_GYRO_PID_KP = .1;
	public static final double DRIVE_GYRO_PID_KI = 0;
	
	public static final double DRIVE_SPEED_PID_KP = 1.0;
}
