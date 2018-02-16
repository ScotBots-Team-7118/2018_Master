package org.usfirst.frc.team7118.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Encoder;
import org.usfirst.frc.team7118.robot.Scotstants;

public class Drive {
	Gyroscope gyro;
	// name talons
	TalonSRX talLM, talLF, talRM, talRF;

	public Drive() {
		gyro =  new Gyroscope();
		// init talons
		talLM = new TalonSRX(Scotstants.TALON_LM_PORT);
		talLF = new TalonSRX(Scotstants.TALON_LF_PORT);
		talRM = new TalonSRX(Scotstants.TALON_RM_PORT);
		talRF = new TalonSRX(Scotstants.TALON_RF_PORT);
		
		

	}

	// Sensors sensors;

	void setRight(double amountR) {
		// set right side drive
		talRM.set(ControlMode.PercentOutput, amountR);
		talRF.set(ControlMode.Follower, Scotstants.TALON_RM_PORT);
	}

	void setLeft(double amountL) {
		// set left side drive
		talLM.set(ControlMode.PercentOutput, amountL);
		talLF.set(ControlMode.Follower, Scotstants.TALON_LM_PORT);
	}

	void stop(boolean stopping) {
		// stop both motors
		setRight(0);
		setLeft(0);
		talLM.setNeutralMode(NeutralMode.Brake);
		talRM.setNeutralMode(NeutralMode.Brake);
		talLF.setNeutralMode(NeutralMode.Brake);
		talRF.setNeutralMode(NeutralMode.Brake);
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
	public boolean moveLength(double distIN, double speed) {
		double a = 0;
		a = (distIN/Scotstants.WHEEL_CIRCUM)*Scotstants.ENCODER_ROTATION_DIFF;
		move(speed);
		if(a >= distIN) {
			return true;
		}else {
			return false;
		}
}
	// in: inches
	// out rotations
	public double distIN(double n) {
		double a = 0;
		a = (n/Scotstants.WHEEL_CIRCUM)*Scotstants.ENCODER_ROTATION_DIFF;
		return a;
	}
	
	void exactMove(double moveLeft, double moveRight) {
		setLeft(moveLeft);
		setRight(moveRight);
	}

	public void teleopdrive(double joyR, double joyL) {
		if (joyR >= 0.2) {
			setRight(Math.pow(joyR, 2));
		} else if (joyR <= -0.2) {
			setRight(-(Math.pow(joyR, 2)));
		} else {
			setRight(0);
		}
		if (joyL >= 0.2) {
			setLeft(Math.pow(joyL, 2));
		} else if (joyL <= -0.2) {
			setLeft(-(Math.pow(joyL, 2)));
		} else {
			setLeft(0);
		}

	}

	public boolean turn(double angle, double maxSpeed) {
		// Defaults the speed to the maximum speed
		double speed = maxSpeed;
		// Adjusts speed linearly when within 10 degrees of expected value
		double delta = Math.abs(gyro.getOffsetHeading() - angle);

		if (delta <= Scotstants.MAX_DEGREES_FULL_SPEED) {
			// Simple linear regression
			// m = (Y2 - Y1) / ( X2 - X1 )
			double m = ((maxSpeed - Scotstants.MIN_TURN_SPEED) / (Scotstants.MAX_DEGREES_FULL_SPEED - Scotstants.TURN_OFFSET));
			// y = m*x + b => b = y - m*x. Choosing Y1 and X1:
			double b = Scotstants.MIN_TURN_SPEED - (m * Scotstants.TURN_OFFSET);

			// Substitute in the angle delta for x and
			speed = m * delta + b;

		}

		// Checks if the gyro angle is less than the desired angle
		if (gyro.getOffsetHeading() < angle - Scotstants.TURN_OFFSET) {
			// If it is, turn left
			exactMove(-speed, speed);
			// and tell the source that turning is not done
			return false;

		}

		// Otherwise, checks if the gyro angle is greater than the desired angle
		else if (gyro.getOffsetHeading() > angle + Scotstants.TURN_OFFSET) {
			// If it is, turn right
			exactMove(speed, -speed);
			// and tell the source that turning is not done
			return false;
		}

		else {
			// If the gyro angle is aligned with the desired angle,
			// tell the source that the robot has turned the desired amount
			exactMove(0, 0);
			return true;
		}
	}

	// public void teleopintake() {
	// if (joyR.getRawButton(0)) {
	// // intake motor in
	// intake.inMotor(.5);
	// } else if (joyR.getRawButton(3)) {
	// // intake motor out
	// intake.inMotor(-.5);
	// } else {
	// intake.inMotor(0);
	// }
	// }
}
