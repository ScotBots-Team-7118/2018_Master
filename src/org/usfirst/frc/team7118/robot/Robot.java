package org.usfirst.frc.team7118.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team7118.robot.Scotstants;

import com.ctre.phoenix.motorcontrol.ControlMode;

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
//	Auto auto;
	Gyroscope gyro;
	Intake intake;
	Lifter lifter;
	SendableChooser<Scotstants.AutoPath> autoChooser;
	
	// This function is run when the robot is first initialized
	@Override
	public void robotInit() {
		gyro = new Gyroscope();
//		intake = new Intake();
		lifter = new Lifter();
		joyR = new Joystick(Scotstants.JOY_R_PORT);
		joyL = new Joystick(Scotstants.JOY_L_PORT);
		drive = new Drive(gyro);
//		auto = new Auto(drive, intake, lifter);
		autoChooser = new SendableChooser<Scotstants.AutoPath>();
		autoChooser.addDefault("Select Autonomous", null);
		autoChooser.addObject("Center", Scotstants.AutoPath.CENTER);
		autoChooser.addObject("Left", Scotstants.AutoPath.LEFT);
		autoChooser.addObject("Right", Scotstants.AutoPath.RIGHT);
		SmartDashboard.putData("Auto Mode Chooser", autoChooser);
	}

	// This function is run immediately before autonomousPeriodic()
	@Override
	public void autonomousInit() {
		drive.resetEncoders();
	}

	// This function is called periodically during autonomous
	@Override
	public void autonomousPeriodic() {
		if (autoChooser.getSelected() == null) {
			drive.move(0);
			System.out.println("ERROR: No autonomous selected");
		}
		else {
//			auto.run(autoChooser.getSelected());
		}
	}

	@Override
	public void teleopInit() {
		drive.pidControl(SmartDashboard.getNumber("Slider 0", 0), SmartDashboard.getNumber("Slider 1", 0), SmartDashboard.getNumber("Slider 2", 0), SmartDashboard.getNumber("slider 3", 0));
		drive.resetEncoders();
	}
	
	// This function is called periodically during operator control (teleop)
	@Override
	public void teleopPeriodic() {
		drive.teleopdrive(joyR.getRawAxis(1), joyL.getRawAxis(1));
		if(joyR.getRawButton(1)) {
			drive.gyroDrive(0.1);
		}
		
		if (joyL.getRawButton(4)) {
			drive.brakeMode(true);
		}
		if (joyR.getRawButton(4)) {
			drive.brakeMode(false);
		}
		System.out.println("Gyro = " + gyro.getRawHeading());
		System.out.println("Top = " + !lifter.trigTop.get());
		System.out.println("Bottom = " + !lifter.trigBottom.get());
		System.out.println("Switch = " + !lifter.trigSwitch.get());
	}

	@Override
	public void testInit() {
	}
	
	// This function is called periodically during test modes
	@Override
	public void testPeriodic() {
		System.out.println(gyro.getRawHeading());
		System.out.println(gyro.getOffsetHeading());
	}
}

