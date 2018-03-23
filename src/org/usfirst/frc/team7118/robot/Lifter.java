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
	public void operate(int direction) {
		double outputVelocity = 0;
		if (direction != 1 && direction != -1) {
			System.out.println("ERROR: Lifting function used incorrectly");
			stop();
		}
		else if (atBottom() && direction == -1) {
			outputVelocity = 0;
		}
		else if (atScale() && direction == 1) {
			outputVelocity = 0;
		}
		else if (direction == 1) outputVelocity = -Scotstants.AUTO_LIFTING_SPEED;
		else outputVelocity = Scotstants.AUTO_LIFTING_SPEED;
		
		talA1.set(ControlMode.PercentOutput, outputVelocity);
		talA2.set(ControlMode.PercentOutput, outputVelocity);
	}
	
	public void stop() {
		talA1.set(ControlMode.PercentOutput, 0);
		talA2.set(ControlMode.PercentOutput, 0);
		
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