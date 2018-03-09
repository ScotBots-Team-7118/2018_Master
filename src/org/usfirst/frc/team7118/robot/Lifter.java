package org.usfirst.frc.team7118.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.DigitalInput;

/**
 * Skeleton for a lifting system based on
 * 2 distinct mechanical arms and using
 * 4 limit switches to check for data at given points.
 */
/*
 * Methods:
 * public Lifter() - Constructs a new lifter.
 * public void operate(double v) - Operates the lifter for a given velocity
 * public boolean atSwitch() - Returns true if the limit switch at the switch is active.
 * public boolean atScale() - Returns true if the limit switch at the scale is active.
 */

public class Lifter {
	// Define Variables
	TalonSRX talA1, talA2;
	DigitalInput trigTop, trigBottom, trigSwitch;
	
	/**
	 * Constructs a new lifter.
	 */
	public Lifter() {
		talA1 = new TalonSRX(Scotstants.TALON_A1_PORT);
		talA2 = new TalonSRX(Scotstants.TALON_A2_PORT);
		trigTop = new DigitalInput(9);
		trigBottom = new DigitalInput(8);
		trigSwitch = new DigitalInput(7);
		talA1.setNeutralMode(NeutralMode.Brake);
		talA2.setNeutralMode(NeutralMode.Brake);
	}
	
	/**
	 * Operates the lifter for a given velocity
	 * @param v
	 */
	public void operate(double v) {
		double outputVelocity = -v;
//		if (atBottom()) {
//			if (v > 0) outputVelocity = -v;
//			else outputVelocity = 0;
//		}
//		else if (atScale()) {
//			if (v < 0) outputVelocity = -v;
//			else outputVelocity = 0;
//		}
//		else outputVelocity = v;
		talA1.set(ControlMode.PercentOutput, outputVelocity);
		talA2.set(ControlMode.PercentOutput, outputVelocity);
	}
	
	/**
	 * Returns true if the limit switch at the switch is active.
	 * @return
	 */
	public boolean atSwitch() {
		return !trigSwitch.get();
	}
	
	public boolean atBottom() {
		return !trigBottom.get();
	}
	
	/**
	 * Returns true if the limit switch at the scale is active.
	 * @return
	 */
	public boolean atScale() {
		return !trigTop.get();
	}
}