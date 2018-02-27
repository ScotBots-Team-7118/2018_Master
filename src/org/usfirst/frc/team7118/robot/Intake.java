package org.usfirst.frc.team7118.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

/**
 * Skeleton for an intake system based on
 * wheels running on both sides with 2 motors.
 */
/*
 * Methods:
 * public Intake() - Constructs a new intake system.
 * public void run(double v) - Runs the intake system at a given velocity.
 */

public class Intake {
	// Define Variables
	TalonSRX talIR, talIL;
	
	/**
	 * Constructs a new intake system.
	 */
	public Intake() {
		talIR = new TalonSRX(Scotstants.TALON_IR_PORT);
		talIL = new TalonSRX(Scotstants.TALON_IL_PORT);
	}
	
	/**
	 * Runs the intake system at a given velocity.
	 * @param v
	 */
	public void run(int mode) {
		if (mode == 0) {
			talIR.set(ControlMode.PercentOutput, Scotstants.AUTO_INTAKE_SPEED);
			talIL.set(ControlMode.PercentOutput, -Scotstants.AUTO_INTAKE_SPEED);
		}
		else {
			talIR.set(ControlMode.PercentOutput, -Scotstants.AUTO_INTAKE_SPEED);
			talIL.set(ControlMode.PercentOutput, Scotstants.AUTO_INTAKE_SPEED);
		}
	}
	
	public void stop() {
		talIR.set(ControlMode.PercentOutput, 0);
		talIL.set(ControlMode.PercentOutput, 0);
	}
}