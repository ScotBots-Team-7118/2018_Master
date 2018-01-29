package org.usfirst.frc.team7118.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Intake {
	// init intake talons
	TalonSRX inTalR = new TalonSRX(Scotstants.INTAL_R_PORT);
	TalonSRX inTalL = new TalonSRX(Scotstants.INTAL_R_PORT);

	public void inMotor(double speed) {
		// set the intake talons
		inTalR.set(ControlMode.PercentOutput, speed);
		inTalL.set(ControlMode.PercentOutput, speed);
	}

}
