package org.usfirst.frc.team7118.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import org.usfirst.frc.team7118.robot.Scotstants;

/**
 * Skeleton for an object that controls the drive train of the robot.
 */
/*
 * Methods:
 * public Drive(Gyroscope gyro) - Constructs a new Drive Object.
 * public double getNormalizedPositionL() - Fetches the distance traveled by the left side since the last reset (in rotations).
 * public double getNormalizedPositionR() - Fetches the distance traveled by the right side since the last reset (in rotations).
 * public void pidControl(double kF, double kP, double kI, double kD) - Applies a PIDF filter to the talon speeds.
 * public void resetGyro() - Resets the gyroscope.
 * public void resetEncoders() - Resets the encoder distances for the normalized positions.
 * public void setRight(double amountR) - Sets the right side talons to a given value.
 * public void setLeft(double amountL) - Sets the left side talons to a given value.
 * public void stop()- Sets talons on both sides to 0.
 * public void teleopdrive(double joyR, double joyL) - Drives the robot with parabolic control according to 2 joystick values.
 * public void brakeMode(boolean mode)- Configures brake mode on the talons.
 * public void move(double moving)- Sets talons on both sides to the same value.
 * public boolean turn(double angle, double maxSpeed) - Turns the robot a set amount of degrees.
 */

public class Drive {
	// Defines needed objects
	Gyroscope gyro;
	TalonSRX talLM, talLF, talRM, talRF;
	double initEncLeft, initEncRight;
	
	/**
	 * Constructs a new Drive Object.
	 * @param gyro
	 */
	public Drive(Gyroscope gyro) {
		this.gyro = gyro;
		// Initializes Drive Talons
		talLM = new TalonSRX(Scotstants.TALON_LM_PORT);
		talLF = new TalonSRX(Scotstants.TALON_LF_PORT);
		talRM = new TalonSRX(Scotstants.TALON_RM_PORT);
		talRF = new TalonSRX(Scotstants.TALON_RF_PORT);
		
		brakeMode(false);
			
		talLM.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		talRM.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, 0);
		
		talLM.setSensorPhase(true);
		talRM.setSensorPhase(true);
		
		initEncLeft = talLM.getSelectedSensorPosition(0);
		initEncRight = talRM.getSelectedSensorPosition(0);
	}

	/**
	 * Fetches the distance traveled by the left side since the last reset (in rotations).
	 * @return
	 */
	public double getNormalizedPositionL() {
		return talLM.getSelectedSensorPosition(0) - initEncLeft;
	}
	
	/**
	 * Fetches the distance traveled by the right side since the last reset (in rotations).
	 * @return
	 */
	public double getNormalizedPositionR() {
		return talRM.getSelectedSensorPosition(0) - initEncRight;
	}
	
	/**
	 * Applies a PIDF filter to the talon speeds.
	 * @param kF
	 * @param kP
	 * @param kI
	 * @param kD
	 */
	public void pidControl(double kF, double kP, double kI, double kD) {
		talLM.config_kF(0, kF, 0);
		talLM.config_kP(0, kP, 0);
		talLM.config_kI(0, kI, 0);
		talLM.config_kD(0, kD, 0);
		
		talRM.config_kF(0, kF, 0);
		talRM.config_kP(0, kP, 0);
		talRM.config_kI(0, kI, 0);
		talRM.config_kD(0, kD, 0);
	}
	
	/**
	 * Resets the gyroscope.
	 */
	public void resetGyro() {
		gyro.reset();
	}
	
	/**
	 * Resets the encoder distances for the normalized positions.
	 */
	public void resetEncoders() {
		initEncLeft = talLM.getSelectedSensorPosition(0);
		initEncRight = talRM.getSelectedSensorPosition(0);
	}
	
	/**
	 * Sets the right side talons to a given value.
	 * @param amountR
	 */
	public void setRight(double amountR) {
		// set right side drive
		talRM.set(ControlMode.PercentOutput, -amountR);
		talRF.set(ControlMode.Follower, Scotstants.TALON_RM_PORT);
	}
	/**
	 *  Sets the left side talons to a given value.
	 * @param amountL
	 */
	public void setLeft(double amountL) {
		// set left side drive
		talLM.set(ControlMode.PercentOutput, amountL);
		talLF.set(ControlMode.Follower, Scotstants.TALON_LM_PORT);
	}
	
	/**
	 * Sets talons on both sides to the same value.
	 * @param moving
	 */
	public void move(double moving) {
		// forward moving function
		setLeft(moving);
		setRight(moving);
	}
	
	/** 
	 * Sets talons on both sides to 0.
	 */
	public void stop() {
		//stop both motors
		setRight(0);
		setLeft(0);
	}
	
	
	/**
	 *  Configures brake mode on the talons.
	 * @param mode
	 */
	public void brakeMode(boolean mode) {
		if (mode) {
			talLM.setNeutralMode(NeutralMode.Brake);
			talRM.setNeutralMode(NeutralMode.Brake);
			talLF.setNeutralMode(NeutralMode.Brake);
			talRF.setNeutralMode(NeutralMode.Brake);
		}
		else {
			talLM.setNeutralMode(NeutralMode.Coast);
			talRM.setNeutralMode(NeutralMode.Coast);
			talLF.setNeutralMode(NeutralMode.Coast);
			talRF.setNeutralMode(NeutralMode.Coast);
		}
	}
	
	/**
	 * Drives the robot with parabolic control according to 2 joystick values.
	 * @param joyR
	 * @param joyL
	 */
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
	
	/**
	 * Turns the robot a set amount of degrees.
	 * @param angle
	 * @param maxSpeed
	 * @return
	 */
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
			setLeft(-speed);
			setRight(speed);
			// and tell the source that turning is not done
			return false;

		}

		// Otherwise, checks if the gyro angle is greater than the desired angle
		else if (gyro.getOffsetHeading() > angle + Scotstants.TURN_OFFSET) {
			// If it is, turn right
			setRight(-speed);
			setLeft(speed);
			// and tell the source that turning is not done
			return false;
		}

		else {
			// If the gyro angle is aligned with the desired angle,
			// tell the source that the robot has turned the desired amount
			stop();
			return true;
		}
	}
}
