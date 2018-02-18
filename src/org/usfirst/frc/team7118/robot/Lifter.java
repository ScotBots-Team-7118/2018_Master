package org.usfirst.frc.team7118.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.*;
import edu.wpi.first.wpilibj.DigitalInput;

public class Lifter {
	TalonSRX talA1, talA2;
	DigitalInput trigTop, trigBottom, trigSwitch, trigScale;
	public Lifter() {
		talA1 = new TalonSRX(Scotstants.TALON_A1_PORT);
		talA2 = new TalonSRX(Scotstants.TALON_A2_PORT);
		trigTop = new DigitalInput(0);
		trigBottom = new DigitalInput(0);
		trigSwitch = new DigitalInput(0);
		trigScale = new DigitalInput(0);
	}
	
	public void operate(double v) {
		double outputVelocity;
		if (trigBottom.get()) {
			if (v > 0) outputVelocity = v;
			else outputVelocity = 0;
		}
		else if (trigTop.get()) {
			if (v < 0) outputVelocity = v;
			else outputVelocity = 0;
		}
		else outputVelocity = v;
		talA1.set(ControlMode.PercentOutput, outputVelocity);
		talA2.set(ControlMode.PercentOutput, outputVelocity);
	}
	
	public boolean atSwitch() {
		if (trigSwitch.get()) return true;
		else return false;
	}
	
	public boolean atScale() {
		if (trigScale.get()) return true;
		else return false;
	}
}