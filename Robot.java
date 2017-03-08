package org.usfirst.frc.team5505.robot;

import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.SampleRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Ultrasonic;
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
	RobotDrive drive;
	Joystick drivestick;
	Joystick buttonstick;
	CANTalon leftfront, leftback, rightfront, rightback;
	CANTalon intake, winch7, winch8, shooter;
	Servo agitatorservo, cameraservo;
	Talon motoragitator;
	//CameraServer cam1, cam2;
	Ultrasonic ultrason1;
	
	
	final String defaultAuto = "Default";
	final String customAuto = "My Auto";
	SendableChooser<String> chooser = new SendableChooser<>();

	public Robot() {
		leftfront= new CANTalon(6);
		leftback= new CANTalon(3);
		rightfront= new CANTalon(4);
		rightback= new CANTalon(2);
		drive= new RobotDrive(leftfront, leftback, rightfront, rightback);
		//drive.setInvertedMotor(CANTalon.kfrontleft, true);
		drive.setExpiration(0.1);
		
		drivestick= new Joystick(0);
		buttonstick=new Joystick(1);
		intake= new CANTalon(1);
		winch7= new CANTalon(7);
		winch8= new CANTalon(8);
		shooter= new CANTalon(5);
		agitatorservo=new Servo(9);
		motoragitator= new Talon(0);
		cameraservo= new Servo(2);
		ultrason1= new Ultrasonic(0,1);
		
		
	
	}

	@Override
	public void robotInit() {
		CameraServer.getInstance().startAutomaticCapture("gear", 0);
		CameraServer.getInstance().startAutomaticCapture("shooter",1);
		
		chooser.addDefault("Default Auto", defaultAuto);
		chooser.addObject("My Auto", customAuto);
		SmartDashboard.putData("Auto modes", chooser);
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional comparisons to the
	 * if-else structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomous() {
		drive.setSafetyEnabled(false);
		String autoSelected=chooser.getSelected();
		
	}

	/**
	 * Runs the motors with arcade steering.
	 */
	@Override
	public void operatorControl() {
		drive.setSafetyEnabled(true);
		drive.setMaxOutput(0.75);
		//drive
		drive.mecanumDrive_Cartesian(drivestick.getRawAxis(0), drivestick.getRawAxis(1), drivestick.getRawAxis(4), 0);
		//camera servo
		if(drivestick.getRawButton(3)){
			cameraservo.setAngle(0);
		}
		else{
			cameraservo.setAngle(90);
		}
	//intake
		if(drivestick.getRawButton(6)||buttonstick.getRawButton(5)){
			intake.set(0.45);
		}
		else{
			intake.set(0);
		}
		//climb
		if(buttonstick.getRawButton(4)){
			winch7.set(-0.3);
			winch8.set(-0.3);
		}
		else{
			winch7.set(0);
			winch8.set(0);
		}
		
		if(buttonstick.getRawButton(3)){
			winch7.set(-0.8);
			winch8.set(-0.8);
		}
		else{
			winch7.set(0);
			winch8.set(0);
		}
		//shooter
		if(buttonstick.getRawButton(6)){
			shooter.set(0.5);
		}
		else{
			shooter.set(0);
		}
		//agitator
		if (buttonstick.getRawButton(2)){
			motoragitator.set(-.85);
			agitatorservo.setAngle(0);
		}
		else{
			motoragitator.set(0);
			agitatorservo.setAngle(90);
		}
		
		
	}

	/**
	 * Runs during test mode
	 */
	@Override
	public void test() {
	}
}
