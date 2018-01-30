package org.usfirst.frc.team7118.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

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
	public SendableChooser autoChooser;
	// This function is run when the robot is first initialized
	@Override
	public void robotInit() {
		joystick = new Joystick(Scotstants.JOYSTICK_PORT);
		drive = new Drive();
		auto = new Auto();
		gyro = new Gyroscope();
		autoChooser = new SendableChooser();
		autoChooser.addDefault("Center", "Center");
		autoChooser.addObject("Left", "Left");
		autoChooser.addObject("Right", "Right");
		SmartDashboard.putData("Auto Mode Chooser", autoChooser);
	} 
	// This function is run immediately before autonomousPeriodic()
	@Override
	public void autonomousInit() {
	
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