package org.usfirst.frc.team7118.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Encoder;
import org.usfirst.frc.team7118.robot.Scotstants;

public class Drive {
	// name talons
	TalonSRX talLM, talLF, talRM, talRF;

	public Drive() {
		// init talons
		talLM = new TalonSRX(Scotstants.TALON_LM_PORT);
		talLF = new TalonSRX(Scotstants.TALON_LF_PORT);
		talRM = new TalonSRX(Scotstants.TALON_RM_PORT);
		talRF = new TalonSRX(Scotstants.TALON_RF_PORT);
	
	}

//	Sensors sensors;

	void setRight(double amountR) {
		// set right side drive
		talRM.set(ControlMode.PercentOutput, amountR);
		talRF.set(ControlMode.Follower, Scotstants.TALON_RF_PORT);
	}

	void setLeft(double amountL) {
		// set left side drive
		talLM.set(ControlMode.PercentOutput, amountL);
		talLF.set(ControlMode.Follower, Scotstants.TALON_LF_PORT);
	}

	void stop(boolean stopping) {
		// stop both motors
		setRight(0);
		setLeft(0);
	}

	/**
	 * Constructor for Drive
	 * 
	 * @param rm
	 *            right master
	 * 
	 * @param rf
	 *            right follower
	 * 
	 * @param lm
	 *            left master
	 * 
	 * @param lf
	 *            left follower
	 */

	/**
	 * Sets both sides to a certain value
	 * 
	 * @param amount
	 *            speed for motors
	 */

	void move(double moving) {
		// forward moving function
		setLeft(moving);
		setRight(moving);
	}

	public void teleopdrive(double joyR, double joyL) {
		if (joyR >= 0.2) {
		setRight(Math.pow(joyR, 2));
		}else if(joyR <= -0.2){
		setRight(-(Math.pow(joyR,2)));
		}else {
			setLeft(0);
		}
		if (joyL >= 0.2) {	
			setLeft(Math.pow(joyL, 2));
		} else if(joyL <= -0.2){
		setLeft(-(Math.pow(joyL,2)));
		}else{
		setLeft(0);
		}
		 
	}

//	public void teleopintake() {
//		if (joyR.getRawButton(0)) {
//			// intake motor in
//			intake.inMotor(.5);
//		} else if (joyR.getRawButton(3)) {
//			// intake motor out
//			intake.inMotor(-.5);
//		} else {
//			intake.inMotor(0);
//		}
//	}
}
