package org.usfirst.frc.team7118.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import org.usfirst.frc.team7118.robot.Scotstants;

public class Drive {
	
	TalonSRX talLM, talLF, talRM, talRF;
	
	public Drive() {
		talLM = new TalonSRX(Scotstants.TALON_LM_PORT);
		talLF = new TalonSRX(Scotstants.TALON_LF_PORT);
		talRM = new TalonSRX(Scotstants.TALON_RM_PORT);
		talRF = new TalonSRX(Scotstants.TALON_RF_PORT);
	}
}
