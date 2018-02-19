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
	Auto auto;
	Gyroscope gyro;
//	SendableChooser autoChooser;
	
	// This function is run when the robot is first initialized
	@Override
	public void robotInit() {
		gyro = new Gyroscope();
		joyR = new Joystick(Scotstants.JOY_R_PORT);
		joyL = new Joystick(Scotstants.JOY_L_PORT);
		drive = new Drive(gyro);
		auto = new Auto(drive);
//		autoChooser = new SendableChooser();
//		autoChooser.addDefault("Center", "Center");
//		autoChooser.addObject("Left", "Left");
//		autoChooser.addObject("Right", "Right");
//		SmartDashboard.putData("Auto Mode Chooser", autoChooser);
	}

	// This function is run immediately before autonomousPeriodic()
	@Override
	public void autonomousInit() {
		gyro.reset();
		drive.encoderReset();
		
	}

	// This function is called periodically during autonomous
	@Override
	public void autonomousPeriodic() {

	}

	@Override
	public void teleopInit() {
		drive.pidControl(SmartDashboard.getNumber("Slider 0", 0), SmartDashboard.getNumber("Slider 1", 0), SmartDashboard.getNumber("Slider 2", 0), SmartDashboard.getNumber("slider 3", 0));
		gyro.reset();
		drive.encoderReset();
	}
	
	// This function is called periodically during operator control (teleop)
	@Override
	public void teleopPeriodic() {
//		drive.teleopdrive(joyR.getRawAxis(1), -joyL.getRawAxis(1));
		drive.move(0.2);
		
		if (joyL.getRawButton(0)) {
			drive.brakeMode(true);
		}
		if (joyR.getRawButton(0)) {
			drive.brakeMode(false);
		}
	}

	// This function is called periodically during test mode
	@Override
	public void testPeriodic() {

	}

}