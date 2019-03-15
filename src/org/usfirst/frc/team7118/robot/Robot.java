package org.usfirst.frc.team7118.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team7118.robot.Scotstants;
import org.usfirst.frc.team7118.robot.Scotstants.AutoPath;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	// Defining Variables
	Drive drive;
	Joystick joyR, joyL;
	Auto auto;
	Gyroscope gyro;
	Intake intake;
	Lifter lifter;
	Timer timer;
	SendableChooser<Scotstants.AutoPath> autoChooser;
	
	// This function is run when the robot is first initialized
	@Override
	public void robotInit() {
		gyro = new Gyroscope();
		intake = new Intake();
		lifter = new Lifter();
		joyR = new Joystick(Scotstants.JOY_R_PORT);
		joyL = new Joystick(Scotstants.JOY_L_PORT);
		drive = new Drive(gyro);
		auto = new Auto(drive, intake, lifter);
//		autoChooser = new SendableChooser<Scotstants.AutoPath>();
//		autoChooser.addDefault("Select Autonomous", null);
//		autoChooser.addObject("Center", Scotstants.AutoPath.CENTER);
//		autoChooser.addObject("Left", Scotstants.AutoPath.LEFT);
//		autoChooser.addObject("Right", Scotstants.AutoPath.RIGHT);
//		SmartDashboard.putData("Auto Mode Chooser", autoChooser);
		CameraServer.getInstance().startAutomaticCapture();
		CameraServer.getInstance().startAutomaticCapture();

	}

	// This function is run immediately before autonomousPeriodic()
	@Override
	public void autonomousInit() {
		auto.reset();
	}

	// This function is called periodically during autonomous
	@Override
	public void autonomousPeriodic() {
		if (SmartDashboard.getBoolean("Button 0", false)) {
			auto.run(AutoPath.CENTER);
		}
		else if (SmartDashboard.getBoolean("Button 1", false)) {
			auto.run(AutoPath.LEFT);
		}
		else if (SmartDashboard.getBoolean("Button 2", false)) {
			auto.run(AutoPath.RIGHT);
		}
		else {
			drive.move(0);
			System.out.println("ERROR: No Autonomous Selected");
		}
		
//		if (drive.getNormalizedPositionR() < 4900) {
//			drive.move(0.2);
//		}
//		else {
//			drive.stop();
//		}
//		System.out.println("leftenc = "+drive.getNormalizedPositionL());
//		System.out.println("rightenc = "+drive.getNormalizedPositionR());
	}

	@Override
	public void teleopInit() {
		drive.resetEncoders();
		gyro.reset();
		drive.pidControl(0, 0, 0, 0);
	}
	
	// This function is called periodically during operator control (teleop)
	@Override
	public void teleopPeriodic() {
		drive.teleopdrive(joyR.getRawAxis(1), joyL.getRawAxis(1));
		if (joyR.getRawButton(2)) drive.move(0.5);
		if (joyL.getRawButton(3)) drive.move(0.2);
		if(joyR.getRawButton(3)) {
			lifter.operate(1);
		}
		else if (joyL.getRawButton(6) && !lifter.atSwitch()) {
			lifter.operate(-1);
		}
		else if (joyL.getRawButton(4)) {
			lifter.operate(-1);
		}
		else {
			lifter.stop();

		}
		
		if (joyL.getRawButton(1)) {
			intake.run(1);
		}
		else if (joyR.getRawButton(1)){
			intake.run(-1);
		}
		else {
			intake.run(0);
		}
		
		System.out.println("Top = "+lifter.atScale());
		System.out.println("Bottom = "+lifter.atBottom());
		System.out.println("Switch = "+lifter.atSwitch());
	}

	@Override
	public void testInit() {
		auto.reset();
		drive.brakeMode(true);
	}
	
	// This function is called periodically during test modes
	@Override
	public void testPeriodic() {
		auto.runCenter();
//		if(drive.turn( 90 ,Scotstants.AUTO_TURN_SPEED)) {
//			drive.stop();
//		}

	}
	
	public void disabledInit() {
		drive.brakeMode(false);
	}
}
	
	