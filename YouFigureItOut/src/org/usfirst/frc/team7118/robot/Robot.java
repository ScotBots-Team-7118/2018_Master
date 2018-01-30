package org.usfirst.frc.team7118.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import org.usfirst.frc.team7118.robot.Scotstants;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	Drive drive;
	Joystick joystick;
	Auto auto;
	Gyroscope gyro;
	SendableChooser<Integer> autoSelector;
	
	// This function is run when the robot is first initialized
	@Override
	public void robotInit() {
		joystick = new Joystick(Scotstants.JOYSTICK_PORT);
		drive = new Drive();
		auto = new Auto(drive);
		gyro = new Gyroscope();
		autoSelector = new SendableChooser<Integer>();
		autoSelector.addDefault("Default Auto", Scotstants.AUTO_DEFAULT);
		autoSelector.addObject("Left Auto", Scotstants.AUTO_L);
		autoSelector.addObject("Center Auto", Scotstants.AUTO_C);
		autoSelector.addObject("Right Auto", Scotstants.AUTO_R);
	}
	
	// This function is run immediately before autonomousPeriodic()
	@Override
	public void autonomousInit() {
		System.out.println((int) autoSelector.getSelected());
	}
	
	//This function is called periodically during autonomous
	@Override
	public void autonomousPeriodic() {
		
	}
	
	// This function is called periodically during operator control (teleop)
	@Override
	public void teleopPeriodic() {
		
	}
	
	// This function is called periodically during test mode
	@Override
	public void testPeriodic() {
		
	}
	
}