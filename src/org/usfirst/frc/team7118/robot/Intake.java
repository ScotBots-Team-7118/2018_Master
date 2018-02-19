package org.usfirst.frc.team7118.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Intake {
	TalonSRX talIR, talIL;
	
	public Intake() {
		talIR = new TalonSRX(Scotstants.TALON_IR_PORT);
		talIL = new TalonSRX(Scotstants.TALON_IL_PORT);
	}

	public void run(double v) {
		talIR.set(ControlMode.PercentOutput, v);
		talIL.set(ControlMode.PercentOutput, v);
	}

}