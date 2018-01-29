package org.usfirst.frc.team7118.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Encoder;

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

	// fetch classes
	Scotstants scotstants;
	Sensors sensors;
	// init joysticks
	Joystick joyR = new Joystick(Scotstants.PORT_JOY_R);
	Joystick joyL = new Joystick(Scotstants.PORT_JOY_L);

	public Drive(Sensors sense) {
		// sensors
		sensors = sense;
	}

	void setRight(double amountR) {
		// set right side drive
		talRM.set(ControlMode.PercentOutput, amountR);
		talRF.set(ControlMode.Follower, Scotstants.PORT_RF);
	}

	void setLeft(double amountL) {
		// set left side drive
		talLM.set(ControlMode.PercentOutput, amountL);
		talLF.set(ControlMode.Follower, Scotstants.PORT_LF);
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
	 **/

	void move(double moving) {
		// forward moving function
		setLeft(moving);
		setRight(moving);
	}

	// Teleop:
	// bring in intake class
	Intake intake;

	public void teleopdrive() {
		// parabolic drive for right joystick
		if (joyR.getY() >= 0.2 || joyR.getY() <= -0.2) {
			setRight(Math.pow(joyR.getY(), 3));
		} else {
			setRight(0);
		}
		if (joyL.getY() >= 0.2 || joyL.getY() <= -0.2) {
			// parabolic drive for left joystick
			setLeft(Math.pow(joyL.getY(), 3));
		} else {
			setLeft(0);
		}

	}

	public void teleopintake() {
		if (joyR.getRawButton(0)) {
			// intake motor in
			intake.inMotor(.5);
		} else if (joyR.getRawButton(3)) {
			// intake motor out
			intake.inMotor(-.5);
		} else {
			intake.inMotor(0);
		}
	}
}
